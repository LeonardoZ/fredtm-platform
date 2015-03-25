package com.fredtm.desktop.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Operation;
import com.fredtm.desktop.eventbus.MainEventBus;

public class ColetasController extends BaseController implements Initializable {

	@FXML
	private ListView<Collect> listViewColetas;

	private Collect coleta;

	private List<Collect> coletas;

	private Operation operation;

	public void setOperacao(Operation operation) {
		this.operation = operation;
		this.coletas = operation.getCollects();
		listViewColetas.setItems(FXCollections.observableArrayList(coletas));

		coletas.forEach(c -> {
			System.out.println(c.toString());
		});

	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		MenuItem menuColetados = new MenuItem("Ver tempos coletados");

		MenuItem menuTiposDeGraficos = new MenuItem("Análises de coleta");

		MenuItem menuExportarColeta = new MenuItem("Exportar coleta");
		MenuItem menuExportarTodasColetas = new MenuItem(
				"Exportar todas coletas");

		menuColetados.setOnAction(ev -> MainEventBus.INSTANCE
				.eventoAbrirTemposColetados(coleta));
		// TODO - Exportar todas coletas
		menuExportarColeta.setOnAction(ev -> MainEventBus.INSTANCE
				.eventoExportarColetas(Arrays.asList(coleta)));
		menuExportarTodasColetas.setOnAction(ev -> MainEventBus.INSTANCE
				.eventoExportarColetas(operation.getCollects()));

		menuTiposDeGraficos.setOnAction(evt -> MainEventBus.INSTANCE
				.eventoTiposDeGraficos(coleta, coletas));

		ContextMenu contextMenu = new ContextMenu(menuColetados,
				menuExportarColeta, menuExportarTodasColetas,
				menuTiposDeGraficos);
		contextMenu.centerOnScreen();
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
