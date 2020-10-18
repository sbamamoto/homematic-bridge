package de.bamamoto.homematic.bridge.rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

/**
 *
 * @author barmeier
 */
public class Server {

    private static Server server;
    private static int port = 9090;
    protected WebServer webServer;

    private Server() {
        port = 9090;
    }

    public static Server getInstance() throws ServletException, IOException, XmlRpcException {
        if (server == null) {
            server = new Server();
//            XmlRpcServlet servlet = new XmlRpcServlet();
//            // this object is used to configure the xml rpc server
//            PropertyHandlerMapping phm = new PropertyHandlerMapping();
//            phm.addHandler("default", server.getClass());
//
//            // some boilerplate stuff
//            XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl) servlet.getConfig();
//            serverConfig.setEnabledForExtensions(true);
//            serverConfig.setContentLengthOptional(false);
//
//            server.webServer = new ServletWebServer(servlet, port);
//
//            // add the configuration to the xml rpc server
//            server.webServer.getXmlRpcServer().setHandlerMapping(phm);
//            server.webServer.start();

            // start a web server at a certain port, usually above 1000
            server.webServer = new WebServer(port);

            // the xml rpc server lies on top of the web server
            XmlRpcServer xmlRpcServer = server.webServer.getXmlRpcServer();

            // this object is used to configure the xml rpc server
            PropertyHandlerMapping phm = new DefaultHandlerMapping();

            // add a handler to your server
            phm.addHandler("system", LogicLayer.class);
            phm.addHandler("methods", LogicLayer.class);
            // add the configuration to the xml rpc server
            xmlRpcServer.setHandlerMapping(phm);

            // some boilerplate stuff
            XmlRpcServerConfigImpl serverConfig
                    = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
            serverConfig.setEnabledForExtensions(true);
            serverConfig.setContentLengthOptional(false);

            // start the web server
            server.webServer.start();

        }
        return server;
    }

    public void stop() {
        webServer.shutdown();
        server = null;
    }

    public boolean register(String ccuAddress, String callbackUrl) {

        return false;
    }

}

