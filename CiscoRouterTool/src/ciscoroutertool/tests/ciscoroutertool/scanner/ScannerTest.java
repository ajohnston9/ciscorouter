package ciscoroutertool.scanner;

import ciscoroutertool.rules.Rule;
import ciscoroutertool.utils.Host;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ScannerTest {

    String[] sett = new String[1];
    String[] param = new String[1];
    ArrayList<Rule> rule;
    Host testHost;


    @Before
    public void init() {
        sett[0] = "(.*)";
        param[0] = "(.*)";
        rule = new ArrayList<>();
        rule.add(new Rule("Test rule", "Sample Description", "Low", sett, param));
        try {
            testHost = new Host(InetAddress.getByName("ASK_ANDREW_FOR_TEST_HOST"), "johnston", "ASK_ANDREW_FOR_PASSWORD");
        } catch (UnknownHostException e) {
            Assert.fail();
        }
    }

    @Test
    public void testCall() throws Exception {
        Scanner.rules = rule;
        Scanner scan = new Scanner(testHost);
        HostReport test = scan.call();
        Assert.assertNotNull("Report should not be null!", test);
        Rule matched = null;
        try {
            matched = test.getMatchedRules().get(0);
        } catch (IndexOutOfBoundsException e) {
            Assert.assertNotNull("Rule should have matched config!", matched);
        }
        Assert.assertNotNull("Rule should have matched config!", matched);
    }
}