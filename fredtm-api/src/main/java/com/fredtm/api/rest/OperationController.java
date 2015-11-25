package com.fredtm.api.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fredtm.api.resource.OperationResourceAssembler;
import com.fredtm.core.model.Account;
import com.fredtm.core.model.Operation;
import com.fredtm.data.repository.AccountRepository;
import com.fredtm.data.repository.OperationRepository;
import com.fredtm.resources.OperationDTO;
import com.fredtm.service.OperationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(consumes = "application/json", 
produces = "application/json", value = "Operations", description = "Operations Services")
@RestController
@RequestMapping(value = "fredapi/operation")
public class OperationController implements ResourcesUtil<Operation, OperationDTO> {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private OperationRepository repository;

	@Autowired
	private OperationService opService;

	@Autowired
	private OperationResourceAssembler assembler;

	@ApiOperation(value = "Find Operation by UUID")
	@ApiResponses({ @ApiResponse(code = 200, message = "OperationDTO", response = OperationDTO.class),
			@ApiResponse(code = 404, message = "Operation Not Found") })
	@RequestMapping(method = RequestMethod.GET, value = "/{uuid}")
	public HttpEntity<Resource<OperationDTO>> getOperation(
			@ApiParam(name = "uuid", value = "The Operation UUID ", required = true) @PathVariable("uuid") String uuid) {
		if (uuid.equals("")) {
			return createResponseHttp(HttpStatus.NOT_FOUND);
		}
		Operation found = repository.findByUuid(uuid);

		return createResponseEntity(found, HttpStatus.OK);
	}

	@ApiOperation(value = "Finds a complete Operation by UUID")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "OperationDTO", response = OperationDTO.class),
			@ApiResponse(code = 404, message = "Operation Not Found") })
	@RequestMapping(method = RequestMethod.GET, value = "/{uuid}/full")
	public HttpEntity<Resource<OperationDTO>> getOperationFull(
			@ApiParam(name = "uuid", value = "The Operation UUID", required = true) @PathVariable("uuid") String uuid) {
		if (uuid.equals("")) {
			return createResponseHttp(HttpStatus.NOT_FOUND);
		}
		Operation found = repository.findByUuid(uuid);
		return createResponseEntity(found, HttpStatus.OK);
	}

	@ApiOperation(value = "Remove operation")
	@ApiResponses({ @ApiResponse(code = 200, message = "Operation removed", response = OperationDTO.class),
			@ApiResponse(code = 304, message = "Operation not removed") })
	@RequestMapping(value = "/{operationUuid}", method = RequestMethod.DELETE)
	public HttpStatus removeOperation(
			@ApiParam(name = "operationUuid", value = "Operation UUID", required = true)
			@PathVariable("operationUuid") String uuid) {
		try {
			opService.deleteOperation(uuid);
			return HttpStatus.OK;
		} catch (Exception ex) {
			return HttpStatus.NOT_MODIFIED;
		}
	}

	@ApiOperation(value = "Create Operation")
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody HttpEntity<Resource<OperationDTO>> createOperation(
			@ApiParam(name = "Operation DTO", value = "The DTO with informations to be saved", required = true) @RequestBody OperationDTO dto) {

		if (opService.exists(dto.getUuid())) {
			return createResponseHttp(HttpStatus.CONFLICT);
		}
		Operation operation = assembler.fromResource(dto);
		Operation saved = opService.saveOperation(operation);
		return createResponseEntity(saved, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Update Operation")
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public HttpEntity<Resource<OperationDTO>> updateOperation(
			@ApiParam(name = "Operation DTO", value = "The DTO with informations to be updated", required = true) @RequestBody OperationDTO dto) {
		Operation operation = assembler.fromResource(dto);
		Operation saved = opService.saveOperation(operation);
		return createResponseEntity(saved, HttpStatus.OK);
	}

	@ApiOperation(value = "Finds Operations by Account UUID")
	@RequestMapping(method = RequestMethod.GET, value = "/{accUuid}/all")
	public HttpEntity<PagedResources<Resource<OperationDTO>>> getOperationsBy(
			@ApiParam(name = "accUuid", value = "The Account UUID", required = true) @PathVariable("accUuid") String accUuid,
	@ApiParam(name = "page", value = "The page to be retrivied") @RequestParam(value = "page", defaultValue = "0") int page,
	@ApiParam(name = "size", value = "The size of operations to be retrivied") @RequestParam(value = "size", defaultValue = "5") int size) {

		Account found = accountRepository.findByUuid(accUuid);

		PageRequest pageRequest = new PageRequest(page, size);

		Page<Operation> all = repository.findOperationsByAccount(found, pageRequest);
		List<Operation> ops = all.getContent();
		List<Resource<OperationDTO>> dtos = configureResources(ops);
		PageMetadata metadata = buidMetadata(all);

		PagedResources<Resource<OperationDTO>> pagedResources = new PagedResources<>(dtos, metadata);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);

	}

	private PageMetadata buidMetadata(Page<Operation> all) {
		PageMetadata metadata = new PagedResources.PageMetadata(all.getSize(), all.getNumber(),
				all.getNumberOfElements(), all.getTotalPages());
		return metadata;
	}

	public Link pageLink(int size, int elements, String rel) {
		Link otherPage = linkTo(methodOn(OperationController.class).getOperationsBy("", size, elements)).withRel(rel);
		return otherPage;
	}

	@Override
	public Resource<OperationDTO> configureResource(Operation e) {
		OperationDTO resource = assembler.toResource(e);
		Link self = linkTo(OperationController.class).slash(e.getUuid()).withSelfRel();
		Resource<OperationDTO> result = new Resource<OperationDTO>(resource, self);
		return result;
	}

}
