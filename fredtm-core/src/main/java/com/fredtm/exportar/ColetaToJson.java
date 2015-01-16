package com.fredtm.exportar;

import java.util.List;

import com.fredtm.core.model.Coleta;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ColetaToJson implements Exportavel<Coleta> {

	private Gson gson = new GsonBuilder().create();

	@Override
	public void exportar(Coleta coleta, String caminho) {
		String json = gson.toJson(coleta);
		System.err.println(json);
	}

	@Override
	public void exportar(List<Coleta> coletas, String caminho) {
		String json = gson.toJson(coletas);
		System.err.println(json);
	}

}
