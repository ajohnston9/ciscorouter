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
    private int         connectMethod = 1;
    
    public static final int SSH_CONNECTION    = 1;
    public static final int SNMP_CONNECTION   = 2;
    public static final int TELNET_CONNECTION = 3;
    
    public Host(InetAddress ip, int method) {
        ip_addr       = ip;
        connectMethod = method;
    }
    
    public InetAddress getAddress() {
        return ip_addr;
    }
    
    public int getConnectionMethod() {
        return connectMethod;
    }

}
