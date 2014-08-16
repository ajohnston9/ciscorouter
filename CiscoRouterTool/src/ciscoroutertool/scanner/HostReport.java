/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ciscoroutertool.scanner;

import ciscoroutertool.rules.Rule;
import ciscoroutertool.utils.Host;
import java.util.ArrayList;

/**
 *
 * @author andrew
 */
public class HostReport {

    /**
     * The list of rules that matched this host's config file
     */
    private ArrayList<Rule> matchedRules;
    /**
     * The host for this report
     */
    private Host host;

    /**
     * Creates a blank report for a given host
     * @param h The host for this report
     */
    public HostReport(Host h) {
        host = h;
        matchedRules = new ArrayList<>();
    }

    /**
     * Adds a matched rule to the report
     * @param r
     */
    public void addMatchedRule(Rule r) {
        matchedRules.add(r);
    }

    /**
     * Returns a list of rules that matched the host's config
     * @return an ArrayList of rules that matched the host's config file
     */
    public ArrayList<Rule> getMatchedRules() {
        return matchedRules;
    }

    /**
     * Provides access to the underlying host object
     * @return The underlying host object
     */
    public Host getHost() {
        return host;
    }
    
 
    
}
