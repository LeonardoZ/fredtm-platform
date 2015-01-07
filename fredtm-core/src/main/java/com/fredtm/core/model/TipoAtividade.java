package com.fredtm.core.model;

import java.util.Arrays;
import java.util.Optional;

public enum TipoAtividade {
	
	IMPRODUTIVA(0,"#ff7d77"), AUXILIAR(1,"#ffe0a2"), PRODUTIVA(2,"#a2deff");

	private int tipoAtividade;
	private String hexColor;

	TipoAtividade(int tipoAtividade,String hexColor) {
		this.tipoAtividade = tipoAtividade;
		this.hexColor = hexColor;
	}

	public static Optional<TipoAtividade> getByIdentificacao(int tipoAtividade) {
		return Arrays.stream(values()).filter(t -> t.getTipoAtividade()==tipoAtividade).findFirst();
	}

	public String getHexColor() {
		return hexColor;
	}
	
	public int getTipoAtividade() {
		return tipoAtividade;
	}
}
