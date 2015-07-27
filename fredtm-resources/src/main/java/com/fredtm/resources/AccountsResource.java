package com.fredtm.resources;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fredtm.resources.base.PageResources;
import com.google.gson.annotations.SerializedName;

public class AccountsResource extends PageResources {

	@SerializedName("_embedded")
	@JsonProperty("_embedded")
	private Embedded embedded;

	public AccountsResource() {

	}

	public Embedded getEmbedded() {
		return embedded;
	}

	public void setEmbedded(Embedded embedded) {
		this.embedded = embedded;
	}

	class Embedded {
		List<AccountResource> accounts;

		public List<AccountResource> getAccounaccounts() {
			return accounts;
		}

		public void setAccounaccounts(List<AccountResource> accounts) {
			this.accounts = accounts;
		}

		@Override
		public String toString() {
			return "Embedded [elemenaccounts=" + accounts + "]";
		}

	}
}
