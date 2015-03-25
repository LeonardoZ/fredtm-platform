package com.fredtm.exportar;

import java.util.Comparator;
import java.util.List;

import com.fredtm.core.model.Operation;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OperacoesToJson implements Exportavel<Operation> {

	 private Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
         @Override
         public boolean shouldSkipField(FieldAttributes f) {
             return false;
         }

         @Override
         public boolean shouldSkipClass(Class<?> clazz) {
             return clazz.equals(Comparator.class);
         }
     }).create();
	@Override
	public void exportar(Operation t, String caminho)
			throws ErroDeExportacaoExcetion {
		String json = gson.toJson(t);
		salvarEmArquivo(caminho, json);

	}

	@Override
	public void exportar(List<Operation> t, String caminho)
			throws ErroDeExportacaoExcetion {
		String json = gson.toJson(t);
		salvarEmArquivo(caminho, json);
	}

}
