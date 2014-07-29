package ciscoroutertool.config;

import ciscoroutertool.utils.Host;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;


/**
 * Manages the Device Configurations. This is separate from Application Settings
 * @version 0.01ALPHA
 * @author Andrew H. Johnston
 */
public class ConfigurationManager {

    /**
     * The hosts to save.
     */
    private ArrayList<Host> hosts;
    
    /**
     * Initializes the Manager by reading the configuration into memory
     * @param config The file where the configuration is stored
     */
    public ConfigurationManager(File config) {
        hosts = parseConfig(config);
    }
    
    /**
     * Turns the XML file into a list of host objects.
     * @param config The file to read from.
     * @return The list of hosts read from the file.
     */
    private ArrayList<Host> parseConfig(File config) {
        ArrayList<Host> _hosts = new ArrayList<>();
        try {
            Builder parser = new Builder();
            Document doc = parser.build(config);
            Element root = doc.getRootElement();
            Elements eHosts = root.getChildElements("Host");
            for (int i = 0; i < eHosts.size(); i++) {
                String ip, user, pass;
                Element host = eHosts.get(i);
                ip   = host.getFirstChildElement("IP").getValue();
                user = host.getFirstChildElement("Username").getValue();
                pass = host.getFirstChildElement("Password").getValue();
                hosts.add(new Host(InetAddress.getByName(ip), user, pass));
            }
            
        } catch (ParsingException | IOException ex) {
            Logger.getLogger(ConfigurationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return _hosts;
    }
    
    /**
     * Retrieves the hosts stored in the file.
     * @return The list of hosts in the configuration file
     */
    public ArrayList<Host> getAllHosts() {
        return hosts;
    }
    
    /**
     * Checks if there are saved hosts in the configuration file
     * @return true if there are hosts in the config file
     */
    public boolean hasHosts() {
        return !hosts.isEmpty();
    }
    
    /**
     * Add a host to the configuration file.
     * @param h The host to be added.
     */
    public void addHost(Host h) {
        hosts.add(h);
    }

    /**
     * Saves the updated configuration to the specified file.
     */
    public void updateConfiguration() {
        //TODO: Save config to file
    }

}
