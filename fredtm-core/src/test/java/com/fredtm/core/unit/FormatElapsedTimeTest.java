package com.fredtm.core.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fredtm.core.util.FormatElapsedTime;

public class FormatElapsedTimeTest {

	@Test
	public void formattedTimeShouldBeIn0() {
		// 52 Minutes in miliseconds
		String output = FormatElapsedTime.format(0);
		assertEquals("0s", output);
	}

	@Test
	public void formattedTimeShouldBeInSeconds() {
		String output = FormatElapsedTime.format(23_000);
		assertEquals("23s", output);
	}

	@Test
	public void formattedTimeShouldBeInMinutes() {
		String output = FormatElapsedTime.format(3_120_000);
		assertEquals("52min 0s", output);
	}

	@Test
	public void formattedTimeShouldBeInMinutesSeconds() {
		String output = FormatElapsedTime.format(3_220_000);
		assertEquals("53min 40s", output);
	}

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
