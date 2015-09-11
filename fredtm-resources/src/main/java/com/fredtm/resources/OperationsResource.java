package com.fredtm.resources;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class OperationsResource {

	@JsonProperty("_embedded")
	@SerializedName("_embedded")
	private Embedded embedded;

	public Embedded getEmbedded() {
		return embedded;
	}

	public void setEmbedded(Embedded embedded) {
		this.embedded = embedded;
	}

	public class Embedded {

		List<OperationResource> operationResourceList;

		public List<OperationResource> getOperationResourceList() {
			return operationResourceList;
		}

		public void setOperationsResourceList(List<OperationResource> operations) {
			this.operationResourceList = operations;
		}

	}

}
