package com.fredtm.core.unit;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fredtm.core.model.Activity;
import com.fredtm.core.model.ActivityType;
import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Operation;
import com.fredtm.core.model.TimeActivity;

public class TimeActivityTest {


	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionOnNullActivity() {
		TimeActivity timeActivity = new TimeActivity();
		timeActivity.setActivity(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionOnNullCollect() {
		TimeActivity timeActivity = new TimeActivity();
		timeActivity.setCollect(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionOnNullStartDate() {
		TimeActivity timeActivity = new TimeActivity();
		timeActivity.setStartDate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionOnNullFinalDate() {
		TimeActivity timeActivity = new TimeActivity();
		timeActivity.setFinalDate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionOnNullTimed() {
		TimeActivity timeActivity = new TimeActivity();
		timeActivity.setTimed(null);
	}

	@Test
	public void elapsedTimeMustBe11000ms() {
		TimeActivity timeActivity = new TimeActivity();
		long currentTimeMillis = System.currentTimeMillis();
		timeActivity.setStartDate(currentTimeMillis);
		currentTimeMillis += 11000;
		timeActivity.setFinalDate(currentTimeMillis);
		assertEquals(11000l, timeActivity.getElapsedTime().longValue());
	}

	@Test
	public void timedInSecondsMustBe110s() {
		TimeActivity timeActivity = new TimeActivity();
		long baseTime = System.currentTimeMillis();
		long currentTime = baseTime + 11000;
		timeActivity.setTimed(currentTime - baseTime);
		assertEquals(11l, timeActivity.getEllapsedTimeInSeconds());
	}

	@Test
	public void elapsedTimeMustBeFormatted() {
		Operation operacao = new Operation("A", "B", "C");
		Activity activity = new Activity(operacao, "AA", "This is a...",
				ActivityType.AUXILIARY, false);
		TimeActivity timeActivity = new TimeActivity();
		timeActivity.setActivity(activity);
		timeActivity.setCollect(new Collect());

		// 3 hours 33 minutes 48 seconds
		long baseTime = 12_828_000;
		// 3 hours 33 minutes 58 seconds
		long currentTime = baseTime + 10_000;

		timeActivity.setStartDate(baseTime);
		timeActivity.setFinalDate(currentTime);
		timeActivity.setTimed(currentTime - baseTime);

		String formattedEllapsedTime = timeActivity
				.getFormattedEllapsedTime(false);
		assertEquals("10s", formattedEllapsedTime);
	}
	

	@Test
	public void productiveElapsedTimeMustBeFormatted() {
		Operation operacao = new Operation("A", "B", "C");
		Activity activity = new Activity(operacao, "AA", "This is a...",
				ActivityType.PRODUCTIVE, true);
		activity.setItemName("Tests");
		TimeActivity timeActivity = new TimeActivity();
		timeActivity.setActivity(activity);
		timeActivity.setCollect(new Collect());
		timeActivity.setCollectedAmount(12);
		// 3 hours 33 minutes 48 seconds
		long baseTime = 12_828_000;
		// 3 hours 33 minutes 58 seconds
		long currentTime = baseTime + 10_000;

		timeActivity.setStartDate(baseTime);
		timeActivity.setFinalDate(currentTime);
		timeActivity.setTimed(currentTime - baseTime);

		String formattedEllapsedTime = timeActivity
				.getFormattedEllapsedTime(false);
		assertEquals("10s - Tests: 12", formattedEllapsedTime);
	}
	
	@Test
	public void productiveElapsedTimeMustBeFormattedWithABreak() {
		Operation operacao = new Operation("A", "B", "C");
		Activity activity = new Activity(operacao, "AA", "This is a...",
				ActivityType.PRODUCTIVE, true);
		activity.setItemName("Tests");
		TimeActivity timeActivity = new TimeActivity();
		timeActivity.setActivity(activity);
		timeActivity.setCollect(new Collect());
		timeActivity.setCollectedAmount(12);
		// 3 hours 33 minutes 48 seconds
		long baseTime = 12_828_000;
		// 3 hours 33 minutes 58 seconds
		long currentTime = baseTime + 10_000;

		timeActivity.setStartDate(baseTime);
		timeActivity.setFinalDate(currentTime);
		timeActivity.setTimed(currentTime - baseTime);

		String formattedEllapsedTime = timeActivity
				.getFormattedEllapsedTime(true);
		assertEquals("10s\nTests: 12", formattedEllapsedTime);
	}
	
	
	@Test
	public void simpleElapsedTimeMustBeFormatted() {
		Operation operacao = new Operation("A", "B", "C");
		Activity activity = new Activity(operacao, "AA", "This is a...",
				ActivityType.PRODUCTIVE, true);
		activity.setItemName("Tests");
		TimeActivity timeActivity = new TimeActivity();
		timeActivity.setActivity(activity);
		timeActivity.setCollect(new Collect());
		timeActivity.setCollectedAmount(12);
		// 3 hours 33 minutes 48 seconds
		long baseTime = 12_828_000;
		// 3 hours 33 minutes 58 seconds
		long currentTime = baseTime + 10_000;

		timeActivity.setStartDate(baseTime);
		timeActivity.setFinalDate(currentTime);
		timeActivity.setTimed(currentTime - baseTime);

		String formattedEllapsedTime = timeActivity
				.getSimpleEllapsedTime();
		assertEquals("10s", formattedEllapsedTime);
	}

	
	@Test
	public void fullElapsedTimeMustBeFormatted() {
		Operation operacao = new Operation("A", "B", "C");
		Activity activity = new Activity(operacao, "AA", "This is a...",
				ActivityType.PRODUCTIVE, true);
		activity.setItemName("Tests");
		TimeActivity timeActivity = new TimeActivity();
		timeActivity.setActivity(activity);
		timeActivity.setCollect(new Collect());
		timeActivity.setCollectedAmount(12);
		// 3 hours 33 minutes 48 seconds
		long baseTime = 12_828_000;
		// 3 hours 33 minutes 58 seconds
		long currentTime = baseTime + 10_000;

		timeActivity.setStartDate(baseTime);
		timeActivity.setFinalDate(currentTime);
		timeActivity.setTimed(currentTime - baseTime);

		SimpleDateFormat sdf = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm:ss", new Locale("pt", "BR"));
		String start = sdf.format(new Date(baseTime));
		String end = sdf.format(new Date(currentTime));
		
		String formattedEllapsedTime = timeActivity
				.getFullElapsedTime();
		assertEquals(start + " - " + end + " : " + "10s - Tests: 12", formattedEllapsedTime);
	}
}
