package com.fredtm.api.rest;

import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.fredtm.api.resource.OperationResource;
import com.fredtm.api.resource.OperationResourceAssembler;
import com.fredtm.api.resource.SyncResource;
import com.fredtm.api.resource.SyncResourceAssembler;
import com.fredtm.core.model.Operation;
import com.fredtm.core.model.Sync;
import com.fredtm.data.repository.AccountRepository;
import com.fredtm.data.repository.OperationRepository;
import com.fredtm.service.SyncService;
import com.google.gson.Gson;

@Component
@Path(value = "/sync")
@Produces(value = MediaType.APPLICATION_JSON)
@ExposesResourceFor(SyncResources.class)
public class SyncResources implements ResourcesUtil<Sync, SyncResource> {

	@Autowired
	private EntityLinks entityLinks;

	@Autowired
	private SyncService service;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private OperationRepository operationRepository;

	@Autowired
	private OperationResourceAssembler operationAssembler;

	@Autowired
	private SyncResourceAssembler syncAssembler;

	private ResourcesUtil<Operation, OperationResource> operationResourceUtil = e -> {
		OperationResource resource = operationAssembler.toResource(e);
		Link self = entityLinks.linkFor(OperationResources.class)
				.slash(e.getId()).withSelfRel();
		resource.addLinks(self);
		return resource;
	};

	// Receber Op
	// Verificar se é válida
	// remover Antiga - inserir nova ? update geral
	// riar e devolver devolver sync
	@POST
	public Response receiveSync(OperationResource fullResource) {
		if (!service.isValidSync(fullResource.getUuid(),
				fullResource.getModification())) {
			return Response.notModified().build();
		}
		String json = "";
		String uuid = fullResource.getUuid();
		if (uuid != null && !uuid.isEmpty()) {
			Operation oldOperation = operationRepository.findOne(uuid);
			OperationResource oldResource = operationAssembler
					.toResource(oldOperation);
			json = new Gson().toJson(oldResource);
		}
		Operation newOperation = operationAssembler.fromResource(fullResource)
				.get();
		Sync sync = service.receiveSync(json, newOperation);
		SyncResource syncr = configureResource(sync);
		return Response.ok(syncr).build();
	}

	@GET
	@Path("/{accountId}")
	public Response sendSyncs(@PathParam("accountId") String accountId) {
		Set<Operation> operations = service
				.sendLastOperations(accountRepository.findOne(accountId));
		if (!operations.isEmpty()) {
			List<OperationResource> resources = operationResourceUtil
					.configureResources(operations);
			return Response.ok(resources).build();
		} else {
			return Response.noContent().build();
		}
	}

	@Override
	public SyncResource configureResource(Sync e) {
		SyncResource resource = syncAssembler.toResource(e);
		Link self = entityLinks.linkFor(SyncResources.class).slash(e.getId())
				.withSelfRel();
		Link sendSyncData = entityLinks.linkFor(SyncResources.class).withRel(
				"sendSyncData");
		Link receiveSyncData = entityLinks.linkFor(SyncResources.class)
				.slash(e.getId()).withRel("receiveSyncData");
		resource.addLinks(self, sendSyncData, receiveSyncData);
		return resource;
	}

}
