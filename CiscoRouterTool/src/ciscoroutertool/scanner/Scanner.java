package ciscoroutertool.scanner;

import ciscoroutertool.rules.Rule;
import ciscoroutertool.scanner.parser.RouterConfigManager;
import ciscoroutertool.utils.Host;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import net.sf.expectit.Expect;
import net.sf.expectit.ExpectBuilder;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;

import static net.sf.expectit.matcher.Matchers.contains;

/**
 * Runs the scan for a host and holds the results
 * @version 0.01ALPHA
 * @author Andrew Johnston
 */
public class Scanner implements Callable<HostReport> {

    /**
     * The list of rules to check each host against.
     */
    public static ArrayList<Rule> rules;
    
    /**
     * The list of rules that the host matched.
     */
    private ArrayList<Rule> matched;
    
    /**
     * The port the SSH Server is listening on.
     */
    private static final int SSH_PORT = 22;
    
    /**
     * The command to get the full configuration file from the device.
     */
    private static final String GET_ALL_CONFIG = "show running-config full";

    /**
     * The command to enable superuser privileges
     */
    private static final String ENABLE_SUPERUSER = "enable";

    /**
     * The command to disable the use of "more" to pipe the config file
     */
    private static final String DISABLE_OUTPUT_BUFFERING = "terminal length 0";
    
    /**
     * The host that the Scanner object will scan.
     */
    private final Host host;

    private static final String PASSWORD_PROMPT = "Password:";

    /**
     * Initializes the scanner.
     * @param h The host to be scanned
     */
    public Scanner(Host h) {
        host = h;
    }


    /**
     * Performs the scan and returns a HostReport containing all matched rules.
     * @return The HostReport containing the matched rules.
     * @throws Exception Any Unhandled exception generated during the scan.
     */
    @Override
    public HostReport call()  {
        ArrayList<String> configlines = null;
        try {
            configlines = getConfigFile();
        } catch (Exception e) {
            System.err.println("Failed to connect to host " + host.toString() + ". Please check " +
                "URL and credentials and rerun.");
            System.err.println("The exact error was: " + e.getMessage());
            //We won't have any data on the host, so construct an empty report and throw it back
            HostReport failedToConnect = new HostReport(host);
            return failedToConnect;
        }
        ArrayList<String> activeLines = 
                RouterConfigManager.getActiveConfig(configlines);
        return getHostReport(activeLines);
    }
    
    /**
     * Connects to the device and retrieves the configuration file from the 
     * device.
     * @return A ArrayList containing the output from the GET_ALL_CONFIG
     * command 
     */
    private ArrayList<String> getConfigFile()  throws Exception{
        JSch jsch = new JSch();
        InputStream in = null;
        Session session = jsch.getSession(
                host.getUser(),
                host.toString(),
                SSH_PORT);
        session.setPassword(host.getPass());
        //If this line isn't present, every host must be in known_hosts
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        Channel channel = session.openChannel("shell");
        in = channel.getInputStream();
        OutputStream outputStream = channel.getOutputStream();
        Expect expect = new ExpectBuilder()
                .withOutput(outputStream)
                .withInputs(channel.getInputStream(), channel.getExtInputStream())
                        //.withEchoOutput(System.out)
                        //.withEchoInput(System.out)
                .build();

        channel.connect();
        if (host.usesEnable()) {
            expect.expect(contains(">"));
            expect.sendLine(ENABLE_SUPERUSER);
            expect.expect(contains(PASSWORD_PROMPT));
            expect.sendLine(host.getEnablePass());
        }
        expect.expect(contains("#")); //#
        expect.sendLine(DISABLE_OUTPUT_BUFFERING); //terminal length 0
        expect.expect(contains("#")); //#
        expect.sendLine(GET_ALL_CONFIG); //show running-config full
        String result = expect.expect(contains("#")).getBefore(); //#
        channel.disconnect();
        session.disconnect();
        expect.close();
        String[] arrLines = result.split("\n");
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(arrLines));
        return lines;
    }

    /**
     * Compares the rules to the configuration file and stores the matched ones
     * @param activeConfig The configuration with all shutdown interfaces 
     * removed.
     * @return A HostReport containing all matched rules
     */
    private HostReport getHostReport(ArrayList<String> activeConfig) {
        HostReport report = new HostReport(host);
        for (Rule r : rules) {
            if (r.matchesRule(activeConfig)) {
                report.addMatchedRule(r);
            }
        }
        return report;
    }
}
