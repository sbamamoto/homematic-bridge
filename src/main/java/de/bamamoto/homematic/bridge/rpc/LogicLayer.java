package de.bamamoto.homematic.bridge.rpc;

import de.bamamoto.homematic.bridge.processing.NewDevicesProcessor;
import java.util.Map;

/**
 *
 * @author barmeier
 */
public class LogicLayer {

    private final static Object semaphore = new Object();
    
    public void event(String interface_id, String address, String value_key, Object value) {
        System.out.println(" EVENT RECEIVED " + address + " ValueID: " + value_key + "  Value: " + value);
    }

    public Object[] listDevices(String interface_id) {
        System.out.println("   #### listDevices called");
        return new Object[]{};
    }

    public Object[] newDevices(String interface_id, Object[] dev_descriptions) {
        System.out.println("   #### new devices called" + dev_descriptions);
        NewDevicesProcessor ndp = new NewDevicesProcessor(dev_descriptions);
        ndp.start();
        return new Object[]{};
    }

    public void deleteDevices(String interface_id, String[] addresses) {
        System.out.println("   #### delete devices called");
        for (String ad : addresses) {
            System.out.println("delete: " + ad);
        }
    }

    public void updateDevice(String interface_id, String address, int hint) {
        System.out.println("   #### update device called" + address);
    }

    public void replaceDevice(String interface_id, String oldDeviceAddress, String newDeviceAddress) {
        System.out.println("   #### replace device called" + newDeviceAddress);
    }

    public void readdedDevice(String interfaceId, String[] addresses) {
        System.out.println("   #### new devices called");
        for (String ad : addresses) {
            System.out.println(" new: " + ad);
        }
    }

    public Object[] multicall(Object[] objs) {
        synchronized (semaphore) {
            String stateName = "";
            String stateType = "";
            String stateValue = "";

            for (int i = 0; i < objs.length; ++i) {
                Object obj = objs[i];

                try {
                    Map<String, Object> m = (Map) obj;
                    String childName = (String) m.get("methodName");
                    Object[] childParams = (Object[]) m.get("params");

                    for (Object p : childParams) {
                        System.out.println("  --> " + p.toString());
                    }
                    System.out.println("");

//                for(int x = 0; x < childParams.length; ++x) {
//                    switch(x) {
//                        case 1:
//                            stateName = childParams[x].toString();
//                            break;
//                        case 2:
//                            stateType = childParams[x].toString();
//                            break;
//                        case 3:
//                            stateValue = childParams[x].toString();
//                    }
//                }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return objs;
    }
}
