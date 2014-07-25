/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ciscoroutertool.scanner;

import ciscoroutertool.rules.Rule;
import ciscoroutertool.scanner.parser.RouterInterfaceManager;
import ciscoroutertool.scanner.parser.RouterInterface;
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

    public static ArrayList<Rule> rules;
    
    private ArrayList<Rule> matched;
    
    private static final int SSH_PORT = 22;
    private static final String GET_ALL_CONFIG = "show running-config";
    
    private Host host;
    public Scanner(Host h) {
        host = h;
    }



    @Override
    public HostReport call() throws Exception {
        BufferedReader reader = getConfigFile();
        String line = null;
        ArrayList<String> lines = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        ArrayList<String> activeLines = 
                RouterInterfaceManager.getActiveConfig(lines);
        HostReport report = getHostReport(activeLines);
        return report;
    }
    
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
        return new BufferedReader(new InputStreamReader(in));
    }

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
