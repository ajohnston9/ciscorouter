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

    private FullReport report;
    private File file;
    private AbstractOutputRenderer renderer;

    public final static int TEXT_OUTPUT = 0;
    public final static int XML_OUTPUT  = 1;
    public final static int CSV_OUTPUT  = 2;

    public OutputManager(FullReport _report, File _file, int format) throws IllegalArgumentException {
        report = _report;
        file   = _file;

        switch (format) {
            case (TEXT_OUTPUT):
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

    public void saveFile() throws IOException {
        renderer.writeToFile();
    }

}
