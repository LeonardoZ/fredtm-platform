package com.fredtm.desktop;

import java.io.File;
import java.util.List;
import java.util.Optional;

import com.fredtm.core.model.Operation;
import com.fredtm.core.util.OperacoesJsonUtils;

public class BuscarDispositivo {

	private File selectedDirectory;
	private OperacoesJsonUtils jsonUtils = new OperacoesJsonUtils();
	private List<Operation> operations;

	public BuscarDispositivo(File selectedDirectory) {
		this.selectedDirectory = selectedDirectory;
		buscarEmDispositivo();
	}
	
	public Optional<List<Operation>> getOperacoes() {
		return operations.isEmpty() ? Optional.empty() : Optional.ofNullable(operations);
	}

	private void buscarEmDispositivo() {
		File file = new File(selectedDirectory.getPath()
				+ "/Documents/fred_tm/json-export/operations.json");
		operations = jsonUtils.converterJsonParaJava(file).get();
	}
	

}
