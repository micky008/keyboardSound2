package com.msc.keyboardsound2.server.entity;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Michael
 */
public class Config {

    private Webservice webservice;
    private Websocket websocket;
    private Server server;

    public Webservice getWebservice() {
        return webservice;
    }

    public void setWebservice(Webservice webservice) {
        this.webservice = webservice;
    }

    public Websocket getWebsocket() {
        return websocket;
    }

    public void setWebsocket(Websocket websocket) {
        this.websocket = websocket;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public class Webservice {

        public int port;
        public String domain;
    }

    public class Websocket {

        public int port;
        public String domain;
    }

    public class Server {
        public String liste;
	public String[] channels;
    }

    
    public static Config getConfig(String absoluteFilename) throws IOException {
        File file = new File(absoluteFilename);
        if (!file.exists()){
            throw new FileNotFoundException(absoluteFilename + "n'existe pas");
        }
        Gson gson = new Gson();
        String json = FileUtils.readFileToString(file, "UTF-8");
        return gson.fromJson(json, Config.class);
    }
    
}
