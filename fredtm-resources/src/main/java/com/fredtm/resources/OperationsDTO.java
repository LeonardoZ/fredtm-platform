package com.fredtm.resources;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class OperationsDTO {

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

		List<OperationDTO> operationDTOList;

		public List<OperationDTO> getOperationDTOList() {
			return operationDTOList;
		}

		public void setOperationsDTOList(List<OperationDTO> operations) {
			this.operationDTOList = operations;
		}

	}

}
