/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ciscoroutertool.scanner;

import ciscoroutertool.utils.Host;

/**
 * Runs the scan for a host and holds the results
 * @version 0.01ALPHA
 * @author Andrew Johnston
 */
public class Scanner implements Runnable{

    private Host host;
    
    public Scanner(Host h) {
        //TODO: Write Constructor for Scanner
        host = h;
    }

    @Override
    public void run() {
        //TODO: Write Method run
        //Connect to the host
        //
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
