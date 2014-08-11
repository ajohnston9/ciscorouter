package ciscoroutertool.rules;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Holds the rules that config files are scanned against
 * @version 0.01ALPHA
 * @author Andrew Johnston
 */
public class Rule {

    /**
     * The name of the Rule
     */
    private String   name;

    /**
     * The description of what the rule looks for
     */
    private String   description;

    /**
     * The severity of the vulnerability should the rule match
     */
    private String   severity;

    /**
     * An array of settings to look for in a rule file
     */
    private String[] settings;

    /**
     * An array of parameters to look for should a setting match
     */
    private String[] params;

    /**
     * An array of pattern objects used to do pattern matching (formed from the setting array)
     */
    private Pattern[] settPattern;

    /**
     * An array of pattern objects used to do pattern matching (formed from the parameter array)
     */
    private Pattern[] paramPattern;

    /**
     * Constructs the rule with all relevant information
     * @param _name The name of the rule (presented to the user on match)
     * @param _desc The description of what the rule looks for (presented to the user on match)
     * @param _sev The severity of a rule match
     * @param _sett An array of settings to look for
     * @param _params An array of parameters to check should a setting be found.
     */
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

    /**
     * Returns true if a rule matches a specific line
     * @param config The line to check
     * @return true if the rule matches the line
     * @return false if the rule does not match the line
     */
    public boolean matchesRule(String config) {
        //Needs to match every rule in the ruleset
        boolean[] matches = new boolean[settings.length];
        String[] configLines = config.split("\n"); 
        for (String line : configLines) {
            line = line.trim();
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

    /**
     * Given a config file, will return true should the rule match the file
     * @param config The ArrayList of lines in the config file
     * @return true if the rule matches
     * @return false if the rule doesn't match
     */
    public boolean matchesRule(ArrayList<String> config) {
        boolean[] matches = new boolean[settings.length];
        int i = 0; //shows which part of the rule we're testing
        for (String line : config) {
            line = line.trim();
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

    /**
     * Returns the name of the Rule
     * @return the name of the rule as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the Rule
     * @return the description of the rule as a String
     */
    public String getDescription() {
        return description;
    }
}
