package com.fredtm.core.util;

import java.util.Locale;

public class FormatElapsedTime {
	private final static Locale l = new Locale("pt-br");

	public static String format(long time) {
		String formatado = "";

		long segundos = (Long) (time / 1000) % 60;
		long minutos = (Long) ((time / (1000 * 60)) % 60);
		long horas = (Long) ((time / (1000 * 60 * 60)) % 24);

		if (horas != 0) {
			formatado = String.format(l, "%dh %dmin %ds", horas, minutos,
					segundos);
		} else if (minutos != 0 && horas == 0)
			formatado = String.format(l, "%dmin %ds", minutos, segundos);
		else if (minutos == 0 && horas == 0)
			formatado = String.format(l, "%ds", segundos);
		return formatado;
	}

}
