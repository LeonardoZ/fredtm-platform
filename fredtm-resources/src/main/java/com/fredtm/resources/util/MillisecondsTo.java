package com.fredtm.resources.util;

public class MillisecondsTo {

	public static double hours(long millis) {
		return (double) millis / (3600 * 1000);
	}

	public static double minutes(long millis) {
		return (double) millis / (1000 * 1000);
	}

	public static double seconds(long millis) {
		return (double) millis / (3600 * 1000);
	}
}
