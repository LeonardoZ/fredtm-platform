package com.fredtm.exportar;

import java.util.List;

import com.fredtm.core.model.Collect;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ColetaToJson implements Exportavel<Collect> {

	private Gson gson = new GsonBuilder().create();

	@Override
	public void exportar(Collect coleta, String caminho) {
		String json = gson.toJson(coleta);
		salvarEmArquivo(caminho, json);
	}

	@Override
	public void exportar(List<Collect> coletas, String caminho) {
		String json = gson.toJson(coletas);
		salvarEmArquivo(caminho, json);
	}

	

}
