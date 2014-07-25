package ciscoroutertool.scanner;

import java.util.ArrayList;

/**
 * Holds the hosts report in one report. While it's just a wrapper for the 
 * HostReport objects now, it is useful should we ever want to add more information
 * about each host
 * @version 0.01ALPHA
 * @author Andrew H. Johnston
 */
public class FullReport {
    
    private ArrayList<HostReport> hostReports;
    
    /**
     * Builds the Full Report
     * @param reports the individual host reports
     */
    public FullReport(ArrayList<HostReport> reports) {
        hostReports = reports;
    }
    
    /**
     * Retrieves the list of individual host reports
     * @return An ArrayList of HostReport objects
     */
    public ArrayList<HostReport> getReports() {
        return hostReports;
    }
    
}
