/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ciscoroutertool.config;

import ciscoroutertool.utils.Host;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import nu.xom.*;

/**
 * Manages the Device Configurations. This is separate from Application Settings
 * @version 0.01ALPHA
 * @author Andrew Johnston
 */
public class ConfigurationManager {

    private ArrayList<Host> hosts;
    
    public ConfigurationManager(File config) {
        //TODO: Write Constructor for ConfigurationManager
        hosts = parseConfig(config);
    }
    
    private ArrayList<Host> parseConfig(File config) {
        ArrayList<Host> hosts = new ArrayList<>();
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
        return hosts;
    }
    
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
    
    public void addHost(Host h) {
        hosts.add(h);
    }
    
    public void updateConfiguration() {
        //TODO: Save config to file
    }

}
