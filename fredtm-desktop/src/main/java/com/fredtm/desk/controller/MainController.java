package com.fredtm.desk.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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

		tabPane.getTabs().remove(0);

		System.out.println("Clicked!");

		Pane p = null;
		try {
			p = (Pane) new FXMLLoader().load(getClass().getResourceAsStream(
					"/fxml/projetos.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Tab tab = new Tab("Projetos");
		tab.setStyle("-fx-background-color: #fff");
		tab.setContent(p);
		tabPane.getTabs().add(tab);
	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {

	}

}
