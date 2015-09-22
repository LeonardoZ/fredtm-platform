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

import com.fredtm.resources.OperationDTO;
import com.fredtm.resources.base.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class OperationJsonUtils {

	public Gson gson;
	private Type listType = new TypeToken<List<OperationDTO>>() {
	}.getType();

	public OperationJsonUtils() {
		gson = GsonFactory.getGson();
	}

	public List<OperationDTO> jsonToJava(Reader f) {
		JsonReader jsonReader = new JsonReader(f);
		List<OperationDTO> fromJson = gson.fromJson(jsonReader, listType);
		return fromJson;
	}
	
	public List<OperationDTO> jsonToJava(File f) {
		List<OperationDTO> operations = new LinkedList<>();
		try {
			OperationDTO[] fromJson = getJsonFrom(f);
			operations.addAll(Arrays.asList(fromJson));
			return operations;
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
		}
		return new LinkedList<>();
	}

	public List<OperationDTO> jsonToJava(String f) {
		List<OperationDTO> operations = null;
		try {
			operations = getJsonFrom(f);
		} catch (JsonSyntaxException | JsonIOException e) {
			e.printStackTrace();
		}
		return operations;
	}
	
	public OperationDTO jsonElementToJava(String f) {
		return gson.fromJson(f, OperationDTO.class);
	}

	private List<OperationDTO> getJsonFrom(String f) {
		return gson.fromJson(f, listType);
	}
	
	

	private OperationDTO[] getJsonFrom(File f) throws FileNotFoundException {
		InputStreamReader reader = new InputStreamReader(
				new FileInputStream(f.getAbsolutePath()),Charset.forName("UTF-8")
		);
		return gson.fromJson(reader, OperationDTO[].class);
	}

}