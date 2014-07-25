/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ciscoroutertool.scanner;

import ciscoroutertool.rules.Rule;
import ciscoroutertool.utils.Host;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author andrew
 */
public class HostReport {
    
    private ArrayList<Rule> matchedRules;
    private Host host;
    
    public HostReport(Host h) {
        host = h;
        matchedRules = new ArrayList<Rule>();
    }
    
    public void addMatchedRule(Rule r) {
        matchedRules.add(r);
    }
    
    public ArrayList<Rule> getMatchedRules() {
        return matchedRules;
    }
    
 
    
}
