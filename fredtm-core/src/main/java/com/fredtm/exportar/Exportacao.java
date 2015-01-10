package com.fredtm.exportar;

import java.util.Arrays;
import java.util.Collection;

public enum Exportacao {

	JSON, XML, CSV;

	public static Collection<Exportacao> toList() {
		return Arrays.asList(values());
	}

}
