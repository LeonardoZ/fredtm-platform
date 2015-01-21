package com.fredtm.desktop.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.fredtm.core.model.Atividade;
import com.fredtm.core.model.TempoAtividade;

public class TemposColetadosController extends BaseController implements
		Initializable {

	@FXML
	private TableView<TempoAtividade> tbAtividades;

	@FXML
	private TableColumn<TempoAtividade, String> colInicial;

	@FXML
	private TableColumn<TempoAtividade, Atividade> colAtividade;

	@FXML
	private TableColumn<TempoAtividade, String> colColetado;

	@FXML
	private TableColumn<TempoAtividade, String> colFinal;

	@FXML
	private TableColumn<TempoAtividade, String> colQuantificado;

	@SuppressWarnings("unused")
	private List<TempoAtividade> tempos;

	public void setTempos(List<TempoAtividade> tempos) {
		this.tempos = tempos;
		tbAtividades.getItems().addAll(tempos);
	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		colAtividade
				.setCellValueFactory(new PropertyValueFactory<TempoAtividade, Atividade>(
						"atividade"));
		colInicial.setCellValueFactory(dado -> new SimpleStringProperty(dado
				.getValue().getDataInicioFormatada()));
		colFinal.setCellValueFactory(dado -> new SimpleStringProperty(dado
				.getValue().getDataFimFormatada()));
		colColetado.setCellValueFactory(dado -> new SimpleStringProperty(dado
				.getValue().getTempoDecorridoFormatadoSimples()));
		colQuantificado.setCellValueFactory(dado -> new SimpleStringProperty(
				dado.getValue().getQuantidadeColetadaFormatada()));
		// Cell change
		colAtividade
				.setCellValueFactory(new PropertyValueFactory<TempoAtividade, Atividade>(
						"atividade"));

	}

}
