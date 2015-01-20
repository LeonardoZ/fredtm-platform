package com.fredtm.desktop.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

import javax.swing.JOptionPane;

import com.fredtm.desktop.sync.SocketConfig;

public class ConfigurarController extends BaseController implements
		Initializable {

	@FXML
	private TextField textEditIp;

	@FXML
	private TextField textEditPorta;

	private SocketConfig config;

	@FXML
	void onConfirmarClicked(ActionEvent event) {
		boolean salvo = config.salvar();
		if (salvo) {
			JOptionPane.showMessageDialog(null,
					"Alterações realizadas com sucesso!");
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		config = new SocketConfig();
		textEditIp.setText(config.getIp().getValue());
		textEditIp.setAlignment(Pos.CENTER);
		textEditPorta.setText(config.getPort().getValue());
		textEditPorta.setAlignment(Pos.CENTER);
		config.getIp().bindBidirectional(textEditIp.textProperty());
		config.getPort().bindBidirectional(textEditPorta.textProperty());

	}

}
