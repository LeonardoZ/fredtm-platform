package com.fredtm.desktop.controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;

import javax.swing.JOptionPane;

import com.fredtm.core.model.Operation;
import com.fredtm.desktop.eventbus.MainEventBus;
import com.fredtm.export.OperationsToJson;

public class ProjectsController extends BaseController implements Initializable {

	@FXML
	private ListView<Operation> listViewProjetos;

	private Operation operation;

	private List<Operation> operations;

	public void setOperacoes(List<Operation> operations) {
		this.operations = operations;
		listViewProjetos.getItems().addAll(operations);
	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		MenuItem menucollect = new MenuItem("Ver coletas");
		MenuItem menuAtividade = new MenuItem("Ver atividades");
		MenuItem menuExportar = new MenuItem("Exportar todas operações");
		menuAtividade.setOnAction(ev -> MainEventBus.INSTANCE
				.eventoAbrirAtividades(operation));
		menucollect.setOnAction(ev -> MainEventBus.INSTANCE
				.eventoAbrircollects(operation));
		menuExportar.setOnAction(ev -> exportarOperacoes());
		ContextMenu contextMenu = new ContextMenu(menucollect, menuAtividade,
				menuExportar);
		contextMenu.setStyle("-fx-background-color: #fff");
		contextMenu.setAutoFix(true);
		listViewProjetos.setContextMenu(contextMenu);

		listViewProjetos.setOnMouseClicked(event -> {
			operation = this.listViewProjetos.getSelectionModel()
					.getSelectedItem();
		});
	}

	private void exportarOperacoes() {
		DirectoryChooser dc = new DirectoryChooser();
		dc.setTitle("Escolha o diretório destino de exportação");
		File selectedDirectory = dc.showDialog(getWindow());
		if (selectedDirectory.isDirectory() && selectedDirectory.canWrite()) {
			OperationsToJson op = new OperationsToJson();
			op.export(operations, selectedDirectory.getAbsolutePath());
			JOptionPane.showMessageDialog(null, "Exportação completa!");
		} else {
			JOptionPane
					.showMessageDialog(null,
							"Erro ao exportar. Verifique se o destino é um diretório válido.");
		}
	}

}
