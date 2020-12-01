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
package de.bamamoto.homematic.bridge.rpc;

import java.util.HashMap;
import java.util.Map;
import org.apache.xmlrpc.client.XmlRpcClient;

/**
 *
 * @author barmeier
 */
public class SessionStore {

    private static SessionStore sessionStore;
    private static Map<String,XmlRpcClient> store;
    
    private SessionStore() {
        store = new HashMap<>();
    }
    
    public static SessionStore getInstance () {
        if (sessionStore == null) {
            sessionStore = new SessionStore();
        }
        return sessionStore;
    }
    
    public void addClient(String interfaceId, XmlRpcClient client) {
        store.put(interfaceId, client);
    }
    
    public XmlRpcClient getClient (String interfaceId) {
        return store.get(interfaceId);
    }
    
}
