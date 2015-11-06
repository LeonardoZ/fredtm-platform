package com.fredtm.core.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fredtm.core.util.FormatElapsedTime;

public class FormatElapsedTimeTest {

	@Test
	public void formattedTimeShouldBeInHours() {
		String output = FormatElapsedTime.format(10_800_000);
		assertEquals("3h 0min 0s", output);
	}

	@Test
	public void formattedTimeShouldBeInHoursMinutes() {
		String output = FormatElapsedTime.format(11_640_000);
		assertEquals("3h 14min 0s", output);
	}

	@Test
	public void formattedTimeShouldBeInHoursMinutesSeconds() {
		String output = FormatElapsedTime.format(12_838_000);
		assertEquals("3h 33min 58s", output);
	}

}

