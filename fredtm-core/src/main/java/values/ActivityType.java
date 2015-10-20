package values;

import java.util.Arrays;
import java.util.Optional;

public enum ActivityType {

	UNPRODUCTIVE(0, "#ff7d77", "IMPRODUTIVA", "UNPRODUCTIVE"), AUXILIARY(1, "#ffe0a2", "AUXILIAR",
			"AUXILIARY"), PRODUCTIVE(2, "#a2deff", "PRODUTIVA", "PRODUCTIVE"), UNDEFINED(3, "#ffffff", "INDEFINIDA",
					"UNDEFINED"), NOT_PRODUCTIVE(4, "#ce77ff", "NÃO-PRODUTIVA", "NOT-PRODUCTIVE");

	private int idActivityType;
	private String hexColor;
	private String value;
	private String translated;

	ActivityType(int activityType, String hexColor, String value, String translated) {
		this.idActivityType = activityType;
		this.hexColor = hexColor;
		this.value = value;
		this.translated = translated;
	}

	public static Optional<ActivityType> getById(int activityType) {
		return Arrays.stream(values()).filter(t -> t.getActivityType() == activityType).findFirst();
	}

	public static Optional<ActivityType> getById(String activityType) {
		return Arrays.stream(values()).filter(t -> t.getValue().equals(activityType)).findFirst();
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

	public int getIdActivityType() {
		return this.idActivityType;
	}

	public String getTranslated() {
		return this.translated;
	}

}
