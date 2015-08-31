package com.fredtm.export;

import java.util.List;

import com.fredtm.core.model.Operation;
import com.fredtm.core.util.FredObjectMapper;
import com.fredtm.resources.OperationResource;
import com.fredtm.resources.base.GsonFactory;
import com.google.gson.Gson;

public class OperationsToJson implements Exportable<Operation> {

	 private Gson gson = GsonFactory.getGson();
	 
	 
	@Override
	public void export(Operation t, String path)
			throws ExportationErrorExcetion {
	}

	@Override
	public void export(List<Operation> t, String path)
			throws ExportationErrorExcetion {
		List<OperationResource> resources = FredObjectMapper.mapEntitiesToResources(t);
		String json = gson.toJson(resources);
		saveInFile(path, json);
	}

}
