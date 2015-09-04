package com.fredtm.resources;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fredtm.resources.base.FredResourceSupport;

public class TimeActivityResource extends FredResourceSupport {

	private String activityId;
	private String activityTitle;
	private String collectId;
	private String collectIndex;
	private String activityType;
	private long finalDate = 0l;
	private long startDate = 0l;
	private long timed = 0l;
	private String latitude = "";
	private String longitude = "";
	private int collectedAmount;
	private List<PictureResource> pics;

	public TimeActivityResource() {
		this.pics = new LinkedList<>();
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
	

	public TimeActivityResource latitude(String value) {
		this.latitude = value;
		return this;
	}

	public TimeActivityResource longitude(String value) {
		this.longitude = value;
		return this;
	}

	public TimeActivityResource collectedAmount(int value) {
		this.collectedAmount = value;
		return this;
	}
	
	public TimeActivityResource pic(byte[] value) {
		this.pics.add(new PictureResource(value));
		return this;
	}
	
	public TimeActivityResource pic(List<PictureResource> value) {
		this.pics.addAll(value);
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

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public List<PictureResource> getPics() {
		return this.pics;
	}
	
	public void setPics(List<PictureResource> pics) {
		this.pics = pics;
	}
	
	public String getCollectIndex() {
		return this.collectIndex;
	}
	
	public void setCollectIndex(String collectIndex) {
		this.collectIndex = collectIndex;
	}
	
	public String getActivityType() {
		return this.activityType;
	}
	
	public void setActivityType(String activityType) {
		this.activityType = activityType;
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
