package com.fredtm.resources.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


public class ActivityDTO extends BaseDTO {

	private String title;
	private String description;
	private String activityType;
	private Boolean quantitative;
	private String itemName;
	private String operationId;
	
	public ActivityDTO uuid(String uuid) {
		setUuid(uuid);
		return this;
	}

	public ActivityDTO title(String value) {
		this.title = value;
		return this;
	}

	public ActivityDTO description(String value) {
		this.description = value;
		return this;
	}

	public ActivityDTO activityType(String value) {
		this.activityType = value;
		return this;
	}

	public ActivityDTO quantitative(boolean value) {
		this.quantitative = value;
		return this;
	}

	public ActivityDTO itemName(String value) {
		this.itemName = value;
		return this;
	}

	public ActivityDTO operationId(String value) {
		this.operationId = value;
		return this;
	}

	public ActivityDTO() {
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

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ActivityDTO activity = (ActivityDTO) o;
		return new EqualsBuilder()
				.append(getUuid(), activity.getUuid())
				.append(operationId, activity.operationId)
				.append(activityType, activity.activityType)
				.append(title, activity.title).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getUuid()).append(operationId).append(activityType)
				.append(title).build();
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
