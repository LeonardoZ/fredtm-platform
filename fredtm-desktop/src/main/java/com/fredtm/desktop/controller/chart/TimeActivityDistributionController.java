package com.fredtm.desktop.controller.chart;

import java.util.Map;

import com.fredtm.core.model.Collect;
import com.fredtm.desktop.controller.BaseController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class TimeActivityDistributionController extends BaseController {

	@FXML
	private PieChart timesPizzaChart;
	private Collect collect;

	public void setCollect(Collect collect) {
		this.collect = collect;
		configuraGraficoPizza();
	}

	private void configuraGraficoPizza() {
		ObservableList<PieChart.Data> list = FXCollections
				.observableArrayList();
		Map<String, Long> sum = collect.getSumOfTimesByActivity();
		sum.forEach(
				(k, v) -> list.add(new PieChart.Data(k, Double.valueOf(v)))
		);
		timesPizzaChart.setData(list);
	}

}
