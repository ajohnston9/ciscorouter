package ciscoroutertool.output.formats;

import ciscoroutertool.scanner.HostReport;

import java.io.File;

/**
 * Renders output in CSV format
 * @version 0.01ALPHA
 * @author Andrew H. Johnston
 */
public class CSVOutputRenderer extends AbstractOutputRenderer {

    /**
     * Readies the output renderer
     * @param _file the file to save to NOTE: This assumes f has correct extension
     */
    public CSVOutputRenderer(File _file) {
        super(_file);
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
