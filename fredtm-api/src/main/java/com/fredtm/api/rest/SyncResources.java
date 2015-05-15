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

	@Autowired
	private OperationResourceAssembler operationAssembler;

	@Autowired
	private SyncResourceAssembler syncAssembler;

	// Receber Op
	// Verificar se é válida
	// remover Antiga - inserir nova ? update geral
	// riar e devolver devolver sync
	@POST
	public Response receiveSync(OperationResource fullResource) {
		String json = "";
		String uuid = fullResource.getUuid();
		if (uuid != null && !uuid.isEmpty()) {
			Operation oldOperation = operationRepository.findOne(uuid);
			OperationResource oldResource = operationAssembler
					.toResource(oldOperation);
			json = new Gson().toJson(oldResource);
		}
		fullResource.getCollects().forEach(c->System.out.println(c.toString()));
		Operation newOperation = operationAssembler.fromResource(fullResource)
				.get();
		System.err.println("New: " + newOperation);
		System.err.println("New: " + newOperation.getId());
		System.err.println("New: " + newOperation.getAccount());
		System.err.println("New TIMES: " + newOperation.getCollects().iterator().next().getTimes());
		System.err.println("New ACTS: " + newOperation.getActivities());
		Sync sync = null;
		try {
			sync = service.receiveSync(json, newOperation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("After ===========");

		System.err.println("After: " + newOperation);
		System.err.println("After: " + newOperation.getId());
		System.err.println("After: " + newOperation.getAccount());
		System.err.println("After: " + newOperation.getCollects());
		newOperation.getActivities().forEach(
				a -> System.out.println(a.getOperation().toString()));

		SyncResource syncr = syncAssembler.toResource(sync);
		return Response.ok(syncr).build();
	}
}
