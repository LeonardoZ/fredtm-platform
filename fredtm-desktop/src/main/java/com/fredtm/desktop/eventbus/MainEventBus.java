package com.fredtm.desktop.eventbus;

import java.util.List;

import com.fredtm.core.model.Coleta;
import com.fredtm.core.model.Operacao;
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

	public void eventoAbrirAtividades(Operacao operacao) {
		main.abrirAtividades(operacao);
	}

	public void eventoAbrirColetas(Operacao operacao) {
		main.abrirColetas(operacao);
	}

	public void eventoExportarAtividades(Operacao operacao) {
		main.habilitarExportarAtividades(operacao);
	}

	public void eventoExportarColetas(List<Coleta> coletas) {
		main.exportarColetas(coletas);
	}

	public void eventoAbrirTemposColetados(Coleta coleta) {
		main.abrirTemposColetados(coleta);
	}

	public void eventoCriarGraficoPizza(Coleta coleta) {
		main.criarGraficoPizza(coleta);
	}

}
