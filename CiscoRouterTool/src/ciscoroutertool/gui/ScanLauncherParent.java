package ciscoroutertool.gui;

import ciscoroutertool.scanner.FullReport;

/**
 * The required methods for the ScanLauncher to integrate into the GUI. By 
 * abstracting these methods into an interface, the Scanning portion of the 
 * application is completely separate from the GUI itself.
 * @version 0.01ALPHA
 * @author Andrew H. Johnston
 */
public interface ScanLauncherParent {
    
    /**
     * Shows the "Please Wait" Dialog while the scan runs.
     */
    public void showPleaseWaitDialog();
    
    /**
     * Removes the "Please Wait" Dialog once the scan is finished.
     */
    public void disposePleaseWaitDialog();
    
    /**
     * Displays the report once the scan is finished
     * @param report 
     */
    public void displayReport(FullReport report);
    
    
}
