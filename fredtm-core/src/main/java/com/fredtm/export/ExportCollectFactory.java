package com.fredtm.export;

import com.fredtm.core.model.Collect;

public class ExportCollectFactory {

	public static Exportable<Collect> getExporter(Exportation exportable) {
		switch (exportable) {
		case CSV:
			return new CollectToCSV();
			// case JSON:
			// return new collectToJson();

		default:
			break;
		}
		return null;
	}

}
