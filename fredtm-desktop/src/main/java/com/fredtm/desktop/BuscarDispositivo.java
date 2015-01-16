package com.fredtm.desktop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.fredtm.core.model.Operacao;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class BuscarDispositivo {

	private File selectedDirectory;
	private List<Operacao> operacoes = new LinkedList<Operacao>();
	private Gson gson = new Gson();

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
		converterJsonParaJava(file);
	}

	private void converterJsonParaJava(File f) {
		try {
			Operacao []fromJson = gson
					.fromJson(new FileReader(f), Operacao[].class);
			operacoes.addAll(Arrays.asList(fromJson));
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
