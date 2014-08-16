/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ciscoroutertool.scanner.parser;

/**
 * Holds a specific setting in a config file
 * @version 0.01ALPHA
 * @author Andrew H. Johnston
 */
public class RouterSetting {
    
    private final String line;
    
    public RouterSetting() {
        line = null;
    }
    
    public RouterSetting(String line) {
        this.line = line;
    }
    
    public String getLine() {
        return line;
    }
    
}
