package com.fredtm.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.fredtm.resources.OperationResource;
import com.fredtm.resources.base.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class OperationJsonUtils {

	public Gson gson;
	private Type listType = new TypeToken<List<OperationResource>>() {
	}.getType();

	public OperationJsonUtils() {
		gson = GsonFactory.getGson();
	}

	public List<OperationResource> jsonToJava(Reader f) {
		JsonReader jsonReader = new JsonReader(f);
		List<OperationResource> fromJson = gson.fromJson(jsonReader, listType);
		return fromJson;
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
		List<OperationResource> operations = null;
		try {
			operations = getJsonFrom(f);
		} catch (JsonSyntaxException | JsonIOException e) {
			e.printStackTrace();
		}
		return operations;
	}
	
	public OperationResource jsonElementToJava(String f) {
		return gson.fromJson(f, OperationResource.class);
	}

	private List<OperationResource> getJsonFrom(String f) {
		return gson.fromJson(f, listType);
	}
	
	

	private OperationResource[] getJsonFrom(File f) throws FileNotFoundException {
		InputStreamReader reader = new InputStreamReader(
				new FileInputStream(f.getAbsolutePath()),Charset.forName("UTF-8")
		);
		return gson.fromJson(reader, OperationResource[].class);
	}

}