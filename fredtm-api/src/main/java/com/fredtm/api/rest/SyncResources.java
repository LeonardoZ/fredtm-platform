package com.fredtm.api.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.stereotype.Component;

import com.fredtm.api.dto.OperationResource;
import com.fredtm.data.repository.OperationRepository;
import com.fredtm.data.repository.SyncRepository;

@Component
@Path("/sync")
@Produces(value = MediaType.APPLICATION_JSON)
@ExposesResourceFor(SyncResources.class)
public class SyncResources {

	@Autowired
	private SyncRepository syncRepository;

	@Autowired
	private OperationRepository operationRepository;

	@POST
	@Path("")
	public Response receiveSync(OperationResource fullResource) {
		  
		
		return Response.ok().build();
	}
}
