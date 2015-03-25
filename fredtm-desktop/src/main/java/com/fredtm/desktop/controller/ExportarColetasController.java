package com.fredtm.desktop.controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import com.fredtm.core.model.Collect;
import com.fredtm.exportar.ErroDeExportacaoExcetion;
import com.fredtm.exportar.Exportacao;
import com.fredtm.exportar.ExportarColetaFactory;
import com.fredtm.exportar.Exportavel;

public class ExportarColetasController extends BaseController implements
		Initializable {

	@FXML
	private ListView<Collect> listViewColetas;
	@FXML
	private ChoiceBox<Exportacao> choiceTiposExportacao;
	@FXML
	private TextField tfDiretorio;

	private List<Collect> coletas;
	private File selectedDirectory;

	public void setColetas(List<Collect> coletas) {
		this.coletas = coletas;
		listViewColetas.getItems().addAll(coletas);
	}

	@FXML
	void onEscolherDiretorioClicked(ActionEvent event) {
		DirectoryChooser dc = new DirectoryChooser();
		dc.setTitle("Escolha o local para salvar sua exportação");
		selectedDirectory = dc.showDialog(getWindow());
		if (selectedDirectory != null && selectedDirectory.isDirectory() && selectedDirectory.canWrite()) {
			tfDiretorio.setText(selectedDirectory.getAbsolutePath());
		}
	}

	@FXML
	private void onExportarClicked() {
		if (selectedDirectory == null) {
			return;
		}
		Exportavel<Collect> exportador = ExportarColetaFactory
				.getExportador(choiceTiposExportacao.getValue());
		try {
			if (coletas.size() == 1) {
				exportador.exportar(coletas.get(0),
						selectedDirectory.getAbsolutePath());
			} else {
				exportador.exportar(coletas,
						selectedDirectory.getAbsolutePath());
			}
			JOptionPane.showMessageDialog(null, "Exportação completa!");
		} catch (ErroDeExportacaoExcetion e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		choiceTiposExportacao.getItems().addAll(Exportacao.toList());
		choiceTiposExportacao.autosize();
	}

}
