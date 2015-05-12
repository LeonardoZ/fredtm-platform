package com.fredtm.export;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public enum Exportation {

	CSV, OPERACOES_JSON;

	public static Collection<Exportation> toList() {
		List<Exportation> asList = new ArrayList<>(Arrays.asList(values()));
		asList.remove(OPERACOES_JSON);
		return asList;
	}

}
