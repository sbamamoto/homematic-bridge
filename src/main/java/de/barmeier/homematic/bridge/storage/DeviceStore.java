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
package de.barmeier.homematic.bridge.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author barmeier
 */
public class DeviceStore {

    private static DeviceStore deviceStore;

    private HashMap<String, DeviceEntity> storage;

    private DeviceStore() {
    }

    ;
    
    public static DeviceStore getInstance() {
        if (deviceStore == null) {
            deviceStore = new DeviceStore();
            deviceStore.storage = new HashMap<>();
        }
        return deviceStore;
    }

    public void addDevice(Map<String, Object> device) {
        String address = (String) device.get("ADDRESS");
        if (address.contains(":")) {                                             // this is a child address
            String parts[] = address.split((":"));
            DeviceEntity de = storage.get(parts[0]);
            de.addChildren(device);
        } else {
            DeviceEntity de = storage.get(address);
            if (de == null) {
                de = new DeviceEntity();
                de.setAddress(address);
                de.setType((String) device.get("TYPE"));
                de.setParamSet(new ArrayList<Object>(Arrays.asList((Object[]) device.get("PARAMSETS"))));
                storage.put(address, de);
            }
        }
    }
    
    public List<DeviceEntity> getDevices() {
        List<DeviceEntity> devices = new ArrayList<>();
        storage.forEach((address, deviceEntity) -> devices.add(deviceEntity));
        return devices;
    }
    
}
