package ciscoroutertool.output;

import ciscoroutertool.output.formats.AbstractOutputRenderer;
import ciscoroutertool.output.formats.CSVOutputRenderer;
import ciscoroutertool.output.formats.TXTOutputRenderer;
import ciscoroutertool.output.formats.XMLOutputRenderer;
import ciscoroutertool.scanner.FullReport;

import java.io.File;
import java.io.IOException;

/**
 * Handles the creation of output
 * @version 0.01ALPHA
 * @author Andrew H. Johnston
 */
public class OutputManager {

    /**
     * The report to use as a datasource
     */
    private FullReport report;
    /**
     * The file to save the output to
     */
    private File file;
    /**
     * The renderer object used to save the output in a specific format
     */
    private AbstractOutputRenderer renderer;

    /**
     * The option for saving a plaintext file
     */
    public final static int TXT_OUTPUT = 0;
    /**
     * The option for saving to an XML-based format
     */
    public final static int XML_OUTPUT  = 1;
    /**
     * The option for saving to a CSV format
     */
    public final static int CSV_OUTPUT  = 2;

    /**
     * Prepares the output for a report
     * @param _report The FullReport object the that will be used for the report
     * @param _file The file to save the report to
     * @param format An integer that corresponds to the desired output format
     * @throws IllegalArgumentException
     */
    public OutputManager(FullReport _report, File _file, int format) throws IllegalArgumentException {
        report = _report;
        file   = _file;

        switch (format) {
            case (TXT_OUTPUT):
                renderer = new TXTOutputRenderer(_file);
                break;
            case (XML_OUTPUT):
                renderer = new XMLOutputRenderer(_file);
                break;
            case (CSV_OUTPUT):
                renderer = new CSVOutputRenderer(_file);
                break;
            default:
                throw new IllegalArgumentException("Format must be a supported type!");
        }
        renderer.addFullReport(report);
    }

    /**
     * Saves the file to disk
     * @throws IOException
     */
    public void saveFile() throws IOException {
        renderer.writeToFile();
    }

}
