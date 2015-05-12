package com.fredtm.export;

import com.fredtm.core.model.Collect;

public class ExportCollectFactory {
	
	public static Exportable<Collect> getExporter(Exportation exportation) {
		switch (exportation) {
		case CSV:
			return new CollectToCSV();
//		case JSON:
//			return new collectToJson();
		
		default:
			break;
		}
		return null;
	}

}
