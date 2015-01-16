package com.fredtm.exportar;

public class ErroDeExportacaoExcetion extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ErroDeExportacaoExcetion() {
		super("Erro ao exportar os dados.");
	}
	
}
