/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ciscoroutertool.scanner;

import ciscoroutertool.utils.Host;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Runs the scan for a host and holds the results
 * @version 0.01ALPHA
 * @author Andrew Johnston
 */
public class Scanner implements Callable<HostReport> {

    public static ArrayList<Rule> rules;
    
    private ArrayList<Rule> matched;
    
    private static final int SSH_PORT = 22;
    private static final String GET_ALL_CONFIG = "";
    
    private Host host;
    public Scanner(Host h) {
        host = h;
    }



    @Override
    public HostReport call() throws Exception {
        //SSH into each host
        //get all configuration information
        //get objects of active configurations
        //run all rules on each active configuration
        //if rule successful, add to report
        //add host object to report
        //return report
        JSch jsch = new JSch();
        Session session = jsch.getSession(
                host.getUser(), 
                host.getAddress().getHostAddress(),
                SSH_PORT);
        session.setPassword(host.getPass());
        session.connect();
        //Run the command that gets the config
        ChannelExec exec = (ChannelExec) session.openChannel("exec");
        InputStream in = exec.getInputStream();
        exec.setCommand(GET_ALL_CONFIG);
        exec.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                
        
        throw new UnsupportedOperationException("Not supported yet."); 
    }

}
