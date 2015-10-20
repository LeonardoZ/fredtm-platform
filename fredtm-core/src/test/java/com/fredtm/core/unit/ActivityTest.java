package com.fredtm.core.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import com.fredtm.core.model.Activity;
import com.fredtm.core.model.Operation;

import values.ActivityType;

public class ActivityTest {

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionOnEmptyTitle() {
		Activity activity = new Activity();
		activity.setTitle("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionOnNullTitle() {
		Activity activity = new Activity();
		activity.setTitle(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionOnNullOperation() {
		Activity activity = new Activity();
		activity.setOperation(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWithNullActivityType() {
		Activity activity = new Activity();
		activity.setActivityType(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void onlyProductiveActivityShouldBeQuantitative() {
		new Activity("Imp", "imp", ActivityType.UNPRODUCTIVE, true);
	}

	@Test
	public void invalidActivityTypeIdShouldBeTheDefualtValueProductive() {
		Activity activity = new Activity();
		activity.setActivityType(334232);
		Assert.assertEquals(ActivityType.PRODUCTIVE, activity.getActivityType());
	}

	@Test
	public void validActivityTypeIddShouldBeImproductive() {
		Activity activity = new Activity();
		activity.setActivityType(0);
		assertEquals(ActivityType.UNPRODUCTIVE, activity.getActivityType());
	}

	@Test
	public void shouldBeEquals() {
		Operation operacao = new Operation("A", "B", "C");
		Activity a1 = new Activity(operacao, "AA", "This is a...",
				ActivityType.AUXILIARY, false);
		Activity a2 = new Activity(operacao, "AA", "This is a...",
				ActivityType.AUXILIARY, false);
		assertTrue(a1.equals(a2));
	}

	@Test
	public void shouldNotBeEquals() {
		Operation operacao = new Operation("A", "B", "C");
		Activity a1 = new Activity(operacao, "AA", "This is a...",
				ActivityType.AUXILIARY, false);
		Activity a2 = new Activity(operacao, "AAb", "This is a....",
				ActivityType.UNPRODUCTIVE, false);
		assertTrue(!a1.equals(a2));
	}

}
