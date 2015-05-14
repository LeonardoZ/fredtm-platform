package com.fredtm.api.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.stereotype.Component;

import com.fredtm.api.resource.OperationResource;
import com.fredtm.api.resource.OperationResourceAssembler;
import com.fredtm.api.resource.SyncResource;
import com.fredtm.api.resource.SyncResourceAssembler;
import com.fredtm.core.model.Operation;
import com.fredtm.core.model.Sync;
import com.fredtm.data.repository.OperationRepository;
import com.fredtm.data.repository.SyncRepository;
import com.fredtm.service.SyncService;
import com.google.gson.Gson;

@Component
@Path(value = "/sync")
@Produces(value = MediaType.APPLICATION_JSON)
@ExposesResourceFor(SyncResources.class)
public class SyncResources {

	@Autowired
	private SyncRepository syncRepository;

	@Autowired
	private SyncService service;

	@Autowired
	private OperationRepository operationRepository;

	private OperationResourceAssembler operationAssembler = new OperationResourceAssembler();

	private SyncResourceAssembler syncAssembler = new SyncResourceAssembler();

	// Receber Op
	// Verificar se é válida
	// remover Antiga - inserir nova ? update geral
	// riar e devolver devolver sync
	@POST
	public Response receiveSync(OperationResource fullResource) {

		String uuid = fullResource.getUuid();
		Operation oldOperation = operationRepository.findOne(uuid);
		OperationResource oldResource = operationAssembler
				.toResource(oldOperation);
		String json = new Gson().toJson(oldResource);
		Operation newOperation = operationAssembler.fromResource(fullResource)
				.get();
		Sync sync = service.receiveSync(json, oldOperation, newOperation);
		SyncResource syncr = syncAssembler.toResource(sync);
		return Response.ok(syncr).build();
	}

}
