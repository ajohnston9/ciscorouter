package ciscoroutertool.settings;

import org.junit.Assert;
import org.junit.Test;

public class SettingsManagerTest {

    //NOTE: Should make username + password public beforehand or create test settings file
    @Test
    public void testCheckAuth() throws Exception {
        SettingsManager manager = new SettingsManager();
        //manager.username = "testUsername";
        //manager.password = "testPassword";
        Assert.assertTrue("Correct credentials should return true", manager.checkAuth("testUsername", "testPassword"));
        Assert.assertFalse("Bad username should return false", manager.checkAuth("badUsername", "testPassword"));
        Assert.assertFalse("Bad password should return false", manager.checkAuth("testUsername", "wrongPassword"));
    }
}