package ciscoroutertool.scanner.parser;

import java.util.ArrayList;

/**
 *
 * @author andrew
 */
public class RouterInterface extends RouterSetting{
 
    private ArrayList<String> lines;
    private String ifaceName;
    
    public RouterInterface() {
        lines = new ArrayList<>();
        ifaceName = null;
    }
    
    public RouterInterface(String name) {
        super(); //Call default
        ifaceName = name;
        lines = new ArrayList<>();
    }
    
    public void setName(String name) {
        ifaceName = name;
    }
    
    public String getName() {
        return ifaceName;
    }
    
    public void addLine(String l) {
        lines.add(l);
    }
    
    public ArrayList<String> getLines() {
        return lines;
    }
    
    public boolean isShutdown() {
        for (String line : lines) {
            if (line.matches("(\\s)shutdown")) {
                return true;
            }
        }
        return false;
    }
    
}
