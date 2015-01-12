package com.fredtm.desktop.controller;

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

import com.fredtm.core.model.Coleta;
import com.fredtm.core.model.TipoAtividade;

public class TempoObtidoPorClassificacaoController extends BaseController {

	@FXML
	private BarChart<String, Number> graficoClassificacaoBarras;

	private Coleta coleta;

	private List<Coleta> coletas;

	public void setColetas(List<Coleta> coletas) {
		this.coletas = coletas;
		configurarGraficoDeColetas();
	}

	public void setColeta(Coleta coleta) {
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
					coleta.getPercentualTotaisCronometradosPor(TipoAtividade.IMPRODUTIVA));

			XYChart.Data<String, Number> data2 = new XYChart.Data<String, Number>(
					"Produtivo",
					coleta.getPercentualTotaisCronometradosPor(TipoAtividade.PRODUTIVA));

			XYChart.Data<String, Number> data3 = new XYChart.Data<String, Number>(
					"Auxiliar",
					coleta.getPercentualTotaisCronometradosPor(TipoAtividade.AUXILIAR));
			
			
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
				coleta.getPercentualTotaisCronometradosPor(TipoAtividade.IMPRODUTIVA));

		XYChart.Data<String, Number> data2 = new XYChart.Data<String, Number>(
				"Produtivo",
				coleta.getPercentualTotaisCronometradosPor(TipoAtividade.PRODUTIVA));

		XYChart.Data<String, Number> data3 = new XYChart.Data<String, Number>(
				"Auxiliar",
				coleta.getPercentualTotaisCronometradosPor(TipoAtividade.AUXILIAR));
		configurarCor(data1, TipoAtividade.IMPRODUTIVA);
		configurarCor(data2, TipoAtividade.PRODUTIVA);
		configurarCor(data3, TipoAtividade.AUXILIAR);

		Series<String, Number> series = new XYChart.Series<String, Number>();
		series.getData().addAll(data1, data2, data3);

		graficoClassificacaoBarras.getYAxis().setAutoRanging(false);
		graficoClassificacaoBarras.getYAxis().setLabel("Tempo (%)");
		graficoClassificacaoBarras.getData().add(series);

	}

	public void configurarCor(XYChart.Data<String, Number> dado,
			TipoAtividade tipo) {
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
