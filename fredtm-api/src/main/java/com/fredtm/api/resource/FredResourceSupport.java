package com.fredtm.api.resource;

import java.util.stream.Stream;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public class FredResourceSupport extends ResourceSupport {

	protected String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String id) {
		this.uuid = id;
	}

	public void addLinks(Link... links) {
		Stream.of(links).forEach(l -> this.add(l));
	}

}
