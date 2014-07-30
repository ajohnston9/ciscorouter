package ciscoroutertool.rules;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Holds the rules that config files are scanned against
 * @author Andrew Johnston
 */
public class Rule {
    
    private String   name;
    private String   description;
    private String   severity;
    private String[] settings;
    private String[] params;
    private Pattern[] settPattern;
    private Pattern[] paramPattern;
    
    public Rule(String _name, String _desc, String _sev, String[] _sett, String[] _params) {
        name        = _name;
        description = _desc;
        severity    = _sev;
        settings    = _sett;
        params      = _params;
        settPattern = new Pattern[settings.length];
        paramPattern = new Pattern[params.length];
        for (int i = 0; i < settings.length; i++) {
            settPattern[i]  = Pattern.compile(settings[i] + "(.*)");
            paramPattern[i] = Pattern.compile("(.*)" + params[i]);
        }
    }
    
    public boolean matchesRule(String config) {
        //Needs to match every rule in the ruleset
        boolean[] matches = new boolean[settings.length];
        String[] configLines = config.split("\n"); 
        for (String line : configLines) {
            for (int i = 0; i < settings.length; i++) {
                Matcher matchSetting = settPattern[i].matcher(line);
                Matcher matchParam   = paramPattern[i].matcher(line);
                //Since matches() must match whole string, this
                //will ensure a good match succeeds
                if (matchSetting.matches()) {
                    //Check if it matches the param
                    if (matchParam.matches()) {
                        matches[i] = true;
                    }
                }
            }
        }
        //Every rule must matches for it to be a match
        boolean allTrue = true;
        for (boolean b : matches) {
            //AND the bools together, must all be true for allTrue to stay true
            allTrue = allTrue && b;
        }
        return allTrue;
    }
    
    public boolean matchesRule(ArrayList<String> config) {
        boolean[] matches = new boolean[settings.length];
        int i = 0; //shows which part of the rule we're testing
        for (String line : config) {
            if (line.matches(settings[i] + "(.*)")) {
               //Check if it matches the param
               if (line.matches("(.*)" + params[i])) {
                   matches[i] = true;
                   i++; //Move to next part of rule
                   if (i >= settings.length) {
                       break;
                   }
               }
           }           
        }
        //Make sure all parts of the rule are satisfied
        boolean allTrue = true;
        for (boolean b : matches) {
            //AND the bools together, must all be true for allTrue to stay true
            allTrue = allTrue && b;
        }
        return allTrue;       
    }
    
}
