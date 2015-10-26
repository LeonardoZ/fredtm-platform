package com.fredtm.core.decorator;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fredtm.core.model.Collect;

public enum TimeMeasure {

	HOURS("h - horas", 1000 * 60 * 60) {
		public TimeSystem build(Collect collect) {
			return new HoursSystem(collect);
		}
	},

	MINUTES("m - minutos", 1000 * 60) {
		public TimeSystem build(Collect collect) {
			return new MinuteSystem(collect);
		}
	},
	SECONDS("s - segundos", 1000) {
		public TimeSystem build(Collect collect) {
			return new SecondsSystem(collect);
		}
	},
	PCT("%", 0) {
		public TimeSystem build(Collect collect) {
			return new PercentageSystem(collect);
		}
	};

	private String value;
	private int fromMillisConverterFactor;

	private TimeMeasure(String value, int fromMillisConverterFactor) {
		this.value = value;
		this.fromMillisConverterFactor = fromMillisConverterFactor;
	}

	public String getValue() {
		return this.value;
	}
	
	public int getFromMillisConverterFactor() {
		return this.fromMillisConverterFactor;
	}

	public BigDecimal bigfromMillisConverterFactor() {
		return BigDecimal.valueOf(this.fromMillisConverterFactor);
	}


	public abstract TimeSystem build(Collect collect);

	public List<TimeSystem> buildList(List<Collect> collects) {
		return collects.stream().map(this::build).collect(Collectors.toList());
	}

	public static List<TimeMeasure> withoutPercentual() {
		return Arrays.asList(HOURS, MINUTES, SECONDS);
	}

	@Override
	public String toString() {
		return value;
	}

}
