package com.fredtm.resources;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fredtm.resources.base.FredDTOSupport;

public class CollectDTO extends FredDTOSupport {

	private int index;
	private String operationId;
	private int generalSpeed;
	private double normalTime;
	private double standardTime;
	private double productivity;
	private double mean;
	private double total;
	private double utilizationEfficiency;
	private double operationalEfficiency;
	private long totalProduction;
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

	public int getGeneralSpeed() {
		return this.generalSpeed;
	}

	public void setGeneralSpeed(int generalSpeed) {
		this.generalSpeed = generalSpeed;
	}

	public double getNormalTime() {
		return this.normalTime;
	}

	public void setNormalTime(double normalTime) {
		this.normalTime = normalTime;
	}

	public double getStandardTime() {
		return this.standardTime;
	}

	public void setStandardTime(double standardTime) {
		this.standardTime = standardTime;
	}

	public double getProductivity() {
		return this.productivity;
	}

	public void setProductivity(double productivity) {
		this.productivity = productivity;
	}

	public double getMean() {
		return this.mean;
	}

	public int getIndex() {
		return this.index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getUtilizationEfficiency() {
		return this.utilizationEfficiency;
	}

	public void setUtilizationEfficiency(double utilizationEfficiency) {
		this.utilizationEfficiency = utilizationEfficiency;
	}

	public double getOperationalEfficiency() {
		return this.operationalEfficiency;
	}

	public void setOperationalEfficiency(double operationalEfficiency) {
		this.operationalEfficiency = operationalEfficiency;
	}

	public long getTotalProduction() {
		return this.totalProduction;
	}

	public void setTotalProduction(long totalProduction) {
		this.totalProduction = totalProduction;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getUuid()).append(times).append(activities).append(generalSpeed)
				.append(operationId).toHashCode();
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
		return new EqualsBuilder().append(getUuid(), other.getUuid()).append(getActivities(), other.getActivities())
				.append(generalSpeed, other.getGeneralSpeed()).append(times, other.getTimes())
				.append(getOperationId(), other.getOperationId()).isEquals();
	}

	@Override
	public String toString() {
		return "CollectResource [operationId=" + operationId + " Times: " + times.toString() + "]";
	}

}
