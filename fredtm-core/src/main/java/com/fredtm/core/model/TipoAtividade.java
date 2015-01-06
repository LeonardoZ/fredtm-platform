package com.fredtm.core.model;

import java.util.Arrays;
import java.util.Optional;

public enum TipoAtividade {
	
	IMPRODUTIVA(0), AUXILIAR(1), PRODUTIVA(2);

	private int tipoAtividade;

	TipoAtividade(Integer tipoAtividade) {
		this.tipoAtividade = tipoAtividade;
	}

	public static Optional<TipoAtividade> getByIdentificacao(int tipoAtividade) {
		return Arrays.stream(values()).filter(t -> t.getTipoAtividade()==tipoAtividade).findFirst();
	}

	public int getTipoAtividade() {
		return tipoAtividade;
	}
}
