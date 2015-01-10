package com.fredtm.exportar;

import com.fredtm.core.model.Coleta;

public class ExportarColetaFactory {
	
	public static Exportavel<Coleta> getExportador(Exportacao exportacao) {
		switch (exportacao) {
		case CSV:
			return new ColetaToCSV();
		case JSON:
			return new ColetaToJson();
		default:
			break;
		}
		return null;
	}

}
