package com.fredtm.desk.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import com.fredtm.core.model.Operacao;

public class ProjetosController implements Initializable {

	@FXML
	private ListView<Operacao> listViewProjetos;

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		
		for (int i = 0; i < 20; i++) {
			Operacao operacao = new Operacao("Teste "+i, "Teste empresa "+i, "Primeira operação "+i);
			listViewProjetos.getItems().add(operacao);
		}
			
	}

}
