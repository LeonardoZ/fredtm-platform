package com.fredtm.core.decorator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fredtm.core.model.Collect;
import com.fredtm.core.model.TimeActivity;

import values.ActivityType;

public class PercentageSystem extends TimeSystem {

	public PercentageSystem(Collect collect) {
		super(collect);
	}

	@Override
	public Double convertTime(TimeActivity ta) {
		return null;
	}
	
	@Override
	public Double getValue(ActivityType type) {
		return getCollect().getTotalPercentageOfTimed(type);
	}

	@Override
	public Double getValueSimplified() {
		double totalUnproductive = getCollect().getTotalPercentageOfTimed(ActivityType.UNPRODUCTIVE);
		double totalAuxiliary = getCollect().getTotalPercentageOfTimed(ActivityType.AUXILIARY);
		return new BigDecimal(totalAuxiliary).add(BigDecimal.valueOf(totalUnproductive)).setScale(1,
				RoundingMode.FLOOR).doubleValue();
	}
	
	@Override
	public LinkedHashMap<String, Optional<Double>> getTimeByActivities() {
		Collect collect = getCollect();
		
		double totalInMs = Double.valueOf(collect.getTotalTimed());
		
		Function<TimeActivity,Double> fn = a -> (a.getTimed() / totalInMs) * 100;
		
		LinkedHashMap<String, Optional<Double>> collected = 
			collect.getTimes().stream()
			.collect(
					Collectors.groupingBy(
								ta -> ta.getActivity().getTitle(),
								LinkedHashMap::new,
								Collectors.mapping(
										fn, Collectors.reducing(( x,y)-> x+y )
								)
					)
			);
		return collected;
	}
	
	@Override
	public String getSymbol() {
		return "%";
	}



}
