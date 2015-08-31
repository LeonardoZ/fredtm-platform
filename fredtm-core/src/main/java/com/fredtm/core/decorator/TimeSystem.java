package com.fredtm.core.decorator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fredtm.core.model.ActivityType;
import com.fredtm.core.model.Collect;
import com.fredtm.core.model.TimeActivity;

public abstract class TimeSystem {

	private Collect collect;

	public TimeSystem(Collect collect) {
		this.collect = collect;
	}

	public List<TimeActivity> getTimes() {
		return collect.getTimes();
	}

	public Collect getCollect() {
		return this.collect;
	}

	public List<Double> convertAll() {
		return getTimes().stream().map(this::convertTime).collect(Collectors.toList());
	}

	public abstract Double convertTime(TimeActivity ta);

	public Double getValue(ActivityType type) {
		return getCollect().getTimes().stream().filter(ta -> ta.getActivity().getActivityType().equals(type))
				.mapToDouble(this::convertTime).sum();
	}

	public abstract String getSymbol();

	public Map<TimeActivity,Double> getValueMap(){
		Map<TimeActivity, Double> collected = getTimes().stream()
			.collect(Collectors.toMap(
					t -> t, t2 -> convertTime(t2)
			)
		);
		
		collected.entrySet()
	        .stream()
	        .peek(System.out::println)
	        .sorted((e1, e2) -> e1.getKey().getStartDate().compareTo(e2.getKey().getStartDate())) // custom Comparator
	        .map(e -> e.getKey())

	        .peek(System.out::println)
	        .collect(Collectors.toList());
		return collected;
	}
	
	public Double getValueSimplified() {
		if (!getCollect().getTimes().isEmpty()) {
			return getCollect().getTimes().stream()
					.filter(ta -> !ta.getActivity().getActivityType().equals(ActivityType.PRODUCTIVE))
					.mapToDouble(this::convertTime).sum();
		} else {
			return 0.0;
		}
	}

	public Map<String, Optional<Double>> getTimeByActivities() {
		if (!getCollect().getTimes().isEmpty()) {
			return getCollect().getTimes()
					.stream()
					.collect(
							Collectors.groupingBy(ta -> ta.getActivity().getTitle(),
									Collectors.mapping(this::convertTime, Collectors.reducing((x,y)->x+y))
							)
					);
		}else {
			return null;
		}
	}

}
