package com.fredtm.desktop;

import java.io.File;
import java.util.List;
import java.util.Optional;

import com.fredtm.core.model.Operacao;
import com.fredtm.core.util.OperacoesJsonUtils;

public class BuscarDispositivo {

	private File selectedDirectory;
	private OperacoesJsonUtils jsonUtils = new OperacoesJsonUtils();
	private List<Operacao> operacoes;

	public BuscarDispositivo(File selectedDirectory) {
		this.selectedDirectory = selectedDirectory;
		buscarEmDispositivo();
	}
	
	public Optional<List<Operacao>> getOperacoes() {
		return operacoes.isEmpty() ? Optional.empty() : Optional.ofNullable(operacoes);
	}

	private void buscarEmDispositivo() {
		File file = new File(selectedDirectory.getPath()
				+ "/Documents/fred_tm/json-export/operacoes.json");
		operacoes = jsonUtils.converterJsonParaJava(file).get();
	}

	

}
