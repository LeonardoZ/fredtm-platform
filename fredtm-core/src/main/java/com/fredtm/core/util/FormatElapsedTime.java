package com.fredtm.core.util;

import java.util.Locale;

public class FormatElapsedTime {
	private final static Locale l = new Locale("pt-br");

	public static String format(long time) {
		String formatted = "";

		long secs = (long) (time / 1000) % 60;
		long mins = (long) ((time / (1000 * 60)) % 60);
		long hours = (long) ((time / (1000 * 60 * 60)) % 24);

		if (hours != 0) {
			formatted = String.format(l, "%dh %dmin %ds", hours, mins,
					secs);
		} else if (mins != 0 && hours == 0){
			formatted = String.format(l, "%dmin %ds", mins, secs);
		} else if (mins == 0 && hours == 0){
			formatted = String.format(l, "%ds", secs);
		}
		return formatted;
	}

}
