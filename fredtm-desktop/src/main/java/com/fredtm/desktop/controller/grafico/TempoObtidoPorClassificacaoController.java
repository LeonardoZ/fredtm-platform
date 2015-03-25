package com.fredtm.desktop.controller.grafico;

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

public class TempoObtidoPorClassificacaoController extends BaseController {

	@FXML
	private BarChart<String, Number> graficoClassificacaoBarras;

	private Collect coleta;

	private List<Collect> coletas;

	public void setColetas(List<Collect> coletas) {
		this.coletas = coletas;
		configurarGraficoDeColetas();
	}

	public void setColeta(Collect coleta) {
		this.coleta = coleta;
		configurarGraficoSimples();
	}

	@SuppressWarnings("unchecked")
	private void configurarGraficoDeColetas() {
		AtomicInteger ai = new AtomicInteger(0);
		List<Series<String, Number>> seriesList = FXCollections.observableArrayList();
		
		coletas.forEach(coleta -> {

			XYChart.Data<String, Number> data1 = new XYChart.Data<String, Number>(
					"Improdutivo",
					coleta.getTotalPercentegeOfTimed(ActivityType.IMPRODUCTIVE));

			XYChart.Data<String, Number> data2 = new XYChart.Data<String, Number>(
					"Produtivo",
					coleta.getTotalPercentegeOfTimed(ActivityType.PRODUCTIVE));

			XYChart.Data<String, Number> data3 = new XYChart.Data<String, Number>(
					"Auxiliar",
					coleta.getTotalPercentegeOfTimed(ActivityType.AUXILIARY));
			
			
			Series<String, Number> series = new XYChart.Series<String, Number>();
			series.setName(ai.incrementAndGet()+"º Ciclo");
			series.getData().addAll(data1, data2, data3);
			seriesList.add(series);
		});
		graficoClassificacaoBarras.getYAxis().setAutoRanging(false);
		graficoClassificacaoBarras.getYAxis().setLabel("Tempo (%)");
		graficoClassificacaoBarras.getData().addAll(seriesList);
	}

	@SuppressWarnings("unchecked")
	private void configurarGraficoSimples() {

		XYChart.Data<String, Number> data1 = new XYChart.Data<String, Number>(
				"Improdutivo",
				coleta.getTotalPercentegeOfTimed(ActivityType.IMPRODUCTIVE));

		XYChart.Data<String, Number> data2 = new XYChart.Data<String, Number>(
				"Produtivo",
				coleta.getTotalPercentegeOfTimed(ActivityType.PRODUCTIVE));

		XYChart.Data<String, Number> data3 = new XYChart.Data<String, Number>(
				"Auxiliar",
				coleta.getTotalPercentegeOfTimed(ActivityType.AUXILIARY));
		configurarCor(data1, ActivityType.IMPRODUCTIVE);
		configurarCor(data2, ActivityType.PRODUCTIVE);
		configurarCor(data3, ActivityType.AUXILIARY);

		Series<String, Number> series = new XYChart.Series<String, Number>();
		series.getData().addAll(data1, data2, data3);

		graficoClassificacaoBarras.getYAxis().setAutoRanging(false);
		graficoClassificacaoBarras.getYAxis().setLabel("Tempo (%)");
		graficoClassificacaoBarras.getData().add(series);

	}

	public void configurarCor(XYChart.Data<String, Number> dado,
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
