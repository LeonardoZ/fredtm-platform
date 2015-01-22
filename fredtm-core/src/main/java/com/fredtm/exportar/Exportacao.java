package com.fredtm.exportar;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public enum Exportacao {

	CSV, OPERACOES_JSON;

	public static Collection<Exportacao> toList() {
		List<Exportacao> asList = Arrays.asList(values());
		asList.remove(OPERACOES_JSON);
		return asList;
	}

}
