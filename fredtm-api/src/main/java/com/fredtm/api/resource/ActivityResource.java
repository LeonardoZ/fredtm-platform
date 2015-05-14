package com.fredtm.api.resource;


public class ActivityResource extends FredResourceSupport {

	private String title;
	private String description;
	private String activityType;
	private Boolean quantitative;
	private String itemName;
	private long operationId;
	
	public ActivityResource uuid(String uuid) {
		setUuid(uuid);
		return this;
	}

	public ActivityResource title(String value) {
		this.title = value;
		return this;
	}

	public ActivityResource description(String value) {
		this.description = value;
		return this;
	}

	public ActivityResource activityType(String value) {
		this.activityType = value;
		return this;
	}

	public ActivityResource quantitative(boolean value) {
		this.quantitative = value;
		return this;
	}

	public ActivityResource itemName(String value) {
		this.itemName = value;
		return this;
	}

	public ActivityResource operationId(long value) {
		this.operationId = value;
		return this;
	}

	public ActivityResource() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Boolean getQuantitative() {
		return quantitative;
	}

	public void setQuantitative(Boolean quantitative) {
		this.quantitative = quantitative;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public long getOperationId() {
		return operationId;
	}

	public void setOperationId(long operationId) {
		this.operationId = operationId;
	}

	@Override
	public String toString() {
		return "ActivityResource [uuId=" + getUuid() + ", title=" + title
				+ ", description=" + description + ", activityType="
				+ activityType + ", quantitative=" + quantitative
				+ ", itemName=" + itemName + ", operationId=" + operationId
				+ "]";
	}

}
