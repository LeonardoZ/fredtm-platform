package com.fredtm.resources;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fredtm.resources.base.FredResourceSupport;

public class TimeActivityResource extends FredResourceSupport {

	private String activityId;
	private String activityTitle;
	private String collectId;
	private long finalDate = 0l;
	private long startDate = 0l;
	private long timed = 0l;
	private long latitude = 0l;
	private long longitude = 0l;
	private int collectedAmount;

	public TimeActivityResource() {

	}

	public TimeActivityResource uuid(String uuid) {
		setUuid(uuid);
		return this;
	}

	public TimeActivityResource activityTitle(String title) {
		this.activityTitle = title;
		return this;
	}

	public TimeActivityResource collectId(String value) {
		this.collectId = value;
		return this;
	}

	public TimeActivityResource activityId(String value) {
		this.activityId = value;
		return this;
	}

	public TimeActivityResource finalDate(long value) {
		this.finalDate = value;
		return this;
	}

	public TimeActivityResource startDate(long value) {
		this.startDate = value;
		return this;
	}

	public TimeActivityResource timed(long value) {
		this.timed = value;
		return this;
	}
	

	public TimeActivityResource latitude(long value) {
		this.latitude = value;
		return this;
	}

	public TimeActivityResource longitude(long value) {
		this.longitude = value;
		return this;
	}

	public TimeActivityResource collectedAmount(int value) {
		this.collectedAmount = value;
		return this;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getCollectId() {
		return collectId;
	}

	public void setCollectId(String collectId) {
		this.collectId = collectId;
	}

	public long getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(long finalDate) {
		this.finalDate = finalDate;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getTimed() {
		return timed;
	}

	public void setTimed(long timed) {
		this.timed = timed;
	}

	public int getCollectedAmount() {
		return collectedAmount;
	}

	public void setCollectedAmount(int collectedAmount) {
		this.collectedAmount = collectedAmount;
	}

	public String getActivityTitle() {
		return activityTitle;
	}

	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}

	public long getLatitude() {
		return latitude;
	}

	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}

	public long getLongitude() {
		return longitude;
	}

	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "TimeActivityResource [activityId=" + activityId
				+ ", activityTitle=" + activityTitle + ", collectId="
				+ collectId + ", finalDate=" + finalDate + ", startDate="
				+ startDate + ", timed=" + timed + ", collectedAmount="
				+ collectedAmount + ", getUuid()=" + getUuid()
				+ ", getLinks()=" + null + "]";
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getUuid()).append(activityId)
				.append(collectId).append(finalDate).append(startDate)
				.append(timed).build();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeActivityResource other = (TimeActivityResource) obj;
		return new EqualsBuilder().append(getUuid(), other.getUuid())
				.append(activityId, other.activityId)
				.append(collectId, other.collectId)
				.append(finalDate, other.finalDate)
				.append(startDate, other.startDate).append(timed, other.timed)
				.isEquals();
	}

}
