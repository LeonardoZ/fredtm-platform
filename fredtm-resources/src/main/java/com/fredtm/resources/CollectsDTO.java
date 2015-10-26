package com.fredtm.resources;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class CollectsDTO {

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

		List<CollectDTO> collectDTOList;

		public List<CollectDTO> getCollectDTOList() {
			return this.collectDTOList;
		}

		public void setCollectDTOList(List<CollectDTO> collectDTOList) {
			this.collectDTOList = collectDTOList;
		}

	}
}
