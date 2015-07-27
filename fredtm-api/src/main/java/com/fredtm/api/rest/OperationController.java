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
import com.fredtm.resources.OperationResource;

@RestController
@RequestMapping(value = "fredapi/operation")
public class OperationController implements
		ResourcesUtil<Operation, OperationResource> {

	@Autowired
	private OperationRepository repository;

	@Autowired
	private OperationResourceAssembler assembler;

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public HttpEntity<Resource<OperationResource>> getOperation(
			@PathVariable("id") String id) {
		if (id.isEmpty()) {
			return new ResponseEntity<Resource<OperationResource>>(
					HttpStatus.NO_CONTENT);
		}
		Operation found = repository.findOne(id);

		Resource<OperationResource> resource = configureResource(found);
		return new ResponseEntity<Resource<OperationResource>>(resource,
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}/full")
	public HttpEntity<Resource<OperationResource>> getOperationFull(
			@PathVariable("id") String id) {
		if (id.isEmpty()) {
			return new ResponseEntity<Resource<OperationResource>>(
					HttpStatus.NO_CONTENT);
		}
		Operation found = repository.findOne(id);
		Resource<OperationResource> resource = configureResource(found);
		return new ResponseEntity<Resource<OperationResource>>(resource,
				HttpStatus.OK);
	}

	@Override
	public Resource<OperationResource> configureResource(Operation e) {
		OperationResource resource = assembler.toResource(e);
		Link self = linkTo(OperationController.class).slash(e.getId())
				.withSelfRel();
		Resource<OperationResource> result = new Resource<OperationResource>(
				resource, self);
		return result;
	}

}
