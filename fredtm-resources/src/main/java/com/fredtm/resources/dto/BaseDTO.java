package com.fredtm.resources.dto;

import java.util.List;

public class BaseDTO {

	protected String uuid;

	private List<Link> links;

	public BaseDTO() {

	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String id) {
		this.uuid = id;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public Link getHref(String rel) {
		return links.stream().filter(f -> f.getRel().equals(rel)).findFirst()
				.get();
	}
}
