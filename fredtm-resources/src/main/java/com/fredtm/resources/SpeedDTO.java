package com.fredtm.resources;

import com.fredtm.resources.base.FredDTOSupport;

public class SpeedDTO extends FredDTOSupport {

	private String activityTitle;
	private String collectUuid;
	private String activityUuid;
	private int speed;
	
	public SpeedDTO() {

	}

	public String getActivityTitle() {
		return this.activityTitle;
	}
	
	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}
	
	public String getCollectUuid() {
		return this.collectUuid;
	}

	public void setCollectUuid(String collectUuid) {
		this.collectUuid = collectUuid;
	}

	public String getActivityUuid() {
		return this.activityUuid;
	}

	public void setActivityUuid(String activityUuid) {
		this.activityUuid = activityUuid;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
