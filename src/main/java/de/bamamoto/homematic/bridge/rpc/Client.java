package de.bamamoto.homematic.bridge.rpc;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 *
 * @author barmeier
 */
public class Client {

    /**
     * Registers the local server to the homematic ccu as callback server.Use port 2010 for homematic IP, port 2001 for BidCos RF and port
 2000 for BidCos wired devices.
     * 
     * @param callbackUrl
     * @param interface_id
     * @param hostUrl
     * @param username
     * @param password
     * @throws XmlRpcException
     * @throws MalformedURLException 
     */
    
    public XmlRpcClient init(String callbackUrl, String interface_id, String hostUrl, 
            String username, String password) throws XmlRpcException, MalformedURLException {
        
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(hostUrl));
        config.setBasicUserName(username);
        config.setBasicPassword(password);
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
        Object[] params = new Object[]{callbackUrl, interface_id};
        client.execute("init", params);
        return client;
    }

//    public void getDeviceList() {
//        try {
//            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
//            config.setServerURL(new URL("http://homematic-raspi.home:2010"));
//            config.setBasicUserName("contro");
//            config.setBasicPassword("contro");
//            XmlRpcClient client = new XmlRpcClient();
//            client.setConfig(config);
//            Object[] params = new Object[]{};
////            Object result =  client.execute("system.listMethods", params);
//            Object[] result = (Object[]) client.execute("listDevices", params);
//            for (Object map : result) {
//                System.out.println(((HashMap) map).get("PARENT_TYPE") + " ==> "
//                        + ((HashMap) map).get("ADDRESS") + " ==> "
//                        + ((HashMap) map).get("LINK_SOURCE_ROLES")
//                        + " ==> " + ((HashMap) map).get("TYPE"));
//            }
//
//            params = new Object[]{"000A1A49A7A819:1", "VALUES"};
//
//            Object result2 = (Object) client.execute("getParamsetDescription", params);
//
//            for (Object x : ((HashMap) result2).keySet()) {
//                System.out.println("          " + x + " => " + ((HashMap) result2).get(x));
//            }
//
//            params = new Object[]{"000A1A49A7A819:1", "SET_POINT_TEMPERATURE"};
//            result2 = (Object) client.execute("getValue", params);
//
//            params = new Object[]{"000A1A49A7A819:1", "SET_POINT_TEMPERATURE", 19.0};
//            client.execute("setValue", params);
//
//            System.out.println(result);
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (XmlRpcException ex) {
//            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
