package com.fredtm.api.resource;

import org.springframework.hateoas.ResourceSupport;


public class FredResourceSupport extends ResourceSupport {

	private String uuid;


	public String getUuid() {
		return uuid;
	}

	public void setUuid(String id) {
		this.uuid = id;
	}

}
