package de.bamamoto.homematic.bridge;

import de.bamamoto.homematic.bridge.rest.HomematicRestServer;
import de.bamamoto.homematic.bridge.rpc.Client;
import de.bamamoto.homematic.bridge.rpc.Server;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import org.apache.xmlrpc.XmlRpcException;

/**
 *
 * @author barmeier
 */
public class HmcBridge {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println(" *** Starting Contro <-> HomeMatic Bridge\n");
            Server server = Server.getInstance();
            System.out.println("    RPC Server gestartet");
            Thread.sleep(200);
            Client client = new Client();
            String id = UUID.randomUUID().toString();
            client.init("http://192.168.0.77:9191", "e27801c6-11c0-4198-88ee-71c5e2d75700");
            HomematicRestServer hap = new HomematicRestServer();
            hap.start();
            Thread.sleep (120000000);
            System.out.println("Terminated ...");
        } catch (ServletException ex) {
            Logger.getLogger(HmcBridge.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HmcBridge.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(HmcBridge.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XmlRpcException ex) {
            Logger.getLogger(HmcBridge.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
