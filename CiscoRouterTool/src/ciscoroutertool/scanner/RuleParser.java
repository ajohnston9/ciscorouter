package ciscoroutertool.scanner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

/**
 *
 * @author Andrew Johnston
 */
public class RuleParser {
    
    public static ArrayList<Rule> getRules() {
        ArrayList<Rule> rules = new ArrayList<Rule>();
        //Get the current working directory
        String cwd = System.getProperty("user.dir");
        String xmlDirectory = cwd + "/xml";
        File xmlFolder = new File(xmlDirectory);
        File[] ruleFiles = xmlFolder.listFiles();
        for (File rule : ruleFiles) {
            if (rule.isFile()) {
                Rule r = getRuleFromFile(rule);
                rules.add(r);
            }
        }
        return rules;
    }
    
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
                settings[i] = sett.getValue();
                
                Element arg  = rule.getFirstChildElement("Argument");
                params[i] = arg.getValue();
            }
            r = new Rule(nameVal, descVal, sevVal, settings, params);
        } catch (ParsingException | IOException ex) {
            Logger.getLogger(RuleParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    
}
