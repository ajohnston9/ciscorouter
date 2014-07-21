/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ciscoroutertool.utils;

import java.net.InetAddress;

/**
 * Holds the host-specific information
 * @version 0.01ALPHA
 * @author Andrew Johnston
 */
public class Host {

    private InetAddress ip_addr;
    private String      user;
    private String      pass;
    
    
    public Host(InetAddress ip, String _user, String _pass) {
        ip_addr       = ip;
        user = _user;
        pass = _pass;
    }
    
    public InetAddress getAddress() {
        return ip_addr;
    }
    
    public String getUser() {
        return user;
    }
    
    public String getPass() {
        return pass;
    }

}
