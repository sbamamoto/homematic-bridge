package de.bamamoto.homematic.bridge.rest;

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
    public String setValue ( @QueryParam("address") String address, @QueryParam("key") String key, @QueryParam("value") String value ) {
        Object x;
        try {
            
            // TODO: Input parameter validation
            
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL("http://homematic-raspi:2010"));
            config.setBasicUserName("contro");
            config.setBasicPassword("contro");
            XmlRpcClient client = new XmlRpcClient();
            client.setConfig(config);
            double doubleValue = Double.parseDouble(value);
            Object[] params = {address, key, doubleValue};   //needed to be the right data type !!!
             x = client.execute("setValue", params);
            return "OK";
        } catch (MalformedURLException | XmlRpcException ex) {
            Logger.getLogger(HomematicApi.class.getName()).log(Level.SEVERE, null, ex);
            return "NOK";
        }

    }
    
}
