package com.fredtm.core.decorator;

import java.util.List;
import java.util.stream.Collectors;

import com.fredtm.core.model.Collect;

public enum TimeSystems {

	HOURS("h") {
		public TimeSystem build(Collect collect) {
			return new HoursSystem(collect);
		}
	},

	MINUTES("m") {
		public TimeSystem build(Collect collect) {
			return new MinuteSystem(collect);
		}
	},
	SECONDS("s") {
		public TimeSystem build(Collect collect) {
			return new SecondsSystem(collect);
		}
	},
	PCT("%") {
		public TimeSystem build(Collect collect) {
			return new PercentageSystem(collect);
		}
	};

	private String value;

	private TimeSystems(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public abstract TimeSystem build(Collect collect);

	public List<TimeSystem> buildList(List<Collect> collects) {
		return collects.stream().map(this::build).collect(Collectors.toList());
	}
	
	@Override
	public String toString() {
		return value;
	}
	
}
