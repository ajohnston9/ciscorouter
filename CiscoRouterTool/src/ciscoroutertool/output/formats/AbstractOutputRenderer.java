package ciscoroutertool.output.formats;

import ciscoroutertool.scanner.HostReport;

import java.io.File;

/**
 * Contains the basic methods to save a FullReport in a specified format
 * @version 0.01ALPHA
 * @author Andrew H. Johnston
 */
public abstract class AbstractOutputRenderer {

    /**
     * The file to save to
     */
    protected File file;

    /**
     * Readies the output renderer
     * @param _file the file to save to NOTE: This assumes _file has correct extension
     */
    public AbstractOutputRenderer(File _file) {
        file = _file;
    }

    /**
     * Adds a host report to the end of the output buffer
     * @param hostReport The report to add to the output
     */
    public abstract void addHostReport(HostReport hostReport);

    /**
     * Writes the data to the Output file
     */
    public abstract void writeToFile();

}
