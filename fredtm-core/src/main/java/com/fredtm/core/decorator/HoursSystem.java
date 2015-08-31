package com.fredtm.core.decorator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fredtm.core.model.Collect;
import com.fredtm.core.model.TimeActivity;

public class HoursSystem extends TimeSystem {


	// public static void main(String[] args) {
	// double ms = 10_251_648;
	// System.out.println((ms / (1000)));
	// System.out.println((ms / (1000 * 60)));
	// System.out.println((ms / (1000 * 60 * 60)));
	//
	//
	// }
	//
	//
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
