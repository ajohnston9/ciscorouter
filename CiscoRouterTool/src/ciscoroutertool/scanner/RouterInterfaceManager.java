package ciscoroutertool.scanner;

import java.util.ArrayList;

/**
 * Parses the lines into interfaces and holds specific interfaces
 * @version 0.01ALPHA
 * @author Andrew H. Johnston
 */
public class RouterInterfaceManager {
    
    public RouterInterfaceManager() {
        
    }
    
    public static ArrayList<RouterInterface> getInterfaces(ArrayList<String> lines) {
        ArrayList<RouterInterface> interfaces = new ArrayList<>();
        int current = 0;
        /**
         * FIXME: What about global settings defined before/after interface
         * definitions?
         */
        interfaces.add(new RouterInterface());
        for (String line : lines) {
            //If it's not a new interface
            if (!line.contains("^interface")) {
                interfaces.get(current).addLine(line);
            }
            current++;
            interfaces.add(current, new RouterInterface());
            
        }
        return interfaces;
    }
    
}
