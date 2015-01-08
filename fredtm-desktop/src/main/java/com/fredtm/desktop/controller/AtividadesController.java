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

import com.fredtm.core.model.Atividade;
import com.fredtm.core.model.Operacao;
import com.fredtm.core.model.TipoAtividade;
import com.fredtm.desktop.eventbus.MainEventBus;

public class AtividadesController implements Initializable {

	@FXML
	private TableView<Atividade> tbAtividades;
	@FXML
	private TableColumn<Atividade, String> colTitulo;
	@FXML
	private TableColumn<Atividade, String> colDescricao;
	@FXML
	private TableColumn<Atividade, TipoAtividade> colTipo;
	@FXML
	private TableColumn<Atividade, String> colQuantitativa;
	private List<Atividade> atividades;
	private Operacao operacao;

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
		this.atividades = operacao.getAtividadesPreDefinidas();
		tbAtividades.setItems(FXCollections.observableArrayList(atividades));
	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		colTitulo
				.setCellValueFactory(new PropertyValueFactory<Atividade, String>(
						"titulo"));
		colDescricao
				.setCellValueFactory(new PropertyValueFactory<Atividade, String>(
						"descricao"));
		colTipo.setCellValueFactory(new PropertyValueFactory<Atividade, TipoAtividade>(
				"tipoAtividade"));
		
		colTipo.setCellFactory(collumn -> new TableCell<Atividade, TipoAtividade>() {
			@Override
			protected void updateItem(TipoAtividade tipo, boolean empty) {
				if (tipo != null && !empty) {
					setText(tipo.toString());
					setStyle("-fx-background-color: " + tipo.getHexColor());
				}
			}
		});
		colQuantitativa.setCellValueFactory(value -> new SimpleStringProperty(
				value.getValue() != null ? value.getValue().getNomeItem()
						: "Não quantitativa"));
		ContextMenu menu = new ContextMenu();
		
		MenuItem menuExportar = new MenuItem("Exportar");
		menuExportar.setOnAction(e -> MainEventBus.INSTANCE
				.eventoExportarAtividades(operacao));
		menu.getItems().addAll(menuExportar);
		
		tbAtividades.setContextMenu(menu);

	}

}
