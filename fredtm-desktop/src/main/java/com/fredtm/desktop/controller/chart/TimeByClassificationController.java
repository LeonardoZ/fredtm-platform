package com.fredtm.desktop.controller.chart;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

import com.fredtm.core.model.ActivityType;
import com.fredtm.core.model.Collect;
import com.fredtm.desktop.controller.BaseController;

public class TimeByClassificationController extends BaseController {

	@FXML
	private BarChart<String, Number> barsClassificationChart;

	private Collect collect;

	private List<Collect> collects;

	public void setCollects(List<Collect> collects) {
		this.collects = collects;
		configureChartForCollects();
	}

	public void setCollect(Collect collect) {
		this.collect = collect;
		configureSimpleChart();
	}

	@SuppressWarnings("unchecked")
	private void configureChartForCollects() {
		AtomicInteger ai = new AtomicInteger(0);
		List<Series<String, Number>> seriesList = FXCollections.observableArrayList();
		
		collects.forEach(collect -> {

			XYChart.Data<String, Number> data1 = new XYChart.Data<String, Number>(
					"Improdutivo",
					collect.getTotalPercentageOfTimed(ActivityType.UNPRODUCTIVE));

			XYChart.Data<String, Number> data2 = new XYChart.Data<String, Number>(
					"Produtivo",
					collect.getTotalPercentageOfTimed(ActivityType.PRODUCTIVE));

			XYChart.Data<String, Number> data3 = new XYChart.Data<String, Number>(
					"Auxiliar",
					collect.getTotalPercentageOfTimed(ActivityType.AUXILIARY));
			
			
			Series<String, Number> series = new XYChart.Series<String, Number>();
			series.setName(ai.incrementAndGet()+"º Coleta (ciclo)");
			series.getData().addAll(data1, data2, data3);
			seriesList.add(series);
		});
		barsClassificationChart.getYAxis().setAutoRanging(false);
		barsClassificationChart.getYAxis().setLabel("Tempo (%)");
		barsClassificationChart.setTitle("Tempo/atividade por coleta (ciclo)");
		barsClassificationChart.getData().addAll(seriesList);
	}

	@SuppressWarnings("unchecked")
	private void configureSimpleChart() {

		XYChart.Data<String, Number> data1 = new XYChart.Data<String, Number>(
				"Improdutivo",
				collect.getTotalPercentageOfTimed(ActivityType.UNPRODUCTIVE));

		XYChart.Data<String, Number> data2 = new XYChart.Data<String, Number>(
				"Produtivo",
				collect.getTotalPercentageOfTimed(ActivityType.PRODUCTIVE));

		XYChart.Data<String, Number> data3 = new XYChart.Data<String, Number>(
				"Auxiliar",
				collect.getTotalPercentageOfTimed(ActivityType.AUXILIARY));
		configureColor(data1, ActivityType.UNPRODUCTIVE);
		configureColor(data2, ActivityType.PRODUCTIVE);
		configureColor(data3, ActivityType.AUXILIARY);

		Series<String, Number> series = new XYChart.Series<String, Number>();
		series.getData().addAll(data1, data2, data3);

		barsClassificationChart.getYAxis().setAutoRanging(false);
		barsClassificationChart.getYAxis().setLabel("Tempo (%)");
		barsClassificationChart.getData().add(series);
		barsClassificationChart.setLegendVisible(false);

	}

	public void configureColor(XYChart.Data<String, Number> dado,
			ActivityType tipo) {
		dado.nodeProperty().addListener(new ChangeListener<Node>() {
			@Override
			public void changed(ObservableValue<? extends Node> ov,
					Node oldNode, Node newNode) {
				if (newNode != null) {
					newNode.setStyle("-fx-bar-fill:" + tipo.getHexColor() + ";");
				}
			}
		});

	}

}
