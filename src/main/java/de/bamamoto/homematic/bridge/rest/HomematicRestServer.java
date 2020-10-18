package de.bamamoto.homematic.bridge.rest;


import java.io.IOException;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author barmeier
 */
public class HomematicRestServer {

    HttpServer server;
    
    public void start () throws IOException {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
        ResourceConfig config = new ResourceConfig(HomematicApi.class);
        server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
    }
    
    public void stop() {
        server.shutdownNow();
    }
    
}
