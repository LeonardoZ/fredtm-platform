package com.fredtm.api.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;


@Component
@Path(value = "/operation")
public class OperationController {

	@GET
	@Produces
	public String getOperation() {
		return "{id: 1, descricao: teste}";
	}

}
