package com.fredtm.exportar;

import com.fredtm.core.model.Collect;

public class ExportarColetaFactory {
	
	public static Exportavel<Collect> getExportador(Exportacao exportacao) {
		switch (exportacao) {
		case CSV:
			return new ColetaToCSV();
//		case JSON:
//			return new ColetaToJson();
		
		default:
			break;
		}
		return null;
	}

}
