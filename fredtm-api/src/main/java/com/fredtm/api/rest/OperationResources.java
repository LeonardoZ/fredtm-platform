package com.fredtm.api.rest;

import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.stereotype.Component;

import com.fredtm.api.dto.AccountResource;
import com.fredtm.data.repository.OperationRepository;


@Component
@Path(value = "/operation")
@Produces(value= MediaType.APPLICATION_JSON)
@ExposesResourceFor(AccountResource.class)
public class OperationResources {
	
	@Autowired
	public OperationRepository repository;

	@GET
	@Path("/{id}")
	public Response getOperation(@PathParam("id") long id) {
		return null;
	}
	

}
