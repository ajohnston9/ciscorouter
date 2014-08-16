package ciscoroutertool.settings;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the Settings for the Application.
 * @version 0.01ALPHA
 * @author Andrew Johnston
 */
public class SettingsManager {

    /**
     * The username to log in to the app
     */
    private String  username;

    /**
     * The password to log in to the app
     */
    private String  password;

    /**
     * The unhashed version of the username (used for forms)
     */
    private String  unhashedUsername;

    /**
     * A boolean that indicates if authentication is currently enabled.
     */
    private boolean requiresAuth;
    
    /**
     * Constructs the class. Requires settings.xml to be present to run
     */
    public SettingsManager() {
        String currentDir = System.getProperty("user.dir");
        String settingDir = currentDir + "/settings/";
        File f = new File(settingDir + "settings.xml");
        if (f.exists() && !f.isDirectory()) {
            try {
                Builder parser = new Builder();
                Document doc   = parser.build(f);
                Element root   = doc.getRootElement();

                Element eUsername = root.getFirstChildElement("Username");
                username = eUsername.getValue();

                Element ePassword = root.getFirstChildElement("Password");
                password = ePassword.getValue();

                requiresAuth = true;
            } catch (ParsingException|IOException e) {
                e.printStackTrace();
            }
        }
        else {
            requiresAuth = false;
        }
    }
    
    
    /**
     * Checks if Authentication is required to use the application
     * @return true if authentication is required
     */
    public boolean requiresAuth() {
        return requiresAuth;
    }

    /**
     * Returns the username needed to access the app
     */
    public String getUsername() {
        return unhashedUsername;
    }

    /**
     * Deletes the settings file if auth is turned off
     */
    public void removeSecurity() {
        requiresAuth = false;
        File f = new File(System.getProperty("user.dir") + "/settings/settings.xml");
        if (f.exists()) {
            try {
                Files.deleteIfExists(f.toPath());
            } catch (IOException e) {
                //Fail silently
            }
        }
    }

    /**
     * Returns the password needed to access the app
     */
    public String getPassword() {
        return password;
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
        try {
            if (PasswordHash.validatePassword(_username, username)) {
                isCorrectUsername = true;
                unhashedUsername = _username;
            }
            if (PasswordHash.validatePassword(_password, password)) {
               isCorrectPassword = true;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(SettingsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (isCorrectUsername && isCorrectPassword);
    }

    /**
     * Sets the username and password for authenticating to the application
     * @param _username the username to save
     * @param _password the password to save
     */
    public void setAuth(String _username, String _password) {
        String hashedPassword;
        String hashedUsername;
        try {
            hashedPassword = PasswordHash.createHash(_password);
            hashedUsername = PasswordHash.createHash(_username);
            File settingsFile = new File(System.getProperty("user.dir") + "/settings/settings.xml");
            FileWriter fw = new FileWriter(settingsFile, false);

            Element root = new Element("Settings");

            Element user = new Element("Username");
            user.appendChild(hashedUsername);
            root.appendChild(user);

            Element pass = new Element("Password");
            pass.appendChild(hashedPassword);
            root.appendChild(pass);

            Document doc = new Document(root);
            fw.write(doc.toXML());
            fw.flush();
            fw.close();

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException ex) {
            Logger.getLogger(SettingsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        requiresAuth = true;
        username = _username;
        password = _password;
    }

}
