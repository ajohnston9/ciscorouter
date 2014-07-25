/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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

    private ArrayList<Rule> rules;
    private ArrayList<Host> hosts;
    
    private static final int MAX_THREADS = 5;
    
    public ScanManager(ArrayList<Host> _hosts) {
        hosts = _hosts;
        rules = RuleParser.getRules();
        //Give the rules to the scanner class
        Scanner.rules = rules;
    }

    /**
     * This could benefit from the Observable pattern, so we can launch this
     * thread and then run the "Please wait..." box, turning it off when we
     * have results.
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
