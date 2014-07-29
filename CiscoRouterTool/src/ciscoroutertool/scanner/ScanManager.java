package ciscoroutertool.scanner;

import ciscoroutertool.rules.Rule;
import ciscoroutertool.rules.RuleParser;
import ciscoroutertool.utils.Host;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Runs the scan on each host and prepares data for the report
 * @version 0.01ALPHA
 * @author Andrew Johnston
 */
public class ScanManager {

    /**
     * The list of rules to run against each host
     */
    private ArrayList<Rule> rules;
    
    /**
     * The hosts we are going to scan
     */
    private ArrayList<Host> hosts;
    
    /**
     * The maximum number of scanners to run at once
     */
    private static final int MAX_THREADS = 5;
    
    /**
     * Sets up the ScanManager so it can run scans against the hosts
     * @param _hosts The hosts you want to scan
     */
    public ScanManager(ArrayList<Host> _hosts) {
        hosts = _hosts;
        rules = RuleParser.getRules();
        //Give the rules to the scanner class
        Scanner.rules = rules;
    }


    /**
     * Runs the scan on all hosts and returns the full report
     * @return The report covering all hosts
     */
    public FullReport run() {
        FullReport fullReport = null;
        try {
            ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
            List<Future<HostReport>> futureReports;
            ArrayList<HostReport> reports = new ArrayList<>();
            ArrayList<Scanner> scanners = new ArrayList<>();
            for (Host h : hosts) {
                Scanner s = new Scanner(h);
                scanners.add(s);
            }
            futureReports = executor.invokeAll(scanners);
            for (Future<HostReport> fReport : futureReports) {
                reports.add(fReport.get());
            }
            fullReport = new FullReport(reports);
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(ScanManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fullReport;
    }

}
