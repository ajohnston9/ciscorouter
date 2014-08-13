package ciscoroutertool.output.formats;

import ciscoroutertool.rules.Rule;
import ciscoroutertool.scanner.FullReport;
import ciscoroutertool.scanner.HostReport;
import nu.xom.Document;
import nu.xom.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Writes output in an XML format
 * @version 0.01ALPHA
 * @author Andrew H. Johnston
 */
public class XMLOutputRenderer extends AbstractOutputRenderer {

    private Element root;
    private Element hostsRoot;

    /**
     * Readies the output renderer
     * @param f the file to save to NOTE: This assumes _file has correct extension
     */
    public XMLOutputRenderer(File f) {
        super(f); //File is now in protected class variable file
        root = new Element("FullReport");
        hostsRoot = new Element("Hosts");
        root.appendChild(hostsRoot);
    }

    /**
     * Adds a host report to the end of the output buffer
     * @param hostReport The report to add to the output
     */
    @Override
    public void addHostReport(HostReport hostReport) {
        ArrayList<Rule> rules = hostReport.getMatchedRules();
        Element host = new Element("Host");

        Element ip = new Element("IP");
        ip.appendChild(String.valueOf(hostReport.getHost()));
        host.appendChild(ip);

        Element scanType = new Element("ScanType");
        scanType.appendChild("Full Scan");
        host.appendChild(scanType);

        Element eRules = new Element("Rules");
        for (Rule rule : rules) {
            Element eRule = new Element("Rule");
            Element ruleName = new Element("RuleName");
            Element severity = new Element("Severity");
            Element description = new Element("Description");

            ruleName.appendChild(rule.getName());
            severity.appendChild(rule.getSeverity());
            description.appendChild(rule.getDescription());

            eRule.appendChild(ruleName);
            eRule.appendChild(severity);
            eRule.appendChild(description);
            eRules.appendChild(eRule);
        }
        host.appendChild(eRules);
        hostsRoot.appendChild(host);
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
        Document doc = new Document(root);
        FileWriter fw = new FileWriter(file, false);
        fw.write(doc.toXML());
        fw.flush();
        fw.close();
    }
}
