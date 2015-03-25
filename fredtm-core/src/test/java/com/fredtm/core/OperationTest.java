package com.fredtm.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.fredtm.core.model.Activity;
import com.fredtm.core.model.ActivityType;
import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Operation;

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

		Activity activity = new Activity(operation, "Toast", "For tests",
				ActivityType.PRODUCTIVE, true);

		Activity activity2 = new Activity("Toast 2", "For tests 2",
				ActivityType.IMPRODUCTIVE);
		activity2.setOperation(operation);

		Activity activity3 = new Activity(operation, "Toast 3", "For tests 3",
				ActivityType.PRODUCTIVE, true);

		operation.addActivity(activity);
		operation.addActivity(activity2);
		operation.addActivity(activity3);

	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotAddTheSameCollect() {
		Collect collect = new Collect();
		collect.setId(12);

		Collect collect2 = new Collect();
		collect.setId(13);

		Collect collect3 = new Collect();
		collect.setId(12);

		Operation operation = new Operation();
		operation.addCollect(collect);
		operation.addCollect(collect2);
		operation.addCollect(collect3);
	}

	@Test
	public void shouldHaveQuantitative() {
		Operation operation = new Operation("AA", "BB", "CC");
		Activity activity = new Activity(operation, "Toast", "For tests",
				ActivityType.PRODUCTIVE, true);
		operation.addActivity(activity);
		assertTrue(operation.hasQuantitativeActivity());
	}

	@Test
	public void shouldNotHaveQuantitative() {
		Operation operation = new Operation();
		Activity activity = new Activity("Toast", "For tests",
				ActivityType.PRODUCTIVE);
		operation.addActivity(activity);
		assertTrue(!operation.hasQuantitativeActivity());
	}

	@Test
	public void shouldReturnQuantitativeActivity() {
		Operation operation = new Operation("AA", "BB", "CC");

		Activity activity = new Activity(operation, "Toast", "For tests",
				ActivityType.PRODUCTIVE, true);

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

}
