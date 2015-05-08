package com.fredtm.api.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.data.repository.OperationRepository;


@Component
@Path(value = "/operation")
public class OperationResources {
	
	@Autowired
	public OperationRepository repo;

	@GET
	@Produces(value= MediaType.APPLICATION_JSON)
	public String getOperation() {
		return "{id: 1, descricao: teste}";
	}

}
