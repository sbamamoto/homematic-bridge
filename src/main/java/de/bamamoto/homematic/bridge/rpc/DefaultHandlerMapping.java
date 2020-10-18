package de.bamamoto.homematic.bridge.rpc;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcHandler;
import org.apache.xmlrpc.server.PropertyHandlerMapping;

/**
 *
 * @author barmeier
 */
public class DefaultHandlerMapping extends PropertyHandlerMapping {

        public XmlRpcHandler getHandler(String pHandlerName) throws XmlRpcException {

            //System.out.println("somebody asked for a handler for " + pHandlerName);

            XmlRpcHandler result = null;

            try {
                result = super.getHandler(pHandlerName);
            } catch (Exception ex) {
                //ignore
            }

            if (result == null) {
                result = super.getHandler("system." + pHandlerName);
            }

            if (result == null) {
                System.out.println("no handler found: " + pHandlerName);
            }

            return result;
        }
}
