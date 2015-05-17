package com.fredtm.api.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface ResourcesUtil<E, D> {

	default <C extends Collection<E>> List<D> configureResources(C elements) {
		List<D> ress = new ArrayList<>();
		elements.iterator().forEachRemaining(
				a -> ress.add(configureResource(a))
		);
		return ress;
	}

	D configureResource(E e);
}
