package com.fredtm.api.resource;

import org.springframework.hateoas.ResourceSupport;

public class TimeActivityResource extends ResourceSupport {

	private long pkId;
	private long activityId;
	private String activityHref;
	private long collectId;
	private String collectHref;
	private long finalDate = 0l;
	private long startDate = 0l;
	private long timed = 0l;
	private int collectedAmount;

	public TimeActivityResource() {

	}
	
	public TimeActivityResource pkId(long value) {
		this.pkId = value;
		return this;
	}

	public TimeActivityResource activityId(long value) {
		this.activityId = value;
		return this;
	}

	public TimeActivityResource collectId(long value) {
		this.collectId = value;
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

	public TimeActivityResource collectedAmount(int value) {
		this.collectedAmount = value;
		return this;
	}

	public TimeActivityResource collectHref(String value) {
		this.collectHref = value;
		return this;
	}

	public long getPkId() {
		return pkId;
	}

	public void setPkId(long pkId) {
		this.pkId = pkId;
	}

	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	public String getActivityHref() {
		return activityHref;
	}

	public void setActivityHref(String activityHref) {
		this.activityHref = activityHref;
	}

	public long getCollectId() {
		return collectId;
	}

	public void setCollectId(long collectId) {
		this.collectId = collectId;
	}

	public String getCollectHref() {
		return collectHref;
	}

	public void setCollectHref(String collectHref) {
		this.collectHref = collectHref;
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

}
