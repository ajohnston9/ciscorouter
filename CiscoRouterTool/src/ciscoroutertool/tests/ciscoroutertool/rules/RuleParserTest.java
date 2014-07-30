package ciscoroutertool.rules;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class RuleParserTest {

    @Test
    public void shouldSuccessfullyParseValidRule() throws Exception {
        //Use location of known test file
        File f = new File("/home/andrew/NetBeansProjects/CiscoRuleTool/ciscorule/test-all.xml");
        Rule r = RuleParser.getRuleFromFile(f);
        Assert.assertNotNull("Rule returned empty object", r);
    }

}