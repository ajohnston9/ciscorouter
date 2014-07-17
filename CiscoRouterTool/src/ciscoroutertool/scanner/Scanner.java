/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ciscoroutertool.scanner;

import ciscoroutertool.utils.Host;
import java.util.concurrent.Callable;

/**
 * Runs the scan for a host and holds the results
 * @version 0.01ALPHA
 * @author Andrew Johnston
 */
public class Scanner implements Callable<HostReport> {

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
        throw new UnsupportedOperationException("Not supported yet."); 
    }

}
