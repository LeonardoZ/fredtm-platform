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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(consumes = "application/json", 
produces = "application/json", value = "Synchronization", description = "Synchronization Services")
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

	@ApiOperation(value = "Receive Sync From Client")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "No need for synchronization", response = SyncDTO.class),
		@ApiResponse(code = 201, message = "Synchronization accepted and created", response = SyncDTO.class),
		@ApiResponse(code = 400, message = "Invalid information for synchronization", response = SyncDTO.class),
		@ApiResponse(code = 406, message = "Conflict while saving synchronization", response = SyncDTO.class),
		@ApiResponse(code = 401, message = "Account not Authenticated") })
	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<Resource<SyncDTO>> receiveSync(
			@ApiParam(name = "OperationDTO", value = "The DTO with informations to be syncronized", required = true) 
			@RequestBody OperationDTO fullResource) {

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
			Operation newOperation = logic.doSyncOnExisting(fullResource);
			// logic doesn't cover account
			newOperation.setAccount(acc);
			sync = service.receiveSync(newOperation);
		}
		Resource<SyncDTO> syncr = configureResource(sync);
		return new ResponseEntity<Resource<SyncDTO>>(syncr, HttpStatus.CREATED);
	}
	
	
	@ApiOperation(value = "Send actual Sync state to client")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "No need for synchronization", response = OperationDTO.class),
		@ApiResponse(code = 204, message = "No content to be viewed", response = OperationDTO.class),
		@ApiResponse(code = 401, message = "Account not Authenticated") })
	@RequestMapping(value = "/{accountUuid}", method = RequestMethod.GET)
	public HttpEntity<Resources<Resource<OperationDTO>>> sendSyncs(
			@ApiParam(name = "accountUuid", value = "The UUID of the account", required = true) 	
			@PathVariable("accountUuid") String accountUuid) {

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
