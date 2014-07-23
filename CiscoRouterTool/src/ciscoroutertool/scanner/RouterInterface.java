package ciscoroutertool.scanner;

import java.util.ArrayList;

/**
 *
 * @author andrew
 */
public class RouterInterface {
 
    private ArrayList<String> lines;
    
    public RouterInterface() {
        lines = new ArrayList<>();
    }
    
    public void addLine(String l) {
        lines.add(l);
    }
    
    public ArrayList<String> getLines() {
        return lines;
    }
    
}
