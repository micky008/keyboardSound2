package com.msc.keyboardsound2.webservice.controller;

import com.msc.keyboardsound2.server.SoundList;
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
    public Response refresh() {
        SoundList.refreshStatic();
	return Response.ok().build();
    }
       

}
