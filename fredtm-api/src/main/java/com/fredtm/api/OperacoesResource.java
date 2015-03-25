package com.fredtm.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fredtm.core.model.Operation;

@Path("/operacoes")
public class OperacoesResource {

	List<Operation> operacoes = new ArrayList<>();
	int id;
	
	@POST
	@Path("/operacao")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON})
	public Response create(Operation operacao) {
		return Response.status(201).entity(operacao).build();
	}
	

	@GET
	@Path("/operacao")
	@Produces({ MediaType.APPLICATION_JSON})
	public Response read(){
		return Response.status(200).entity(new Operation("Teste REST", "Fred Company", "Teste de criação rest")).build();
	}

}
