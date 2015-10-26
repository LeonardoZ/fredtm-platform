package com.fredtm.core.unit;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.fredtm.core.model.Activity;
import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Operation;
import com.fredtm.core.model.TimeActivity;

import values.ActivityType;
import values.ToleranceFactor;

public class ToleranceFactorTest {

	@Test
	public void testCalculatedValue() {

		Operation operation = new Operation("Test OP", "From this factory", "With the description...");
		Collect c = new Collect();
		Activity a1 = new Activity(operation, "Cut", "That's cuts", ActivityType.PRODUCTIVE, false);
		Activity a2 = new Activity(operation, "Rest", "Personal Needs/Rest Time", ActivityType.UNPRODUCTIVE, false);
		a2.setIsIdleActivity(true);
		Activity a3 = new Activity(operation, "Get tools in lockroom", "Employe goes to the lockroom to get his tools.",
				ActivityType.AUXILIARY, false);

		// prepare times
		TimeActivity taAux = new TimeActivity(a3, c);
		// 0,5h
		taAux.setTimed(1_800_000l);

		TimeActivity taProd = new TimeActivity(a1, c);
		// 1,5h
		taProd.setTimed(5_400_000l);

		TimeActivity taUnprod = new TimeActivity(a2, c);
		// 0,3h
		taUnprod.setTimed(1_080_000l);

		TimeActivity taProd2 = new TimeActivity(a1, c);
		// 1,4h
		taProd2.setTimed(5_040_000l);

		// total: 3,7h

		Set<Activity> acts = new HashSet<>(Arrays.asList(a1, a2, a3));
		List<TimeActivity> times = Arrays.asList(taAux, taProd, taUnprod, taProd2);

		c.setActivities(acts);
		c.setTimes(times);

		BigDecimal calculate = new ToleranceFactor()
				.workingTimes(c.getWorkTimes())
				.intervalTimes(c.getIntervalTimes())
				.calculate();
		Assert.assertEquals(calculate.doubleValue(), 1.097d, 0.0001);
	}
	
	@Test
	public void testManualInputValue() {
		ToleranceFactor tf = new ToleranceFactor();
		tf.setIntervalTotal(1_080_000l);
		tf.setWorkingTotal(12_240_000l);
		Assert.assertEquals(tf.calculate().doubleValue(), 1.097d, 0.0001);
	}

	@Test
	public void testManualInputValue2() {
		ToleranceFactor tf = new ToleranceFactor();
		tf.setIntervalTotal(12 + 15 + 20);
		tf.setWorkingTotal(480);
		Assert.assertEquals(tf.calculate().doubleValue(), 1.109d, 0.0001);
	}

}
