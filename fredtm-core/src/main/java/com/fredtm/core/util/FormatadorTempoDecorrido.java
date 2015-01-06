package com.fredtm.core.util;

import java.util.Locale;

public class FormatadorTempoDecorrido {
	private final static Locale l = new Locale("pt-br");

	public static String formatarTempo(long tempoCronometrado) {
		String formatado = "";

		long segundos = (Long) (tempoCronometrado / 1000) % 60;
		long minutos = (Long) ((tempoCronometrado / (1000 * 60)) % 60);
		long horas = (Long) ((tempoCronometrado / (1000 * 60 * 60)) % 24);

		if (horas != 0) {
			formatado = String.format(l, "%dh %dmin %ds", segundos, minutos,
					segundos);
		} else if (minutos != 0 && horas == 0)
			formatado = String.format(l, "%dmin %ds", minutos, segundos);
		else if (minutos == 0 && horas == 0)
			formatado = String.format(l, "%ds", segundos);
		return formatado;
	}

}
