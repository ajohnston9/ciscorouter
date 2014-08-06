package ciscoroutertool.output.formats;

import ciscoroutertool.scanner.HostReport;

import java.io.File;

/**
 * Writes output in an XML format
 * @version 0.01ALPHA
 * @author Andrew H. Johnston
 */
public class XMLOutputRenderer extends AbstractOutputRenderer {

    /**
     * Readies the output renderer
     * @param f the file to save to NOTE: This assumes _file has correct extension
     */
    public XMLOutputRenderer(File f) {
        super(f); //File is now in protected class variable _file
    }

    /**
     * Adds a host report to the end of the output buffer
     * @param hostReport The report to add to the output
     */
    @Override
    public void addHostReport(HostReport hostReport) {

    }

    /**
     * Writes the data to the Output file
     */
    @Override
    public void writeToFile() {

    }
}
