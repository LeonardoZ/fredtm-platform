package com.fredtm.desktop.eventbus;

import java.util.List;

import com.fredtm.core.model.Atividade;
import com.fredtm.core.model.Coleta;
import com.fredtm.desktop.controller.MainController;

public class MainEventBus {

	public static MainEventBus INSTANCE = new MainEventBus();
	private MainController main;

	private MainEventBus() {}

	public void registrarOuvinte(MainController main) {
		if (this.main != null) {
			throw new IllegalArgumentException("Ouvinte já registrado!");
		}
		this.main = main;
	}

	public void eventoAbrirAtividades(List<Atividade> atividades) {
		main.abrirAtividades(atividades);
	}

	public void eventoAbrirColetas(List<Coleta> coletas) {
		main.abrirColetas(coletas);
	}

	public void eventoExportarAtividades(List<Atividade> atividades) {
		// TODO Auto-generated method stub
		return null;
	}

}
