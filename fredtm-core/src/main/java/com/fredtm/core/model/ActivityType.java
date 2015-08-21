package com.fredtm.core.model;

import java.util.Arrays;
import java.util.Optional;

public enum ActivityType {

	UNPRODUCTIVE(0, "#ff7d77", "IMPRODUTIVA"), AUXILIARY(1, "#ffe0a2",
			"AUXILIAR"), PRODUCTIVE(2, "#a2deff", "PRODUTIVA");

	private int idActivityType;
	private String hexColor;
	private String value;

	ActivityType(int activityType, String hexColor, String value) {
		this.idActivityType = activityType;
		this.hexColor = hexColor;
		this.value = value;
	}

	public static Optional<ActivityType> getById(int activityType) {
		return Arrays.stream(values())
				.filter(t -> t.getActivityType() == activityType).findFirst();
	}

	public static Optional<ActivityType> getById(String activityType) {
		return Arrays.stream(values())
				.filter(t -> t.getValue().equals(activityType)).findFirst();
	}

	public String getHexColor() {
		return hexColor;
	}

	public int getActivityType() {
		return idActivityType;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return getValue();
	}

}
