/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ciscoroutertool.config;

import ciscoroutertool.utils.Host;
import java.io.File;
import java.util.ArrayList;

/**
 * Manages the Device Configurations. This is separate from Application Settings
 * @version 0.01ALPHA
 * @author Andrew Johnston
 */
public class ConfigurationManager {

    private ArrayList<Host> hosts;
    
    public ConfigurationManager(File config) {
        //TODO: Write Constructor for ConfigurationManager
        //Open file with XOM
        //Read in values
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
