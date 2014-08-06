package ciscoroutertool.scanner;

import ciscoroutertool.rules.Rule;
import ciscoroutertool.scanner.parser.RouterConfigManager;
import ciscoroutertool.utils.Host;
import com.jcraft.jsch.*;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;

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
        BufferedReader reader = null;
        try {
            reader = getConfigFile();
        } catch (JSchException | IOException e) {
            System.err.println("Failed to connect to host " + host.getAddress().toString() + ". Please check " +
                "URL and credentials and rerun.");
            System.err.println("The exact error was: " + e.getMessage());
            //We won't have any data on the host, so construct an empty report and throw it back
            HostReport failedToConnect = new HostReport(host);
            return failedToConnect;
        }
        String line = null;
        ArrayList<String> lines = new ArrayList<>();
        try {
            while ((line = reader.readLine()) != null) {
                System.out.println("DEBUG: Line is "  + line);
                lines.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> activeLines = 
                RouterConfigManager.getActiveConfig(lines);
        return getHostReport(activeLines);
    }
    
    /**
     * Connects to the device and retrieves the configuration file from the 
     * device.
     * @return A BufferedReader containing the output from the GET_ALL_CONFIG 
     * command 
     */
    private BufferedReader getConfigFile() throws JSchException, IOException{
        InputStream in = null;
        JSch jsch = new JSch();
        Session session = jsch.getSession(
                host.getUser(),
                host.getAddress().getHostAddress(),
                SSH_PORT);
        session.setPassword(host.getPass());
        //If this line isn't present, every host must be in known_hosts
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        Channel channel = session.openChannel("shell");
        in = channel.getInputStream();
        OutputStream outputStream = channel.getOutputStream();
        channel.connect();
        //Enable superuser if its set
        if (host.usesEnable()) {
            outputStream.write((ENABLE_SUPERUSER + "\r\n").getBytes());
            //Send the password with a newline (emulating a user pressing "enter"
            outputStream.write((host.getEnablePass() + "\r\n").getBytes());
            outputStream.flush();
            outputStream.write((DISABLE_OUTPUT_BUFFERING + "\r\n").getBytes());
            outputStream.flush();
            outputStream.write((GET_ALL_CONFIG + "\r\n").getBytes());
            outputStream.flush();

        } else {
            //Run the command to disable buffering, then get config
            outputStream.write((DISABLE_OUTPUT_BUFFERING + "\r\n").getBytes());
            outputStream.flush();
            outputStream.write((GET_ALL_CONFIG+"\r\n").getBytes());
            outputStream.flush();
        }
        //Kill then channel, then read it?
        channel.disconnect();
        return new BufferedReader(new InputStreamReader(in));
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
