package com.msc.keyboardsound2.websocket;

import com.google.gson.Gson;
import com.msc.keyboardsound2.server.entity.AllChannel;
import com.msc.keyboardsound2.server.entity.Channel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

/**
 *
 * @author Michael
 */
public class SimpleServer extends WebSocketServer {

    public SimpleServer(InetSocketAddress address) {
        super(address);
    }

    private static final Gson gson = new Gson();
    private static final String WRITE_SOUND = "/writesound";
    private static final String INPUT_SOUND = "/inputsound";
    private static final String READ_LIST = "/readlist";
    private static final String READ_CHANNEL = "/readchan";

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        if (handshake.getResourceDescriptor().startsWith(READ_LIST)) {
            Channel chan = AllChannel.channels.stream().filter( chanF -> handshake.getResourceDescriptor().endsWith(chanF.getName()) ).findFirst().get();            
            conn.send(gson.toJson(chan.getList().getSongsVisu()));
        }
        if (handshake.getResourceDescriptor().startsWith(READ_CHANNEL)) {
            conn.send(gson.toJson(AllChannel.getAllChannelName()));
        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        for (Channel chan : AllChannel.channels) {
            if (conn.getResourceDescriptor().startsWith(INPUT_SOUND) && conn.getResourceDescriptor().endsWith(chan.getName())) {
                int id = 0;
                try {
                    id = Integer.parseInt(message);
                } catch (Exception nfe) {
                    return;
                }
                List<WebSocket> all = getConnections().stream().filter(ws -> ws.getResourceDescriptor().startsWith(WRITE_SOUND) && ws.getResourceDescriptor().endsWith(chan.getName())).collect(Collectors.toList());
                String song = chan.getList().getSondFileName(id);
                try {
                    String b64 = convertSongToBase64(song);
                    for (WebSocket ws : all) {
                        ws.send(b64);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(SimpleServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        System.out.println("received ByteBuffer from " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        //System.err.println("an error occurred on connection " + conn.getRemoteSocketAddress() + ":" + ex);
    }

    @Override
    public void onStart() {
        System.out.println("WebSocket server started successfully on: " + this.getAddress().getPort());
    }

    private String convertSongToBase64(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            throw new FileNotFoundException(filename + "n'existe pas");
        }
        InputStream fis = new FileInputStream(file);
        byte[] song = IOUtils.toByteArray(fis);
        IOUtils.closeQuietly(fis);
        return Base64.getEncoder().encodeToString(song);
    }

}
