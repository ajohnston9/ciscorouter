package ciscoroutertool.scanner;

import ciscoroutertool.rules.Rule;
import ciscoroutertool.scanner.parser.RouterConfigManager;
import ciscoroutertool.utils.Host;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static final String GET_ALL_CONFIG = "show running-config";
    
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
     * @throws Exception Any Exception generated during the scan.
     */
    @Override
    public HostReport call() throws Exception {
        BufferedReader reader = getConfigFile();
        String line = null;
        ArrayList<String> lines = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
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
    private BufferedReader getConfigFile() {
        InputStream in = null;
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(
                    host.getUser(),
                    host.getAddress().getHostAddress(),
                    SSH_PORT);
            session.setPassword(host.getPass());
            session.connect();
            //Run the command that gets the config
            ChannelExec exec = (ChannelExec) session.openChannel("exec");
            in = exec.getInputStream();
            exec.setCommand(GET_ALL_CONFIG);
            exec.connect();         
        } catch (JSchException | IOException ex) {
            Logger.getLogger(Scanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        //noinspection ConstantConditions
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
