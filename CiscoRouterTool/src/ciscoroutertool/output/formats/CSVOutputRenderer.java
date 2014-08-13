package ciscoroutertool.output.formats;

import ciscoroutertool.rules.Rule;
import ciscoroutertool.scanner.FullReport;
import ciscoroutertool.scanner.HostReport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Renders output in CSV format
 * @version 0.01ALPHA
 * @author Andrew H. Johnston
 */
public class CSVOutputRenderer extends AbstractOutputRenderer {

    StringBuilder reportBuilder;
    /**
     * Readies the output renderer
     * @param _file the file to save to NOTE: This assumes f has correct extension
     */
    public CSVOutputRenderer(File _file) {
        super(_file);
        reportBuilder = new StringBuilder();
        reportBuilder.append("host, scan_type, rule_name, severity, description" + System.getProperty("line.seperator"));
    }

    /**
     * Adds a host report to the end of the output buffer
     * @param hostReport The report to add to the output
     */
    @Override
    public void addHostReport(HostReport hostReport) {
        ArrayList<Rule> rules = hostReport.getMatchedRules();
        String stub = hostReport.getHost().toString() + ",Full Scan,";
        for (Rule rule : rules) {
            reportBuilder.append(stub + rule.getName() + "," +"," + rule.getDescription() +
                    System.getProperty("line.seperator"));
        }
    }

    /**
     * Adds a FullReport object to the output buffer
     *
     * @param fullReport The FullReport object to add
     */
    @Override
    public void addFullReport(FullReport fullReport) {
        ArrayList<HostReport> reports = fullReport.getReports();
        for (HostReport hostReport : reports) {
            this.addHostReport(hostReport);
        }
    }

    /**
     * Writes the data to the Output file
     */
    @Override
    public void writeToFile() throws IOException {
        String document = reportBuilder.toString();
        FileWriter fw = new FileWriter(file, false);
        fw.write(document);
        fw.flush();
        fw.close();
    }
}
