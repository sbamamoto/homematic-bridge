package de.bamamoto.homematic.bridge;

import de.bamamoto.homematic.bridge.rest.HomematicRestServer;
import de.bamamoto.homematic.bridge.rpc.Client;
import de.bamamoto.homematic.bridge.rpc.Server;
import de.bamamoto.homematic.bridge.rpc.SessionStore;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.xmlrpc.XmlRpcException;

/**
 *
 * @author barmeier
 */
public class HmcBridge {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ConfigurationException {
        try {
            Options options = new Options();
            options.addOption("c", "config-file", true, "Absolute path to the HmcBridge configuration file");
            options.addOption("h", "help", false, "Shows this help");
            
            System.out.println(" *** Starting Contro <-> HomeMatic Bridge\n");
            
            
            CommandLineParser parser = new BasicParser();
            CommandLine cmd = parser.parse(options, args);

            PropertiesConfiguration configs = new PropertiesConfiguration();
            
            File configFile = new File (System.getProperty("user.home"),".hmcbridge/bridge.properties");
            if (!configFile.exists()) {
                if (cmd.hasOption('c')) {
                    configFile = new File(cmd.getOptionValue('c'));
                } else {
                    System.out.println("    ### Cannot find any configuration. ");
                    System.out.println("    ### whether "+ System.getProperty("user.home")+".hmcbridge/bridge.properties can be found");
                    System.out.println("    ### nor is a config file set via -c option. ");
                    System.out.println("Exiting.");
                    System.exit(0);
                }                
            }           
            configs.load(configFile);
            
            List<Object> ccus = configs.getList("CCUAddress");
            
            Server server = Server.getInstance(configs.getString("XMLRPCServerURL"));


            System.out.println("    RPC Server gestartet");
            Thread.sleep(200);
            
            int count = 0;
            SessionStore sessionStore = SessionStore.getInstance();
            
            for (Object ccuAddress : ccus) {
                Client client = new Client();
                // Register for homematic IP devices
                String id = configs.getString("IdPrefix", "default")+"-"+count;
                sessionStore.addClient(id, client.init(configs.getString("XMLRPCServerURL"),
                        id, 
                        (String)ccuAddress,
                        configs.getString("Username"),
                        configs.getString("Password")));
                count++;
            }
            // Register for BidCos RF devices
            //client.init("http://192.168.0.77:9090", "e27801c6-11c0-4198-88ee-71c5e2d75701", "http://homematic-raspi.home:2001");

            HomematicRestServer hap = new HomematicRestServer();
            hap.start(configs.getString("RESTRPCGateway"));
            hap.waitForTermination();
            System.out.println("Terminated ...");
        } catch (ServletException ex) {
            Logger.getLogger(HmcBridge.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HmcBridge.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(HmcBridge.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XmlRpcException ex) {
            Logger.getLogger(HmcBridge.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(HmcBridge.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

}
