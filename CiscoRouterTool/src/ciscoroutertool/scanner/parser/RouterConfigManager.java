package ciscoroutertool.scanner.parser;

import ciscoroutertool.scanner.parser.RouterInterface;
import java.util.ArrayList;

/**
 * Parses the lines into interfaces and holds specific interfaces
 * @version 0.01ALPHA
 * @author Andrew H. Johnston
 */
public class RouterConfigManager {
    
    public RouterConfigManager() {
        
    }
    
    public static ArrayList<String> getActiveConfig(ArrayList<String> lines) {
        ArrayList<String> activeLines = new ArrayList<>();
        ArrayList<RouterInterface> interfaces = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            //Ignore comments
            if (line.matches("(\\s+)?!(.*)?")) {
                continue;
            }
            //Put interfaces into its own thing
            if (line.matches("^interface\\s(.*)")) {
                RouterInterface iface = new RouterInterface(line);
                String nline = null;
                i++; //Increment counter
                while ((nline = lines.get(i)) != null && !nline.matches("!") 
                        && !nline.matches("^interface\\s(.*)")) {
                    iface.addLine(nline);
                    i++;
                }
                interfaces.add(iface);
            }
            //Its not an interface, process normally
            activeLines.add(line);
        }
        for (RouterInterface iface : interfaces) {
            if (!iface.isShutdown()) {
                activeLines.addAll(iface.getLines());
            }
        }
        return activeLines;
    }
    
    public static ArrayList<RouterInterface> getInterfaces(ArrayList<String> lines) {
        ArrayList<RouterInterface> interfaces = new ArrayList<>();
        int current = 0;
        /**
         * FIXME: What about global settings defined before/after interface
         * definitions?
         * NOTE: Must check 
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
