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


package de.barmeier.homematic.bridge.processing;

import de.barmeier.homematic.bridge.gateway.ControGateway;
import de.barmeier.homematic.bridge.storage.DeviceEntity;
import de.barmeier.homematic.bridge.storage.DeviceStore;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author barmeier
 */
public class NewDevicesProcessor extends Thread {

    private final Object[] data;
    
    public NewDevicesProcessor (Object[] data) {
        this.data = data;
    }
    
    @Override
    public void run() {
        // updateing device store
        
        DeviceStore ds = DeviceStore.getInstance();
        for (Object o : data) {
            ds.addDevice((Map<String,Object>)o);            
        }
        
        // adding devices to contro
        ControGateway cgw = new ControGateway();
        for (DeviceEntity de : ds.getDevices()) {
            try {
                cgw.addDevice(de);
            } catch (IOException ex) {
                Logger.getLogger(NewDevicesProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
            
    
}
