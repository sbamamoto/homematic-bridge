package de.bamamoto.homematic.bridge.rpc;

import de.bamamoto.homematic.bridge.processing.NewDevicesProcessor;
import de.bamamoto.homematic.bridge.storage.ConfigurationStorage;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author barmeier
 */
public class LogicLayer {

    private final static Object semaphore = new Object();

    Map<String, String> localState;

    public Object[] event(String interface_id, String address, String value_key, Boolean value) {
        PropertiesConfiguration configuration = ConfigurationStorage.getInstance("").getConfig();
        System.out.println(" EVENT RECEIVED " + address + " ValueID: " + value_key + "  Value: " + value);
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(new ClientConfig().register(config));
        WebTarget webTarget = client.target(configuration.getString("RESTForwardServerURL","http://localhost:8080")).path("homematic");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.entity("{\"address\":\""
                + address + "\",\"key\":\""
                + value_key + "\",\"value\":\""
                + value + "\"}", MediaType.APPLICATION_JSON));
        return new Object[]{};
    }

    public Object[] event(String interface_id, String address, String value_key, Integer value) {
        System.out.println(" Integer EVENT RECEIVED " + address + " ValueID: " + value_key + "  Value: " + value);
        return new Object[]{};
    }

    public Object[] listDevices(String interface_id) {
        System.out.println("   #### listDevices called");
        return new Object[]{};
    }

    public Object[] listMethods(String interface_id) {
        System.out.println("   #### listMethods called");
        return new Object[]{};
    }
    
    public Object[] newDevices(String interface_id, Object[] dev_descriptions) {
        System.out.println("   #### new devices called" + dev_descriptions + " ---- " + interface_id);
        NewDevicesProcessor ndp = new NewDevicesProcessor(dev_descriptions, interface_id);
        ndp.start();
        return new Object[]{};
    }

    public Object[] deleteDevices(String interface_id, String[] addresses) {
        System.out.println("   #### delete devices called");
        for (String ad : addresses) {
            System.out.println("delete: " + ad);
        }
        return new Object[]{};
    }

    public Object[] updateDevice(String interface_id, String address, int hint) {
        System.out.println("   #### update device called" + address);
        return new Object[]{};
    }

    public Object[] replaceDevice(String interface_id, String oldDeviceAddress, String newDeviceAddress) {
        System.out.println("   #### replace device called" + newDeviceAddress);
        return new Object[]{};
    }

    public Object[] readdedDevice(String interfaceId, String[] addresses) {
        System.out.println("   #### new devices called");
        for (String ad : addresses) {
            System.out.println(" new: " + ad);
        }
        return new Object[]{};
    }

    public Object[] multicall(Object[] objs) {
        synchronized (semaphore) {
            if (localState == null) {
                localState = new HashMap<>();
            }
            String stateName = "";
            String stateType = "";
            String stateValue = "";

            for (int i = 0; i < objs.length; ++i) {
                Object obj = objs[i];

                try {
                    Map<String, Object> m = (Map) obj;
                    String childName = (String) m.get("methodName");
                    Object[] childParams = (Object[]) m.get("params");
                    System.out.println(" +++ " + childName);
                    for (Object p : childParams) {
                        System.out.println("  --> " + p.toString());
                    }
                    if (localState.containsKey(childParams[1] + "-" + childParams[2])
                            && localState.get(childParams[1] + "-" + childParams[2]).equals(childParams[3])) {
                        System.out.println(" State has not changed: " + localState.get(childParams[1] + "-" + childParams[2]));
                    } else {
                        localState.put(childParams[1] + "-" + childParams[2], childParams[3].toString());
                        ClientConfig config = new ClientConfig();
                        Client client = ClientBuilder.newClient(new ClientConfig().register(config));
                        PropertiesConfiguration configuration = ConfigurationStorage.getInstance("").getConfig();
                        WebTarget webTarget = client.target(configuration.getString("RESTForwardServerURL","http://localhost:8080")).path("homematic");
                        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
                        Response response = invocationBuilder.post(Entity.entity("{\"address\":\""
                                + childParams[1] + "\",\"key\":\""
                                + childParams[2] + "\",\"value\":\""
                                + childParams[3] + "\"}", MediaType.APPLICATION_JSON));
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }

            }
        }
        return objs;
    }
}
