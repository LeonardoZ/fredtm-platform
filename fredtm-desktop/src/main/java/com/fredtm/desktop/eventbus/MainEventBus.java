package com.fredtm.desktop.eventbus;

import java.util.List;

import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Operation;
import com.fredtm.desktop.controller.MainController;
import com.fredtm.desktop.controller.utils.TiposGrafico;

public class MainEventBus {

	public static MainEventBus INSTANCE = new MainEventBus();
	private MainController main;

	private MainEventBus() {
	}

	public void registrarOuvinte(MainController main) {
		if (this.main != null) {
			throw new IllegalArgumentException("Ouvinte já registrado!");
		}
		this.main = main;
	}

	public void eventoAbrirAtividades(Operation operation) {
		main.abrirAtividades(operation);
	}

	public void eventoAbrircollects(Operation operation) {
		main.abrircollects(operation);
	}

	public void eventoExportarAtividades(Operation operation) {
		main.habilitarExportarAtividades(operation);
	}

	public void eventoExportarcollects(List<Collect> collects) {
		main.exportarcollects(collects);
	}

	public void eventoAbrirTemposcollectdos(Collect collect) {
		main.abrirTemposcollectdos(collect);
	}

	public void eventoTiposDeGraficos(Collect collect, List<Collect> collects) {
		main.abrirTiposDeGraficos(collect, collects);
	}

	public void eventoAnaliseGrafica(TiposGrafico tipo, Collect collect,
			List<Collect> collects) {
		main.abrirAnaliseGrafica(tipo, collect, collects);
	}

}
