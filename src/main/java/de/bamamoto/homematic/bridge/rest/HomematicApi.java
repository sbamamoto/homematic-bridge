package de.bamamoto.homematic.bridge.rest;

import de.bamamoto.homematic.bridge.rpc.Client;
import de.bamamoto.homematic.bridge.rpc.SessionStore;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 *
 * @author barmeier
 */
@Path("hapi")
public class HomematicApi {

    @GET
    @Path("setvalue")
    @Produces(MediaType.TEXT_PLAIN)
    public String setValue(@QueryParam("address") String address, @QueryParam("key") String key, @QueryParam("value") Double value, @QueryParam("sessionId") String sessionId) {
        Object x;
        try {

            // TODO: Input parameter validation
            XmlRpcClient client = SessionStore.getInstance().getClient(sessionId);
            Object[] params = {address, key, value};   //needed to be the right data type !!!
            x = client.execute("setValue", params);
            return "OK";
        } catch (XmlRpcException ex) {
            Logger.getLogger(HomematicApi.class.getName()).log(Level.SEVERE, null, ex);
            return "NOK";
        } catch (Throwable ex) {
            Logger.getLogger(HomematicApi.class.getName()).log(Level.SEVERE, null, ex);
            return "NOK";
        }

    }

    @GET
    @Path("setboolean")
    @Produces(MediaType.TEXT_PLAIN)
    public String switchState(@QueryParam("address") String address, @QueryParam("key") String key, @QueryParam("value") Boolean value, @QueryParam("sessionId") String sessionId) {
        Object x;
        try {

            // TODO: Input parameter validation
            XmlRpcClient client = SessionStore.getInstance().getClient(sessionId);
            Object[] params = {address, key, value};   //needed to be the right data type !!!
            x = client.execute("setValue", params);
            Logger.getLogger(HomematicApi.class.getName()).log(Level.INFO,
                    " +++ Success on setState for ["
                    + address
                    + "] with key ["
                    + key
                    + "] value ["
                    + value
                    + "] sessionId ["
                    + sessionId
                    + "]");
            return "OK";
        } catch (XmlRpcException ex) {
            Logger.getLogger(HomematicApi.class.getName()).log(Level.SEVERE,
                    " ### Fail on setState for ["
                    + address
                    + "] with key ["
                    + key
                    + "] value ["
                    + value
                    + "] sessionId ["
                    + sessionId
                    + "]", ex);
            return "NOK";
        }

    }

    @GET
    @Path("getdevices")
    @Produces(MediaType.TEXT_PLAIN)
    public String getDevices() {
        Object x;
        try {

            // TODO: Input parameter validation
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL("http://homematic-raspi.home:2010"));
            config.setBasicUserName("contro");
            config.setBasicPassword("contro");
            XmlRpcClient client = new XmlRpcClient();
            client.setConfig(config);
            Object[] params = {};   //needed to be the right data type !!!
            x = client.execute("listDevices", params);
            return "OK";
        } catch (MalformedURLException | XmlRpcException ex) {
            Logger.getLogger(HomematicApi.class.getName()).log(Level.SEVERE, null, ex);
            return "NOK";
        }

    }

}
