package com.fredtm.desktop.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

import com.fredtm.core.model.Coleta;
import com.fredtm.core.model.Operacao;
import com.fredtm.desktop.eventbus.MainEventBus;

public class ColetasController implements Initializable {

	@FXML
	private ListView<Coleta> listViewColetas;

	private Coleta coleta;

	private List<Coleta> coletas;
	
	private Operacao operacao;
	
	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
		this.coletas = operacao.getColetas();
		listViewColetas.setItems(FXCollections.observableArrayList(coletas));
	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		MenuItem menuColetados = new MenuItem("Ver tempos coletados");
		MenuItem menuExportarColetados = new MenuItem("Exportar coleta");
		MenuItem menuExportarTodasColetas = new MenuItem("Exportar todasColeta");
		
		menuColetados.setOnAction(ev -> MainEventBus.INSTANCE
				.eventoAbrirTemposColetados(coleta));
		// TODO - Exportar todas coletas
		menuExportarColetados.setOnAction(ev -> MainEventBus.INSTANCE
				.eventoAbrirTemposColetados(coleta));
		menuExportarTodasColetas.setOnAction(ev -> MainEventBus.INSTANCE
				.eventoAbrirTemposColetados(coleta));

		ContextMenu contextMenu = new ContextMenu(menuColetados);
		contextMenu.setStyle("-fx-background-color: #fff");
		contextMenu.setAutoFix(true);
		listViewColetas.setContextMenu(contextMenu);

		listViewColetas
				.setOnMouseClicked(event -> {
					coleta = this.listViewColetas.getSelectionModel()
							.getSelectedItem();

				});

	}
}
