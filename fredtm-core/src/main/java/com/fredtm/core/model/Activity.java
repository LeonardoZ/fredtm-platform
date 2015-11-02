package com.fredtm.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import values.ActivityType;

@Entity
@Table(name = "activity")
public class Activity extends FredEntity {

	@Transient
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 120, name = "title")
	private String title;

	@Column(length = 120)
	private String description;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "activity_type")
	private ActivityType activityType;

	@Column(columnDefinition = "BIT")
	private Boolean quantitative;

	@Column(length = 100, name = "item_name")
	private String itemName;
	
	@Column(length = 100, name = "idle_activity")
	private Boolean isIdleActivity;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "operation_id")
	private Operation operation;

	public Activity() {
		quantitative = false;
		isIdleActivity = false;
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

	public Activity(String title, String description, ActivityType activityType, boolean isQuantitative) {
		this(title, description, activityType);
		setIsQuantitative(isQuantitative);
	}

	public Activity(Operation op, String title, String description, ActivityType activityType, boolean isQuantitative) {
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
		this.activityType = ActivityType.getById(activityType).orElse(ActivityType.PRODUCTIVE);
	}

	public Operation getOperation() {
		return operation;
	}

	
	public Boolean getIsIdleActivity() {
		return isIdleActivity == null ? false : isIdleActivity;
	}
	
	public void setIsIdleActivity(Boolean isIdleActivity) {
		this.isIdleActivity = isIdleActivity;
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
		if (quantitative && activityType != ActivityType.PRODUCTIVE) {
			throw new IllegalArgumentException();
		}
		this.quantitative = quantitative;
	}

	public String getItemName() {
		return quantitative ? itemName : "n/a";
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
		return new EqualsBuilder().append(operation, activity.operation).append(activityType, activity.activityType)
				.append(title, activity.title).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(operation).append(activityType).append(title).build();
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
