package ciscoroutertool.settings;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nu.xom.Document;

/**
 * Manages the Settings for the Application.
 * @version 0.01ALPHA
 * @author Andrew Johnston
 */
public class SettingsManager {

    private String  username;
    private String  password;
    private boolean requiresAuth;
    
    /**
     * Constructs the class. Requires settings.xml to be present to run
     */
    public SettingsManager() {
        //Open settings.xml using XOM library
        //Store relevant settings in private variables
        
    }
    
    
    /**
     * Checks if Authentication is required to use the application
     * @return true if authentication is required
     */
    public boolean requiresAuth() {
        return requiresAuth;
    }
    
    /**
     * Checks the provided credentials against stored counterparts
     * @param _username The username provided by the user
     * @param _password The password provided by the user
     * @return true if the credentials are correct
     */
    public boolean checkAuth(String _username, String _password) {
        boolean isCorrectUsername = false;
        boolean isCorrectPassword = false;
        if (username == _username) {
            isCorrectUsername = true;
        }
        try {
            if (PasswordHash.validatePassword(_password, password)) {
               isCorrectPassword = true;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(SettingsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (isCorrectUsername && isCorrectPassword);
    }
    
    public void setAuth(String _username, String _password) {
        String hashedPassword;
        try {
            hashedPassword = PasswordHash.createHash(_password);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SettingsManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(SettingsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Store _username and hashedPassword in Settings file
    }

}
