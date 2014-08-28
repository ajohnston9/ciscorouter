package ciscoroutertool.rules;

import nu.xom.*;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses rule definitions and returns a list of rules to the system
 * @version 0.01ALPHA
 * @author Andrew Johnston
 */
public class RuleParser {

    /**
     * Loads rules from rules/ directory and returns them as cleaned Rule objects in an ArrayList
     * @return an ArrayList of rules
     */
    public static ArrayList<Rule> getRules() {
        ArrayList<Rule> rules = new ArrayList<>();
        //Get the current working directory
        String cwd = System.getProperty("user.dir");
        String xmlDirectory = cwd + "/rules";
        File xmlFolder = new File(xmlDirectory);
        File[] ruleFiles = xmlFolder.listFiles();
        if (ruleFiles != null) {
            for (File rule : ruleFiles) {
                if (rule.isFile()) {
                    Rule r = getRuleFromFile(rule);
                    rules.add(r);
                }
            }
        }
        if (rules.size() == 0) {
            System.err.println("ERROR: No rules found. Scan will fail.");
        }
        return rules;
    }

    /**
     * Returns a Rule object from a given file
     * @param f The file to load the rule from
     * @return A Rule object
     */
    public static Rule getRuleFromFile(File f) {
        Rule r = null;
        try {
            Builder parser = new Builder();
            Document doc = parser.build(f);
            
            Element root = doc.getRootElement();
            
            Element name = root.getFirstChildElement("Name");
            String nameVal = name.getValue();
            
            Element desc = root.getFirstChildElement("Description");
            String descVal = desc.getValue();
            
            Element severity = root.getFirstChildElement("Severity");
            String sevVal = severity.getValue();
            
            Element ruledef = root.getFirstChildElement("Rules");
            Elements rules = ruledef.getChildElements();
            
            String[] settings = new String[rules.size()];
            String[] params   = new String[rules.size()];
            for (int i = 0; i < rules.size(); i++) {
                Element rule = rules.get(i);
                Element sett = rule.getFirstChildElement("Parameter");
                settings[i] = StringEscapeUtils.unescapeXml(sett.getValue());
                
                Element arg  = rule.getFirstChildElement("Argument");
                params[i] = StringEscapeUtils.unescapeXml(arg.getValue());
            }
            r = new Rule(StringEscapeUtils.unescapeXml(nameVal), StringEscapeUtils.unescapeXml(descVal), sevVal, settings, params);
        } catch (ParsingException | IOException ex) {
            Logger.getLogger(RuleParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    
}
