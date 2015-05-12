package com.fredtm.export;

import java.util.List;

import com.fredtm.core.model.Collect;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CollectToJson implements Exportable<Collect> {

	private Gson gson = new GsonBuilder().create();

	@Override
	public void export(Collect collect, String path) {
		String json = gson.toJson(collect);
		saveInFile(path, json);
	}

	@Override
	public void export(List<Collect> collects, String path) {
		String json = gson.toJson(collects);
		saveInFile(path, json);
	}

	

}
