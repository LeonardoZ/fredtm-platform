package com.fredtm.core.unit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.fredtm.core.decorator.TimeMeasure;
import com.fredtm.core.model.Activity;
import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Operation;
import com.fredtm.core.model.TimeActivity;

import values.ActivityType;

public class CollectTest {

	

	@Test
	public void shoudBeTheSpecifiedNormalTimeValue() {

		Operation op = new Operation("Test OP", "From this factory", "With the description...");

		Collect c = new Collect();
		c.setGeneralSpeed(130);

		Activity a1 = new Activity(op, "Cut", "That's cuts", ActivityType.PRODUCTIVE, false);
		Activity a2 = new Activity(op, "Rest", "Personal Needs/Rest Time", ActivityType.UNPRODUCTIVE, false);
		a2.setIsIdleActivity(true);
		Activity a3 = new Activity(op, "Get tools in lockroom", "Employe goes to the lockroom to get his tools.",
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

		Set<Activity> acts = new HashSet<>(Arrays.asList(a1, a2, a3));
		c.setActivities(acts);

		List<TimeActivity> times = Arrays.asList(taAux, taProd, taUnprod, taProd2);
		c.setTimes(times);

		BigDecimal normalTimeInHours = c.getNormalTime(TimeMeasure.HOURS);
		Assert.assertEquals(4.8, normalTimeInHours.doubleValue(),0.1);
	}

	public void shoudBeTheSpecifiedStandardTimeValue() {

		Operation op = new Operation("Test OP", "From this factory", "With the description...");

		Collect c = new Collect();
		c.setGeneralSpeed(130);

		Activity a1 = new Activity(op, "Cut", "That's cuts", ActivityType.PRODUCTIVE, false);
		Activity a2 = new Activity(op, "Rest", "Personal Needs/Rest Time", ActivityType.UNPRODUCTIVE, false);
		a2.setIsIdleActivity(true);
		Activity a3 = new Activity(op, "Get tools in lockroom", "Employe goes to the lockroom to get his tools.",
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

		Set<Activity> acts = new HashSet<>(Arrays.asList(a1, a2, a3));
		c.setActivities(acts);

		List<TimeActivity> times = Arrays.asList(taAux, taProd, taUnprod, taProd2);
		c.setTimes(times);

		BigDecimal standardTime = c.getStandardTime(TimeMeasure.HOURS);
		double doubleValue = standardTime.divide(BigDecimal.valueOf(1000 * 3600), 3, RoundingMode.HALF_UP)
				.doubleValue();
		Assert.assertEquals(5.277, doubleValue, 0.0001d);
	}

}
