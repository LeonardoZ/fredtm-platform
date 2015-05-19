package com.fredtm.api.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

@Component
@Path("/")
@Produces(value = MediaType.APPLICATION_JSON)
public class FredApiResources {

	@GET
	@Path("/base")
	public Response welcome() {
		return Response.ok("Welcome!").build();
	}

}
