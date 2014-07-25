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

    /**
     * The GUI that will run the scan
     */
    private final MainGUI parent;
    
    /**
     * The ScanManager for this scan
     */
    private final ScanManager manager;
    
    /**
     * Initializes the ScanLauncher so it can be run
     * @param parent The GUI launching the ScanLauncher
     * @param manager The ScanManager that will be used
     */
    public ScanLauncher(MainGUI parent, ScanManager manager) {
        this.parent = parent;
        this.manager = manager;
    }
    
    /**
     * Runs the scan and displays the waiting window
     * @return The Full Report from the scan
     * @throws Exception Any Exceptions found during scanning
     */
    @Override
    protected FullReport doInBackground() throws Exception {
        parent.scanning.setVisible(true);
        return manager.run();
    }
    
    /**
     * Cleans up everything once scan is completed
     */
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
