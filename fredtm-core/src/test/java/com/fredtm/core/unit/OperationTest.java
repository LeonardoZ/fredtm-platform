package com.fredtm.core.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.fredtm.core.model.Activity;
import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Operation;
import com.fredtm.core.model.TimeActivity;

import values.ActivityType;

public class OperationTest {

	@Test(expected = IllegalArgumentException.class)
	public void shouldInvalidateASetEmptyName() {
		Operation operation = new Operation();
		operation.setName("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldInvalidateASetNullName() {
		Operation operation = new Operation();
		operation.setName(null);
	}

	@Test
	public void shouldSetAName() {
		Operation operation = new Operation();
		operation.setName("Leo!");
		assertEquals("Leo!", operation.getName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldInvalidateASetEmptyCompany() {
		Operation operation = new Operation();
		operation.setCompany("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldInvalidateASetNullCompany() {
		Operation operation = new Operation();
		operation.setCompany(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotAddTwoQuantitativeActivity() {
		Operation operation = new Operation("AA", "BB", "CC");

		Activity activity = new Activity(operation, "Toast", "For tests", ActivityType.PRODUCTIVE, true);

		Activity activity2 = new Activity("Toast 2", "For tests 2", ActivityType.UNPRODUCTIVE);
		activity2.setOperation(operation);

		Activity activity3 = new Activity(operation, "Toast 3", "For tests 3", ActivityType.PRODUCTIVE, true);

		operation.addActivity(activity);
		operation.addActivity(activity2);
		operation.addActivity(activity3);

	}

	@Test
	public void shouldHaveQuantitative() {
		Operation operation = new Operation("AA", "BB", "CC");
		Activity activity = new Activity(operation, "Toast", "For tests", ActivityType.PRODUCTIVE, true);
		operation.addActivity(activity);
		assertTrue(operation.hasQuantitativeActivity());
	}

	@Test
	public void shouldNotHaveQuantitative() {
		Operation operation = new Operation();
		Activity activity = new Activity("Toast", "For tests", ActivityType.PRODUCTIVE);
		operation.addActivity(activity);
		assertTrue(!operation.hasQuantitativeActivity());
	}

	@Test
	public void shouldReturnQuantitativeActivity() {
		Operation operation = new Operation("AA", "BB", "CC");

		Activity activity = new Activity(operation, "Toast", "For tests", ActivityType.PRODUCTIVE, true);

		operation.addActivity(activity);

		Activity quantitativeActivity = operation.getQuantitativeActivity();
		assertEquals(activity, quantitativeActivity);
	}

	@Test
	public void shouldSetACompany() {
		Operation operation = new Operation();
		operation.setCompany("My company");
		assertEquals("My company", operation.getCompany());
	}

	@Test
	public void shouldBeEquals() {
		Operation op1 = new Operation("A", "B", "C");
		Operation op2 = new Operation("A", "B", "C");
		assertTrue(op1.equals(op2));
	}

	@Test
	public void shouldNotBeEquals() {
		Operation op1 = new Operation("A", "B", "C");
		Operation op2 = new Operation("A1", "B", "C");
		assertTrue(!op1.equals(op2));
	}

	@Test
	public void operationWasModifiedAfterThisDate() {
		Operation op = new Operation("Drink coke", "Coca-cola", "Full of coke with ice cubes");
		Calendar instance = GregorianCalendar.getInstance();
		instance.set(2014, 11, 27, 10, 22, 53);
		op.setModified(instance.getTime());
		instance.set(2014, 10, 29, 12, 03, 21);
		Date oldModification = instance.getTime();
		assertTrue(op.wasModifiedAfter(oldModification));
	}

	@Test
	public void operationNotModifiedAfterThisDate() {
		Operation op = new Operation("Drink coke", "Coca-cola", "Full of coke with ice cubes");
		Calendar instance = GregorianCalendar.getInstance();
		instance.set(2014, 11, 27, 10, 22, 53);
		op.setModified(instance.getTime());
		instance.add(Calendar.DATE, 1);
		Date newModification = instance.getTime();

		assertTrue(op.wasModifiedBefore(newModification));
	}

	@Test
	public void rightCollectNumbers() {
		Operation op = new Operation("Test OP", "From this factory", "With the description...");

		for (int i = 0; i < 10; i++) {

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
			op.addCollect(c);
		}
		System.out.println(op.numberOfCollectsIsRight(0.05));
	}

}
