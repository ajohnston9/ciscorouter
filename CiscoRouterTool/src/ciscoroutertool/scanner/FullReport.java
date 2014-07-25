package ciscoroutertool.scanner;

import java.util.ArrayList;

/**
 *
 * @author andrew
 */
public class FullReport {
    
    private ArrayList<HostReport> hostReports;
    
    public FullReport(ArrayList<HostReport> reports) {
        hostReports = reports;
    }
    
    public ArrayList<HostReport> getReports() {
        return hostReports;
    }
    
}
