package com.fredtm.export;

public class ExportationErrorExcetion extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExportationErrorExcetion() {
		super("Erro ao exportar os dados.");
	}
	
}
