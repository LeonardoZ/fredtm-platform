package com.fredtm.desktop.controller;

import java.net.URL;
import java.util.ArrayList;
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

public class CollectsController extends BaseController implements Initializable {

	@FXML
	private ListView<Collect> listViewcollects;

	private Collect collect;

	private List<Collect> collects;

	private Operation operation;

	public void setOperacao(Operation operation) {
		this.operation = operation;
		this.collects = new ArrayList<>(operation.getCollects());
		listViewcollects.setItems(FXCollections.observableArrayList(collects));

		collects.forEach(c -> {
			System.out.println(c.toString());
		});

	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		MenuItem menucollectdos = new MenuItem("Ver tempos collectdos");

		MenuItem menuTiposDeGraficos = new MenuItem("Análises de collect");

		MenuItem menuExportarcollect = new MenuItem("Exportar collect");
		MenuItem menuExportarTodascollects = new MenuItem(
				"Exportar todas collects");

		menucollectdos.setOnAction(ev -> MainEventBus.INSTANCE
				.eventoAbrirTemposcollectdos(collect));
		// TODO - Exportar todas collects
		menuExportarcollect.setOnAction(ev -> MainEventBus.INSTANCE
				.eventoExportarcollects(Arrays.asList(collect)));
		menuExportarTodascollects.setOnAction(ev -> MainEventBus.INSTANCE
				.eventoExportarcollects(new ArrayList<>(operation.getCollects())));

		menuTiposDeGraficos.setOnAction(evt -> MainEventBus.INSTANCE
				.eventoTiposDeGraficos(collect, collects));

		ContextMenu contextMenu = new ContextMenu(menucollectdos,
				menuExportarcollect, menuExportarTodascollects,
				menuTiposDeGraficos);
		contextMenu.centerOnScreen();
		contextMenu.setStyle("-fx-background-color: #fff");
		contextMenu.setAutoFix(true);
		listViewcollects.setContextMenu(contextMenu);

		listViewcollects
				.setOnMouseClicked(event -> {
					collect = this.listViewcollects.getSelectionModel()
							.getSelectedItem();
				});

	}
}
