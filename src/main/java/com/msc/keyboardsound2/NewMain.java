package com.msc.keyboardsound2;

import com.msc.keyboardsound2.controller.EntryPoint;
import com.msc.keyboardsound2.provider.CORSProvider;
import com.sun.net.httpserver.HttpServer;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author micky
 */
public class NewMain {

    private void go(String[] args) throws Exception {
	URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
	ResourceConfig config = new ResourceConfig(EntryPoint.class, CORSProvider.class);
	HttpServer server = JdkHttpServerFactory.createHttpServer(baseUri, config);
    }

    public static void main(String[] args) throws Exception {
	new NewMain().go(args);
    }

}
