/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ciscoroutertool.gui;

import ciscoroutertool.scanner.FullReport;
import ciscoroutertool.scanner.ScanManager;
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

    private MainGUI parent;
    private ScanManager manager;
    
    public ScanLauncher(MainGUI parent, ScanManager manager) {
        this.parent = parent;
        this.manager = manager;
    }
    
    @Override
    protected FullReport doInBackground() throws Exception {
        parent.scanning.setVisible(true);
        return manager.run();
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
