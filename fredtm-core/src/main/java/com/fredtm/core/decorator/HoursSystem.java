package com.fredtm.core.decorator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fredtm.core.model.Collect;
import com.fredtm.core.model.TimeActivity;

public class HoursSystem extends TimeSystem {


	public HoursSystem(Collect collect) {
		super(collect);
	}

	@Override
	public Double convertTime(TimeActivity ta) {
		BigDecimal timed = new BigDecimal(ta.getTimed());
		BigDecimal mountant = new BigDecimal(1000 * 60 * 60);
		return timed.divide(mountant,4, RoundingMode.CEILING).doubleValue();
	}

	@Override
	public String getSymbol() {
		return "h";
	}



}
