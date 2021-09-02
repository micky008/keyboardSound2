package com.msc.keyboardsound2.provider;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author micky
 */
@Provider
public class CORSProvider implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext crc, ContainerResponseContext response) throws IOException {
	response.getHeaders().add("Access-Control-Allow-Origin", "*");
	response.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
	//response.getHeaders().add("Access-Control-Allow-Credentials", "true");
	response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    }

}
