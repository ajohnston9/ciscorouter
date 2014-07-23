package ciscoroutertool.scanner.parser;

import java.util.ArrayList;

/**
 * Holds the configuration for a router. Will automatically "clean out" interfaces
 * that aren't operational.
 * @version 0.01ALPHA
 * @author Andrew H. Johnston
 */
public class RouterConfig {
    
    private ArrayList<String> lines;
    
    public RouterConfig() {
        //Do nothing
    }
    
    public void addLine(String line) {
        lines.add(line);
    }
    
    
    
    
    
}
