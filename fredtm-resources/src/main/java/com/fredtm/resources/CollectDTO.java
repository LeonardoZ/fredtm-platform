package com.fredtm.resources;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fredtm.resources.base.FredDTOSupport;

public class CollectDTO extends FredDTOSupport {

	private String operationId;
	private Set<TimeActivityDTO> times;
	private Set<ActivityDTO> activities;

	public CollectDTO uuid(String uuid) {
		setUuid(uuid);
		return this;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public Set<TimeActivityDTO> getTimes() {
		return times;
	}

	public void setTimes(Set<TimeActivityDTO> times) {
		this.times = times;
	}

	public Set<ActivityDTO> getActivities() {
		return activities;
	}

	public void setActivities(Set<ActivityDTO> activities) {
		this.activities = activities;
	}
	public void setActivitiesList(List<ActivityDTO> activities) {
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
		CollectDTO other = (CollectDTO) obj;
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
