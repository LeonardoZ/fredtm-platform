package com.fredtm.api.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fredtm.api.resource.OperationResourceAssembler;
import com.fredtm.core.model.Operation;
import com.fredtm.data.repository.OperationRepository;
import com.fredtm.resources.OperationDTO;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Api(value = "operation", description = "Operations methods")
@RestController
@RequestMapping(value = "fredapi/operation")
public class OperationController implements ResourcesUtil<Operation, OperationDTO> {

	@Autowired
	private OperationRepository repository;

	@Autowired
	private OperationResourceAssembler assembler;

	@ApiOperation(value = "View the Specific info of the operation")
	@RequestMapping(method = RequestMethod.GET, value = "/{uuid}")
	public HttpEntity<Resource<OperationDTO>> getOperation(
			@ApiParam(name = "operationId", value = "The Id of the operation to be viewed", required = true) @PathVariable("uuid") String uuid) {
		if (uuid.equals("")) {
			return new ResponseEntity<Resource<OperationDTO>>(HttpStatus.NO_CONTENT);
		}
		Operation found = repository.findByUuid(uuid);

		Resource<OperationDTO> resource = configureResource(found);
		return new ResponseEntity<Resource<OperationDTO>>(resource, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{uuid}/full")
	public HttpEntity<Resource<OperationDTO>> getOperationFull(@PathVariable("uuid") String uuid) {
		if (uuid.equals("")) {
			return new ResponseEntity<Resource<OperationDTO>>(HttpStatus.NO_CONTENT);
		}
		Operation found = repository.findByUuid(uuid);
		Resource<OperationDTO> resource = configureResource(found);
		return new ResponseEntity<Resource<OperationDTO>>(resource, HttpStatus.OK);
	}

	@Override
	public Resource<OperationDTO> configureResource(Operation e) {
		OperationDTO resource = assembler.toResource(e);
		Link self = linkTo(OperationController.class).slash(e.getUuid()).withSelfRel();
		Resource<OperationDTO> result = new Resource<OperationDTO>(resource, self);
		return result;
	}

}
