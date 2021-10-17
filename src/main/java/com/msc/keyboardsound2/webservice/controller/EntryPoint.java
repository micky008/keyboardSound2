package com.msc.keyboardsound2.webservice.controller;

import com.msc.keyboardsound2.server.entity.AllChannel;
import com.msc.keyboardsound2.server.entity.Channel;
import com.msc.keyboardsound2.server.entity.Config;
import com.msc.keyboardsound2.server.SoundList;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author micky
 */
@Path("/")
public class EntryPoint {

    @GET
    @Path("refresh")
    @Produces(MediaType.APPLICATION_JSON)
    public Response refresh() throws IOException {
        Config config = Config.getConfig("config.json");
	AllChannel.channels.clear();
        Map<String,SoundList> map = SoundList.read(config);
        for (Entry<String,SoundList> e :  map.entrySet() ){
            Channel c1 = new Channel(e.getKey());
            c1.setList(e.getValue());
            AllChannel.channels.add(c1);
        }
	return Response.ok().build();
    }
       

}
