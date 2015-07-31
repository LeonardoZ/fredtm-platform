package com.fredtm.desktop.controller.graphic;

import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import com.fredtm.core.model.Collect;
import com.fredtm.desktop.controller.BaseController;

public class DistribuicaoTempoAtividadeController extends BaseController {

	@FXML
	private PieChart graficoPizzaTempos;
	private Collect collect;

	public void setcollect(Collect collect) {
		this.collect = collect;
		configuraGraficoPizza();
	}

	private void configuraGraficoPizza() {
		ObservableList<PieChart.Data> list = FXCollections
				.observableArrayList();
		HashMap<String, Long> somaDosTempos = collect.getSumOfTimes();
		somaDosTempos.forEach(
				(k, v) -> list.add(new PieChart.Data(k, Double.valueOf(v)))
		);
		graficoPizzaTempos.setData(list);
	}

}
