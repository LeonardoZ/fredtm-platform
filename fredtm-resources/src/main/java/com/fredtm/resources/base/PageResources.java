package com.fredtm.resources.base;

import org.springframework.hateoas.PagedResources.PageMetadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class PageResources {

	@SerializedName("_links")
	@JsonProperty("_links")
	private Links links;

	private PageMetadata page;


	public PageResources() {

	}	

	class Links {
		SimpleLink next;
		SimpleLink first;
		SimpleLink last;

		public SimpleLink getNext() {
			return next;
		}

		public void setNext(SimpleLink next) {
			this.next = next;
		}

		public SimpleLink getFirst() {
			return first;
		}

		public void setFirst(SimpleLink first) {
			this.first = first;
		}

		public SimpleLink getLast() {
			return last;
		}

		public void setLast(SimpleLink last) {
			this.last = last;
		}

		@Override
		public String toString() {
			return "Links [next=" + next + ", first=" + first + ", last="
					+ last + "]";
		}

	}

	public Links getLinks() {
		return links;
	}

	public void setLinks(Links links) {
		this.links = links;
	}

	public PageMetadata getPage() {
		return page;
	}

	public void setPage(PageMetadata page) {
		this.page = page;
	}

}
