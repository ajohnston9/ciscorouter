package ciscoroutertool.output.formats;

import ciscoroutertool.rules.Rule;
import ciscoroutertool.scanner.FullReport;
import ciscoroutertool.scanner.HostReport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Renders the output in a plaintext, human-readable format
 * @version 0.01ALPHA
 * @author Andrew H. Johnston
 */
public class TXTOutputRenderer extends AbstractOutputRenderer {

    StringBuilder reportBuilder;

    /**
     * Readies the output renderer
     * @param _file the file to save to NOTE: This assumes _file has correct extension
     */
    public TXTOutputRenderer(File _file) {
        super(_file);
        reportBuilder = new StringBuilder();
    }

    /**
     * Adds a host report to the end of the output buffer
     * @param hostReport The report to add to the output
     */
    @Override
    public void addHostReport(HostReport hostReport) {
        reportBuilder.append(System.lineSeparator() +
                System.lineSeparator() +"Host: " + hostReport.getHost() + System.lineSeparator());
        reportBuilder.append("Scan Type: Full Scan" + System.lineSeparator()); //Add in dynamic part when we add that feature
        ArrayList<Rule> rules = hostReport.getMatchedRules();
        for (Rule rule : rules) {
            reportBuilder.append("Rule Name: " + rule.getName() + System.lineSeparator());
            reportBuilder.append("Severity: " + rule.getSeverity() + System.lineSeparator());
            reportBuilder.append("Description: " + rule.getDescription() + System.lineSeparator());
        }
        //Break the report up a little
        reportBuilder.append("--------------------------------------------" + System.lineSeparator());
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
