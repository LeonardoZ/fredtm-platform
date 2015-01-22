package com.fredtm.core.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fredtm.core.model.Operacao;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class OperacoesJsonUtils {

	public Gson gson = new Gson();

	public Optional<List<Operacao>> converterJsonParaJava(File f) {
		List<Operacao> operacoes = new ArrayList<Operacao>();
		try {
			Operacao[] fromJson = gson.fromJson(new FileReader(f),
					Operacao[].class);
			operacoes.addAll(Arrays.asList(fromJson));
			return Optional.of(operacoes);
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public Optional<List<Operacao>> converterJsonParaJava(String s) {
		List<Operacao> operacoes = new ArrayList<Operacao>();
		try {
			Operacao[] fromJson = gson.fromJson(s, Operacao[].class);
			operacoes.addAll(Arrays.asList(fromJson));
			return Optional.of(operacoes);
		} catch (JsonSyntaxException | JsonIOException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
}