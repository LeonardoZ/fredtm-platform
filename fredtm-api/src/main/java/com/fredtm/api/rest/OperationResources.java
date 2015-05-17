package com.fredtm.api.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.stereotype.Component;

import com.fredtm.api.resource.AccountResource;
import com.fredtm.api.resource.OperationResource;
import com.fredtm.api.resource.OperationResourceAssembler;
import com.fredtm.core.model.Operation;
import com.fredtm.data.repository.OperationRepository;

@Component
@Path(value = "/operation")
@Produces(value = MediaType.APPLICATION_JSON)
@ExposesResourceFor(AccountResource.class)
public class OperationResources {

	@Autowired
	private OperationRepository repository;

	@Autowired
	private OperationResourceAssembler assembler;

	@GET
	@Path("/{id}")
	public Response getOperation(@PathParam("id") String id) {
		Operation found = repository.findOne(id);
		OperationResource resource = assembler.toResource(found);
		return Response.ok(resource).build();
	}

}
