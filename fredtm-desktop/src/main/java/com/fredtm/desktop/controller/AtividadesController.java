package com.fredtm.desktop.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.fredtm.core.model.Activity;
import com.fredtm.core.model.ActivityType;
import com.fredtm.core.model.Operation;
import com.fredtm.desktop.eventbus.MainEventBus;

public class AtividadesController extends BaseController implements Initializable {

	@FXML
	private TableView<Activity> tbAtividades;
	@FXML
	private TableColumn<Activity, String> colTitulo;
	@FXML
	private TableColumn<Activity, String> colDescricao;
	@FXML
	private TableColumn<Activity, ActivityType> colTipo;
	@FXML
	private TableColumn<Activity, String> colQuantitativa;
	private List<Activity> atividades;
	private Operation operation;

	public void setOperacao(Operation operation) {
		this.operation = operation;
		this.atividades = operation.getActivitiesList();
		tbAtividades.setItems(FXCollections.observableArrayList(atividades));
	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		colTitulo
				.setCellValueFactory(new PropertyValueFactory<Activity, String>(
						"titulo"));
		colDescricao
				.setCellValueFactory(new PropertyValueFactory<Activity, String>(
						"descricao"));
		colTipo.setCellValueFactory(new PropertyValueFactory<Activity, ActivityType>(
				"tipoAtividade"));
		
		colTipo.setCellFactory(collumn -> new TableCell<Activity, ActivityType>() {
			@Override
			protected void updateItem(ActivityType tipo, boolean empty) {
				if (tipo != null && !empty) {
					setText(tipo.toString());
					setStyle("-fx-background-color: " + tipo.getHexColor());
				}
			}
		});
		colQuantitativa.setCellValueFactory(value -> new SimpleStringProperty(
				value.getValue() != null ? value.getValue().getItemName()
						: "Não quantitativa"));
		ContextMenu menu = new ContextMenu();
		
		MenuItem menuExportar = new MenuItem("Exportar");
		menuExportar.setOnAction(e -> MainEventBus.INSTANCE
				.eventoExportarAtividades(operation));
		menu.getItems().addAll(menuExportar);
		
		tbAtividades.setContextMenu(menu);

	}

}
