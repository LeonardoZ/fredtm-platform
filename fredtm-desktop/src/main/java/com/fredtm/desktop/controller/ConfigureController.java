package com.fredtm.desktop.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

import javax.swing.JOptionPane;

import com.fredtm.desktop.sync.PropertiesConfig;

public class ConfigureController extends BaseController implements Initializable {

	@FXML
	private TextField textEditIp;

	@FXML
	private TextField textEditPort;

	@FXML
	private TextField textServerEditIp;

	@FXML
	private TextField textServerEditPort;

	private PropertiesConfig config;

	@FXML
	void onConfirmClicked(ActionEvent event) {
		Optional.of(config.save())
				.ifPresent((t) -> JOptionPane.showMessageDialog(null, "Alterações realizadas com sucesso!"));
	}

	@FXML
	void onServerConfirmClicked(ActionEvent event) {
		Optional.of(config.saveServer())
				.ifPresent((t) -> JOptionPane.showMessageDialog(null, "Alterações realizadas com sucesso!"));
	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		config = new PropertiesConfig();
		// socket
		textEditIp.setText(config.getIp().getValue());
		textEditIp.setAlignment(Pos.CENTER);
		textEditPort.setText(config.getPort().getValue());
		textEditPort.setAlignment(Pos.CENTER);

		config.getIp().bindBidirectional(textEditIp.textProperty());
		config.getPort().bindBidirectional(textEditPort.textProperty());

		// server
		textServerEditIp.setText(config.getServerIp().getValue());
		textServerEditIp.setAlignment(Pos.CENTER);
		textServerEditPort.setText(config.getServerPort().getValue());
		textServerEditPort.setAlignment(Pos.CENTER);

		config.getServerIp().bindBidirectional(textServerEditIp.textProperty());
		config.getServerPort().bindBidirectional(textServerEditPort.textProperty());

	}

}
