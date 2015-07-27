package com.fredtm.api.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.hateoas.Resource;

public interface ResourcesUtil<E, D> {

	default <C extends Collection<E>> List<Resource<D>> configureResources(C elements) {
		List<Resource<D>> ress = new ArrayList<>();
		elements.iterator().forEachRemaining(
				a -> ress.add(configureResource(a))
		);
		return ress;
	}

	Resource<D> configureResource(E e);
}
