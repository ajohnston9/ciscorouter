/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ciscoroutertool.gui;

import ciscoroutertool.scanner.FullReport;
import ciscoroutertool.utils.Host;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 * Runs the scan and retrieves the full report
 * @version 0.01ALPHA
 * @author Andrew H. Johnston
 */
public class ScanLauncher extends SwingWorker<FullReport, Object> {

    private ArrayList<Host> hosts;
    private MainGUI parent;
    
    public ScanLauncher(MainGUI parent, ArrayList<Host> hosts) {
        this.parent = parent;
        this.hosts = hosts;
    }
    
    @Override
    protected FullReport doInBackground() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    protected void done() {
        parent.scanning.dispose();
        try {
            parent.displayReport(get());
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(ScanLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
