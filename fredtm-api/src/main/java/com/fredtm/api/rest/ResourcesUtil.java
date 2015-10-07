package com.fredtm.api.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ResourcesUtil<E, D> {

	default <C extends Collection<E>> List<Resource<D>> configureResources(C elements) {
		List<Resource<D>> ress = new ArrayList<>();
		elements.iterator().forEachRemaining(a -> ress.add(configureResource(a)));
		return ress;
	}

	Resource<D> configureResource(E e);

	default ResponseEntity<Resource<D>> createResponseEntity(E entity, HttpStatus status) {
		return new ResponseEntity<Resource<D>>(configureResource(entity), status);
	}
	
	default ResponseEntity<Resource<D>> createResponseHttp(HttpStatus status) {
		return new ResponseEntity<Resource<D>>(status);
	}
	
	default ResponseEntity<Resources<D>> createResponseForCollection(HttpStatus status) {
		return new ResponseEntity<Resources<D>>(status);
	}
	
	

}
