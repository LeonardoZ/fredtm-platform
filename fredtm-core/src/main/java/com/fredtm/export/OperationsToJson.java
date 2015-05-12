package com.fredtm.export;

import java.util.Comparator;
import java.util.List;

import com.fredtm.core.model.Operation;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OperationsToJson implements Exportable<Operation> {

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
	public void export(Operation t, String path)
			throws ExportationErrorExcetion {
		String json = gson.toJson(t);
		saveInFile(path, json);
	}

	@Override
	public void export(List<Operation> t, String path)
			throws ExportationErrorExcetion {
		String json = gson.toJson(t);
		saveInFile(path, json);
	}

}
