/**
 * Copyright SOURCEPARK GmbH 2020. Alle Rechte vorbehalten.
 *
 * SOURCEPARK GmbH
 *
 * Hohenzollerndamm 150 D-14199 Berlin
 *
 * tel.: +49-30-398-068-30 fax: +49-30-398-068-39 e-mail: kontakt@sourcepark.de
 * www: www.sourcepark.de
 */


package de.bamamoto.homematic.bridge.storage;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author barmeier
 */
public class ConfigurationStorage {

    private PropertiesConfiguration config;
    private static ConfigurationStorage configStorage;
    
    private ConfigurationStorage() {
        
    }
    
    
    public static ConfigurationStorage getInstance (String configFilename) {
        if (configStorage == null) {
            try {
                configStorage = new ConfigurationStorage();
                configStorage.setConfig (new PropertiesConfiguration());
                configStorage.getConfig().load(configFilename);
            } catch (ConfigurationException ex) {
                Logger.getLogger(ConfigurationStorage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return configStorage;
    }

    
    
    /**
     * @return the config
     */
    public PropertiesConfiguration getConfig() {
        return config;
    }

    /**
     * @param config the config to set
     */
    public void setConfig(PropertiesConfiguration config) {
        this.config = config;
    }

    
}
