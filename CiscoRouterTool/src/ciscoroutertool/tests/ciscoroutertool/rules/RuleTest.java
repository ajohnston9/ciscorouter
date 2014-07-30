package ciscoroutertool.rules;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class RuleTest {

    @Test
    public void ruleShouldMatchSingleLine() throws Exception {
        String[] sett  = {"enable secret "};
        String[] param = {"cisco"};
        Rule testrule = new Rule("Sample Rule", "Sample Description", "High", sett, param);
        String sampleLineShouldMatch    = "enable secret cisco";
        String sampleLineShouldNotMatch = "enable secret supersecretpassword";
        assertTrue("This line should match", testrule.matchesRule(sampleLineShouldMatch));
        assertFalse("This line should not match", testrule.matchesRule(sampleLineShouldNotMatch));
    }

    @Test
    public void ruleShouldFindMatchInConfig() throws Exception {
        String[] sett  = {"enable secret "};
        String[] param = {"cisco"};
        Rule testrule = new Rule("Sample Rule", "Sample Description", "High", sett, param);
        //Start off with two "Config" files
        String sampleConfigShouldMatch    = "interface whatever\n sample s\n test 5\nenable secret cisco\ntest here";
        String sampleConfigShouldNotMatch = "interface whatever\n sample s\n test 5\nenable secret password\ntest here";
        //Convert to arrays, turn arrays into ArrayLists
        String[] shouldMatchArr    = sampleConfigShouldMatch.split("\n");
        String[] shouldNotMatchArr = sampleConfigShouldNotMatch.split("\n");
        ArrayList<String> shouldMatch    = new ArrayList<>();
        ArrayList<String> shouldNotMatch = new ArrayList<>();
        Collections.addAll(shouldMatch, shouldMatchArr);
        Collections.addAll(shouldNotMatch, shouldNotMatchArr);
        //Perform the assertions
        assertTrue("Rule should match this config file", testrule.matchesRule(shouldMatch));
        assertFalse("Rule should not match this config file", testrule.matchesRule(shouldNotMatch));
    }

    @Test
    public void multipleRulesAllMustMatch() throws Exception {
        String[] sett  = {"enable secret ", " sample "};
        String[] param = {"cisco", "s"};
        Rule testrule = new Rule("Sample Rule", "Sample Description", "High", sett, param);
        //Start off with two "Config" files
        String sampleConfigShouldMatch    = "interface whatever\n sample s\n test 5\nenable secret cisco\ntest here";
        String sampleConfigShouldNotMatch = "interface whatever\n sample s\n test 5\nenable secret password\ntest here";
        //Convert to arrays, turn arrays into ArrayLists
        String[] shouldMatchArr    = sampleConfigShouldMatch.split("\n");
        String[] shouldNotMatchArr = sampleConfigShouldNotMatch.split("\n");
        ArrayList<String> shouldMatch    = new ArrayList<>();
        ArrayList<String> shouldNotMatch = new ArrayList<>();
        Collections.addAll(shouldMatch, shouldMatchArr);
        Collections.addAll(shouldNotMatch, shouldNotMatchArr);
        //Perform the assertions
        assertTrue("Rule should match this config file", testrule.matchesRule(shouldMatch));
        assertFalse("Rule should not match this config file", testrule.matchesRule(shouldNotMatch));
    }
}