package com.fredtm.desktop.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

import com.fredtm.core.model.Operacao;
import com.fredtm.desktop.eventbus.MainEventBus;

public class ProjetosController extends BaseController implements Initializable {

	@FXML
	private ListView<Operacao> listViewProjetos;

	private Operacao operacao;
	
	
	public void setOperacoes(List<Operacao> operacoes) {
		listViewProjetos.getItems().addAll(operacoes);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		MenuItem menuColeta = new MenuItem("Ver coletas");
		MenuItem menuAtividade = new MenuItem("Ver atividades");
		menuAtividade.setOnAction(ev -> 
			MainEventBus.INSTANCE.eventoAbrirAtividades(operacao)
		);
		menuColeta.setOnAction(ev -> 
			MainEventBus.INSTANCE.eventoAbrirColetas(operacao)
		);
		
		ContextMenu contextMenu = new ContextMenu(menuColeta, menuAtividade);
		contextMenu.setStyle("-fx-background-color: #fff");
		contextMenu.setAutoFix(true);
		listViewProjetos.setContextMenu(contextMenu);

		listViewProjetos.setOnMouseClicked(event -> {
			operacao = this.listViewProjetos.getSelectionModel()
					.getSelectedItem();
		});
	}


}
