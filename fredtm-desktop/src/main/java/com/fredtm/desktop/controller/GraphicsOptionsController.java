package com.fredtm.desktop.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import com.fredtm.core.model.Collect;
import com.fredtm.desktop.controller.utils.GraphicsOptions;
import com.fredtm.desktop.eventbus.MainEventBus;

public class GraphicsOptionsController extends BaseController implements
		Initializable {

	private Collect collect;
	private List<Collect> collects;

	public void setcollect(Collect collect) {
		this.collect = collect;
	}

	public void setcollects(List<Collect> collects) {
		this.collects = collects;
	}

	@FXML
	void onDistribuicaoPizzaClicked(ActionEvent event) {
		MainEventBus.INSTANCE.eventGraphicalAnalises(
				GraphicsOptions.DISTRIBUICAO_TEMPO_ATIVIDADE_PIZZA, collect,
				collects);
	}

	@FXML
	void onClassificacaoBarrasClicked(ActionEvent event) {
		MainEventBus.INSTANCE.eventGraphicalAnalises(
				GraphicsOptions.CLASSIFICACAO_POR_BARRAS, collect,
				collects);
	}

	@FXML
	void onLinhaTemporalClicked(ActionEvent event) {

	}

	@FXML
	void onTempoParetoClicked(ActionEvent event) {

	}
	
	@FXML
	void onClassificacaoCicloBarrasClicked(ActionEvent event){
		MainEventBus.INSTANCE.eventGraphicalAnalises(
				GraphicsOptions.CLASSIFICACAO_CICLOS_POR_BARRAS, collect,
				collects);
	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {

	}
}
