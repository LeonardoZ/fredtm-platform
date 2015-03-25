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

	public void eventoAbrirColetas(Operation operation) {
		main.abrirColetas(operation);
	}

	public void eventoExportarAtividades(Operation operation) {
		main.habilitarExportarAtividades(operation);
	}

	public void eventoExportarColetas(List<Collect> coletas) {
		main.exportarColetas(coletas);
	}

	public void eventoAbrirTemposColetados(Collect coleta) {
		main.abrirTemposColetados(coleta);
	}

	public void eventoTiposDeGraficos(Collect coleta, List<Collect> coletas) {
		main.abrirTiposDeGraficos(coleta, coletas);
	}

	public void eventoAnaliseGrafica(TiposGrafico tipo, Collect coleta,
			List<Collect> coletas) {
		main.abrirAnaliseGrafica(tipo, coleta, coletas);
	}

}
