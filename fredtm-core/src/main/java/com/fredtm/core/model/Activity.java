package com.fredtm.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Activity extends Entity {

	private static final long serialVersionUID = 1L;
	private String title;
	private String description;
	private ActivityType activityType;
	private Boolean quantitative;
	private String itemName;
	private Operation operation;

	public Activity() {
		quantitative = false;
	}

	public Activity(String title, ActivityType activityType) {
		this();
		setTitle(title);
		setActivityType(activityType);
	}

	public Activity(String title, String description, ActivityType activityType) {
		this(title, activityType);
		setDescription(description);
	}

	public Activity(String title, String description,
			ActivityType activityType, boolean isQuantitative) {
		this(title, description, activityType);
		setIsQuantitative(isQuantitative);
	}

	public Activity(Operation op, String title, String description,
			ActivityType activityType, boolean isQuantitative) {
		this(title, description, activityType, isQuantitative);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		validation.isValidString(title);
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityType activityType) {
		validation.isNullValue(activityType);
		this.activityType = activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = ActivityType.getById(activityType).orElse(
				ActivityType.PRODUCTIVE);
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		validation.isNullValue(operation);
		this.operation = operation;
	}

	public Boolean isQuantitative() {
		return quantitative == null ? false : quantitative;
	}

	public void setQuantitative(int quantitative) {
		this.quantitative = !(quantitative == 0);
	}

	public void setIsQuantitative(boolean quantitative) {
		if(quantitative && activityType != ActivityType.PRODUCTIVE){
			throw new IllegalArgumentException();
		}
		this.quantitative = quantitative;
	}

	public String getItemName() {
		return quantitative ? itemName : "Não quantitativa";
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Activity activity = (Activity) o;
		return new EqualsBuilder().append(operation, activity.operation)
				.append(activityType, activity.activityType)
				.append(title, activity.title).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(operation).append(activityType)
				.append(title).build();
	}

	@Override
	public String toString() {
		return title;

	}

	public void copy(Activity param) {
		setId(param.getId());
		setTitle(param.getTitle());
		setDescription(param.getDescription());
		setIsQuantitative(param.isQuantitative());
		setItemName(param.getItemName());
		setActivityType(param.getActivityType());
		setOperation(param.getOperation());
	}
}
