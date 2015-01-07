package com.fredtm.desktop.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import com.fredtm.core.model.Atividade;
import com.fredtm.core.model.Coleta;

public class MainController implements Initializable {

	@FXML
	private ToggleButton btnSincronizar;

	@FXML
	private Button btnProjetos;

	@FXML
	private Button btnExportar;

	@FXML
	private Button btnSair;

	@FXML
	private TabPane tabPane;

	@FXML
	private VBox boxNenhumSync;

	@FXML
	private void onSincronizarClicked(ActionEvent event) {
		tabPane.getTabs().clear();
		Pane p = null;
		try {
			p = (Pane) new FXMLLoader().load(getClass().getResourceAsStream(
					"/fxml/projetos.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		criarTabs(p, "Projetos");
		btnProjetos.setDisable(false);
	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
	}

	public void abrirAtividades(List<Atividade> atividades) {
		AnchorPane p = null;
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
					"/fxml/atividades.fxml"));

			p = (AnchorPane) fxmlLoader.load();

			AtividadesController controller = fxmlLoader
					.<AtividadesController> getController();
			controller.setAtividades(atividades);
		} catch (IOException e) {
			e.printStackTrace();
		}

		criarTabs(p, "Atividades");
		btnProjetos.setDisable(false);
	}

	private void criarTabs(Pane p, String titulo) {
		Tab tab = new Tab(titulo);
		tab.setClosable(true);
		p.setStyle("-fx-background-color: #fff");
		tab.setContent(p);
		tabPane.getTabs().add(tab);
	}

	public void abrirColetas(List<Coleta> coletas) {

	}

	public void habilitarExportarAtividades(List<Atividade> atividades) {
		AnchorPane p = null;
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
					"/fxml/exportar_atividades.fxml"));

			p = (AnchorPane) fxmlLoader.load();

			AtividadesController controller = fxmlLoader
					.<AtividadesController> getController();
			controller.setAtividades(atividades);
		} catch (IOException e) {
			e.printStackTrace();
		}
		criarTabs(p, "Exportar atividades");
	}

}
