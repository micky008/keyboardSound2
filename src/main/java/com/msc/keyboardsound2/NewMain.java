package com.msc.keyboardsound2;

import com.msc.keyboardsound2.server.entity.AllChannel;
import com.msc.keyboardsound2.server.entity.Channel;
import com.msc.keyboardsound2.server.entity.Config;
import com.msc.keyboardsound2.server.SoundList;
import com.msc.keyboardsound2.webservice.controller.EntryPoint;
import com.msc.keyboardsound2.webservice.provider.CORSProvider;
import com.msc.keyboardsound2.websocket.SimpleServer;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Map;
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
	Map<String,SoundList> map = SoundList.read(config);
        for (Map.Entry<String,SoundList> e :  map.entrySet() ){
            Channel c1 = new Channel(e.getKey());
            c1.setList(e.getValue());
            AllChannel.channels.add(c1);
        }
	URI baseUri = UriBuilder.fromUri(config.getWebservice().domain).port(config.getWebservice().port).build();
	ResourceConfig wsconfig = new ResourceConfig(EntryPoint.class, CORSProvider.class);
	JdkHttpServerFactory.createHttpServer(baseUri, wsconfig);
        System.out.println("WebServer start on: "+ config.getWebservice().port);
	WebSocketServer server2 = new SimpleServer(new InetSocketAddress(config.getWebsocket().domain, config.getWebsocket().port));
	server2.start();
    }

    public static void main(String[] args) throws Exception {
	new NewMain().go(args);
    }

}
