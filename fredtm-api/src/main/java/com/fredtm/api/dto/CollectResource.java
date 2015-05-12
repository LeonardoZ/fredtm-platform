package com.fredtm.api.dto;

import java.util.HashMap;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class CollectResource extends ResourceSupport {

	private long operationId;
	private HashMap<Long, List<TimeActivityResource>> collectedTimes;
	private List<TimeActivityResource> times;
	private List<ActivityResource> activities;

	public long getOperationId() {
		return operationId;
	}

	public void setOperationId(long operationId) {
		this.operationId = operationId;
	}

	public HashMap<Long, List<TimeActivityResource>> getCollectedTimes() {
		return collectedTimes;
	}

	public void setCollectedTimes(
			HashMap<Long, List<TimeActivityResource>> collectedTimes) {
		this.collectedTimes = collectedTimes;
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

}
