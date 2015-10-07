package com.fredtm.resources;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class ActivitiesDTO {
	@JsonProperty("_embedded")
	@SerializedName("_embedded")
	private Embedded embedded;

	public Embedded getEmbedded() {
		return this.embedded;
	}
	
	public void setEmbedded(Embedded embedded) {
		this.embedded = embedded;
	}
	
	public class Embedded {

		List<ActivityDTO> activityDTOList;

		public List<ActivityDTO> getActivityDTOList() {
			return activityDTOList;
		}

		public void setActivityDTOList(List<ActivityDTO> acts) {
			this.activityDTOList = acts;
		}

	}
}
