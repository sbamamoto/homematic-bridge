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


package de.barmeier.homematic.bridge.rest;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;

/**
 *
 * @author barmeier
 */
public class HomematicRestServer {

    HttpServer server;
    
    public void start () throws IOException {
        server = HttpServerFactory.create( "http://localhost:9292/rest" );
        server.start();
    }
    
    public void stop() {
        server.stop(0);
    }
    
}
