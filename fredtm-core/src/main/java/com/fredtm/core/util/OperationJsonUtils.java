package com.fredtm.core.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.fredtm.resources.OperationResource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class OperationJsonUtils {

	public Gson gson;
	
	public OperationJsonUtils() {
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm:ss").create();
	}
	
	public List<OperationResource> jsonToJava(File f) {
		List<OperationResource> operations = new LinkedList<>();
		try {
			OperationResource[] fromJson = getJsonFrom(f);
			operations.addAll(Arrays.asList(fromJson));
			return operations;
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
		}
		return new LinkedList<>();
	}


	public List<OperationResource> jsonToJava(String f) {
		List<OperationResource> operations = new LinkedList<>();
		try {
			OperationResource[] fromJson = getJsonFrom(f);
			operations.addAll(Arrays.asList(fromJson));
			return operations;
		} catch (JsonSyntaxException | JsonIOException  e) {
			e.printStackTrace();
		}
		return new LinkedList<>();
	}


	private OperationResource[] getJsonFrom(String f) {
		return gson.fromJson(f,
				OperationResource[].class);
	}
	private OperationResource[] getJsonFrom(File f) throws FileNotFoundException {
		return gson.fromJson(new FileReader(f),
				OperationResource[].class);
	}

	
}