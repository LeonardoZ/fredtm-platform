package com.fredtm.resources.resource;

import java.util.List;

public class BaseDTO {

	private List<Link> links;

	public BaseDTO() {

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
