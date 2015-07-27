package com.fredtm.api.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fredtm.api.SynchronizeLogic;
import com.fredtm.api.resource.OperationResourceAssembler;
import com.fredtm.api.resource.SyncResourceAssembler;
import com.fredtm.core.model.Account;
import com.fredtm.core.model.Operation;
import com.fredtm.core.model.Sync;
import com.fredtm.data.repository.AccountRepository;
import com.fredtm.data.repository.OperationRepository;
import com.fredtm.resources.OperationResource;
import com.fredtm.resources.SyncResource;
import com.fredtm.service.SyncService;
import com.fredtm.service.SyncState;

@RestController
@RequestMapping(value = "fredapi/sync")
public class SyncController implements ResourcesUtil<Sync, SyncResource> {

	@Autowired
	private SyncService service;

	@Autowired
	private SynchronizeLogic logic;

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
		Link self = linkTo(OperationController.class).slash(e.getId()).withSelfRel();
		return new Resource<OperationResource>(resource, self);
	};

	// public HttpEntity<Resource<String>> verifySyncState(@RequestBody
	// OperationResource resource){
	//
	// SyncState state = service.isValidSync(resource.getUuid(),
	// resource.getLastSync() != null? resource.getLastSync().getUuid():"",
	// resource.getModification());
	// return new ResponseEntity<Resource<String>>(200);
	// }

	// Receber Op
	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<Resource<SyncResource>> receiveSync(@RequestBody OperationResource fullResource) {
		// Check if exists
		SyncState state = service.isValidSync(fullResource.getUuid(), fullResource.getLastSync().getUuid(),
				fullResource.getModification());

		if (state == SyncState.INVALID_DATA) {
			return new ResponseEntity<Resource<SyncResource>>(HttpStatus.BAD_REQUEST);
		} else if (state == SyncState.NOTHING_TO_RECEIVE_FROM_SYNC) {
			return new ResponseEntity<Resource<SyncResource>>(HttpStatus.OK);
		} else if (state == SyncState.SYNC_DATE_CONFLICT) {
			return new ResponseEntity<Resource<SyncResource>>(HttpStatus.CONFLICT);
		}

		// Converte o que veio para JSON para salvar
		String uuid = fullResource.getUuid();

		Sync sync = null;
		Account acc = accountRepository.findOne(fullResource.getAccountId());

		if (state== SyncState.SYNC_EXISTING) {
			Operation oldOperation = operationRepository.findOne(uuid);
			Operation newOperation = logic.doSyncOnExisting(fullResource);
			// logic doesn't cover account
			newOperation.setAccount(acc);
			sync = service.receiveSync(oldOperation, newOperation);

		} else if(state == SyncState.NEW_SYNC) {
			Operation newOperation = logic.doSyncOnExisting(fullResource);
			// logic doesn't cover account
			newOperation.setAccount(acc);
			sync = service.receiveSync(newOperation);
		}
		Resource<SyncResource> syncr = configureResource(sync);
		return new ResponseEntity<Resource<SyncResource>>(syncr, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
	public HttpEntity<Resources<Resource<OperationResource>>> sendSyncs(@PathVariable("accountId") String accountId) {

		List<Operation> operations = service.sendLastOperations(accountRepository.findOne(accountId));

		if (!operations.isEmpty()) {
			List<Resource<OperationResource>> resourcesList = operationResourceUtil.configureResources(operations);
			Resources<Resource<OperationResource>> resources = 
					new Resources<Resource<OperationResource>>(resourcesList);
			return new ResponseEntity<Resources<Resource<OperationResource>>>(resources, HttpStatus.OK);

		} else {
			return new ResponseEntity<Resources<Resource<OperationResource>>>(HttpStatus.NO_CONTENT);
		}
	}

	@Override
	public Resource<SyncResource> configureResource(Sync e) {
		SyncResource resource = syncAssembler.toResource(e);

		Link self = linkTo(SyncController.class).slash(e.getId()).withSelfRel();

		// Link sendSyncData = linkTo(
		// methodOn(SyncController.class).sendSyncs(resource.getUuid()))
		// .withRel("sendSyncData");

		return new Resource<SyncResource>(resource, self);
	}

	public List<OperationResource> sendDataToClient(String accountUuid) {
		/*
		 * Select
		 */

		return null;
	}
}
