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
package de.barmeier.homematic.bridge;

import de.barmeier.homematic.bridge.rest.HomematicRestServer;
import de.barmeier.homematic.bridge.rpc.Client;
import de.barmeier.homematic.bridge.rpc.Server;
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
            Thread.sleep (120000);
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
