package com.fredtm.core.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fredtm.core.model.Operation;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class OperacoesJsonUtils {

	public Gson gson = new Gson();

	public Optional<List<Operation>> converterJsonParaJava(File f) {
		List<Operation> operations = new ArrayList<Operation>();
		try {
			Operation[] fromJson = gson.fromJson(new FileReader(f),
					Operation[].class);
			operations.addAll(Arrays.asList(fromJson));
			return Optional.of(operations);
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public Optional<List<Operation>> converterJsonParaJava(String s) {
		List<Operation> operations = new ArrayList<Operation>();
		try {
			Operation[] fromJson = gson.fromJson(s, Operation[].class);
			operations.addAll(Arrays.asList(fromJson));
			return Optional.of(operations);
		} catch (JsonSyntaxException | JsonIOException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
}