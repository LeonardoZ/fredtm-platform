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
import com.fredtm.resources.OperationDTO;
import com.fredtm.resources.SyncDTO;
import com.fredtm.service.OperationService;
import com.fredtm.service.SyncService;
import com.fredtm.service.SyncState;

@RestController
@RequestMapping(value = "fredapi/sync")
public class SyncController implements ResourcesUtil<Sync, SyncDTO> {

	@Autowired
	private SyncService service;

	@Autowired
	private SynchronizeLogic logic;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private OperationService operationService;

	@Autowired
	private OperationResourceAssembler operationAssembler;

	@Autowired
	private SyncResourceAssembler syncAssembler;

	private ResourcesUtil<Operation, OperationDTO> operationResourceUtil = e -> {
		OperationDTO resource = operationAssembler.toResource(e);
		Link self = linkTo(OperationController.class).slash(e.getUuid()).withSelfRel();
		return new Resource<OperationDTO>(resource, self);
	};

	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<Resource<SyncDTO>> receiveSync(@RequestBody OperationDTO fullResource) {

		// Check if exists
		SyncState state = service.isValidSync(fullResource.getUuid(), fullResource.getModification());

		if (state == SyncState.INVALID_DATA) {
			return new ResponseEntity<Resource<SyncDTO>>(HttpStatus.BAD_REQUEST);
		} else if (state == SyncState.NOTHING_TO_RECEIVE_FROM_SYNC) {
			return new ResponseEntity<Resource<SyncDTO>>(HttpStatus.OK);
		} else if (state == SyncState.SYNC_DATE_CONFLICT) {
			return new ResponseEntity<Resource<SyncDTO>>(HttpStatus.CONFLICT);
		}

		// Converte o que veio para JSON para salvar
		String uuid = fullResource.getUuid();

		Sync sync = null;
		Account acc = accountRepository.findByUuid(fullResource.getAccountId());

		if (state == SyncState.SYNC_EXISTING) {
			Operation oldOperation = operationService.getOperation(uuid);
			System.err.println("In the old one " + oldOperation.getCollectsList().size());
			Operation newOperation = logic.doSyncOnExisting(fullResource);
			// logic doesn't cover account and op id
			newOperation.setId(oldOperation.getId());
			newOperation.setAccount(acc);
			System.err.println("New " + newOperation.getCollectsList().size());
			sync = service.receiveSync(oldOperation, newOperation);

		} else if (state == SyncState.NEW_SYNC) {
			System.err.println("Sync New +" + fullResource.toString());
			Operation newOperation = logic.doSyncOnExisting(fullResource);
			// logic doesn't cover account
			newOperation.setAccount(acc);
			sync = service.receiveSync(newOperation);
		}
		Resource<SyncDTO> syncr = configureResource(sync);
		return new ResponseEntity<Resource<SyncDTO>>(syncr, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
	public HttpEntity<Resources<Resource<OperationDTO>>> sendSyncs(@PathVariable("accountId") String accountUuid) {

		List<Operation> operations = service.sendLastOperations(accountRepository.findByUuid(accountUuid));

		if (!operations.isEmpty()) {
			List<Resource<OperationDTO>> resourcesList = operationResourceUtil.configureResources(operations);
			Resources<Resource<OperationDTO>> resources = new Resources<Resource<OperationDTO>>(resourcesList);
			return new ResponseEntity<Resources<Resource<OperationDTO>>>(resources, HttpStatus.OK);

		} else {
			return new ResponseEntity<Resources<Resource<OperationDTO>>>(HttpStatus.NO_CONTENT);
		}
	}

	
	@Override
	public Resource<SyncDTO> configureResource(Sync e) {
		SyncDTO resource = syncAssembler.toResource(e);

		Link self = linkTo(SyncController.class).slash(e.getUuid()).withSelfRel();

		// Link sendSyncData = linkTo(
		// methodOn(SyncController.class).sendSyncs(resource.getUuid()))
		// .withRel("sendSyncData");

		return new Resource<SyncDTO>(resource, self);
	}

}
