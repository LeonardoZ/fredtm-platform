package com.fredtm.desktop.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.fredtm.core.model.Activity;
import com.fredtm.core.model.TimeActivity;
public class CollectedTimesController extends BaseController implements
		Initializable {

	@FXML
	private TableView<TimeActivity> tbAtividades;

	@FXML
	private TableColumn<TimeActivity, String> colInicial;

	@FXML
	private TableColumn<TimeActivity, Activity> colAtividade;

	@FXML
	private TableColumn<TimeActivity, String> colcollectdo;

	@FXML
	private TableColumn<TimeActivity, String> colFinal;

	@FXML
	private TableColumn<TimeActivity, String> colQuantificado;

	@SuppressWarnings("unused")
	private List<TimeActivity> tempos;

	public void setTempos(List<TimeActivity> tempos) {
		this.tempos = tempos;
		tbAtividades.getItems().addAll(tempos);
	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		colAtividade
				.setCellValueFactory(new PropertyValueFactory<TimeActivity, Activity>(
						"atividade"));
		colInicial.setCellValueFactory(dado -> new SimpleStringProperty(dado
				.getValue().getFormattedStartDate()));
		colFinal.setCellValueFactory(dado -> new SimpleStringProperty(dado
				.getValue().getFormattedEndDate()));
		colcollectdo.setCellValueFactory(dado -> new SimpleStringProperty(dado
				.getValue().getSimpleEllapsedTime()));
		colQuantificado.setCellValueFactory(dado -> new SimpleStringProperty(
				dado.getValue().getFormattedCollectedAmount()));
		// Cell change
		colAtividade
				.setCellValueFactory(new PropertyValueFactory<TimeActivity, Activity>(
						"atividade"));

	}

}
