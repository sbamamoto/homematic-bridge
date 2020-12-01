package de.bamamoto.homematic.bridge.processing;

import de.bamamoto.homematic.bridge.gateway.ControGateway;
import de.bamamoto.homematic.bridge.storage.DeviceEntity;
import de.bamamoto.homematic.bridge.storage.DeviceStore;
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
    private final String incomingInterface;
    
    public NewDevicesProcessor (Object[] data, String incomingInterface) {
        this.data = data;
        this.incomingInterface = incomingInterface;
    }
    
    @Override
    public void run() {
        // updateing device store
        
        DeviceStore ds = DeviceStore.getInstance();
        for (Object o : data) {
            ds.addDevice((Map<String,Object>)o, incomingInterface);            
        }
        
        // adding devices to contro
        ControGateway cgw = new ControGateway();
        for (DeviceEntity de : ds.getDevices(incomingInterface)) {
            try {
                cgw.addDevice(de, incomingInterface);
            } catch (IOException ex) {
                Logger.getLogger(NewDevicesProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
            
    
}
