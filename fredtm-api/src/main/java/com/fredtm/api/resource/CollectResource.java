package com.fredtm.api.resource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class CollectResource extends FredResourceSupport {

	private String operationId;
	private Set<TimeActivityResource> times;
	private Set<ActivityResource> activities;

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

	public Set<TimeActivityResource> getTimes() {
		return times;
	}

	public void setTimes(Set<TimeActivityResource> times) {
		this.times = times;
	}

	public Set<ActivityResource> getActivities() {
		return activities;
	}

	public void setActivities(Set<ActivityResource> activities) {
		this.activities = activities;
	}
	public void setActivitiesList(List<ActivityResource> activities) {
		this.activities = new HashSet<>(activities);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getUuid()).append(times)
				.append(activities).append(operationId).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CollectResource other = (CollectResource) obj;
		return new EqualsBuilder().append(getUuid(), other.getUuid())
				.append(getActivities(), other.getActivities())
				.append(times, other.getTimes())
				.append(getOperationId(), other.getOperationId()).isEquals();
	}

	@Override
	public String toString() {
		return "CollectResource [operationId=" + operationId + " Times: "
				+ times.toString() + "]";
	}

}
