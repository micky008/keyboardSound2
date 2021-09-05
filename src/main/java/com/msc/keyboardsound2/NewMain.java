package com.msc.keyboardsound2;

import com.msc.keyboardsound2.server.Config;
import com.msc.keyboardsound2.server.SoundList;
import com.msc.keyboardsound2.webservice.controller.EntryPoint;
import com.msc.keyboardsound2.webservice.provider.CORSProvider;
import com.msc.keyboardsound2.websocket.SimpleServer;
import java.net.InetSocketAddress;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.java_websocket.server.WebSocketServer;

/**
 *
 * @author micky
 */
public class NewMain {

    private void go(String[] args) throws Exception {        
        Config config = Config.getConfig("config.json");
        new SoundList(config); //init la liste static 
        URI baseUri = UriBuilder.fromUri(config.getWebservice().domain).port(config.getWebservice().port).build();
        ResourceConfig wsconfig = new ResourceConfig(EntryPoint.class, CORSProvider.class);
        JdkHttpServerFactory.createHttpServer(baseUri, wsconfig);
        WebSocketServer server2 = new SimpleServer(new InetSocketAddress(config.getWebsocket().domain, config.getWebsocket().port));
        server2.start();
    }

    public static void main(String[] args) throws Exception {
        new NewMain().go(args);
    }

}
