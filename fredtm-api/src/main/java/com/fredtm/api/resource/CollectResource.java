package com.fredtm.api.resource;

import java.util.List;

public class CollectResource extends FredResourceSupport {

	private String operationId;
	private List<TimeActivityResource> times;
	private List<ActivityResource> activities;

	public CollectResource uuid(String uuid) {
		setUuid(uuid);
		return this;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public List<TimeActivityResource> getTimes() {
		return times;
	}

	public void setTimes(List<TimeActivityResource> times) {
		this.times = times;
	}

	public List<ActivityResource> getActivities() {
		return activities;
	}

	public void setActivities(List<ActivityResource> activities) {
		this.activities = activities;
	}

	@Override
	public String toString() {
		return "CollectResource [operationId=" + operationId + "]";
	}

}
