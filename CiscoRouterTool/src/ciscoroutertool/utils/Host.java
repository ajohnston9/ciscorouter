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
    private boolean     usesEnable = false;
    private String      enablePass;

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

    public String getEnablePass() {
        return enablePass;
    }

    public void setEnablePass(String enablePass) {
        this.enablePass = enablePass;
    }

    public boolean usesEnable() {
        return usesEnable;
    }

    public void setEnable(boolean usesEnable) {
        this.usesEnable = usesEnable;
    }

    public String toString() {
        String hosts = ip_addr.toString();
        int c = hosts.indexOf("/");
        hosts = hosts.substring((c + 1));
        return hosts;
    }

}
