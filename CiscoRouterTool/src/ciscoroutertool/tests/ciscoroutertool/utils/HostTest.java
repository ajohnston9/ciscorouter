package ciscoroutertool.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

public class HostTest {

    Host h;

    @Before
    public void init() throws Exception {
        InetAddress host = InetAddress.getByName("172.31.19.46");
        h = new Host(host, "username", "password");
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertEquals("Strings must match", h.toString(), "172.31.19.46");
    }
}