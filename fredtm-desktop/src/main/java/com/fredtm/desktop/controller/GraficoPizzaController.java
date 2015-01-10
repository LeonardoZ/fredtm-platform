package com.fredtm.desktop.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import com.fredtm.core.model.Coleta;
import com.fredtm.core.model.TempoAtividade;

public class GraficoPizzaController extends BaseController implements Initializable {

	@FXML
	private PieChart graficoPizzaTempos;
	private Coleta coleta;

	public void setColeta(Coleta coleta) {
		this.coleta = coleta;
	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		
		ObservableList<PieChart.Data> observableArrayList = FXCollections.observableArrayList();
		
		
	}

}
