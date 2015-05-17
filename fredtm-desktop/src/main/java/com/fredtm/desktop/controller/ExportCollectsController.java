package com.fredtm.desktop.controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import javax.swing.JOptionPane;

import com.fredtm.core.model.Collect;
import com.fredtm.export.ExportCollectFactory;
import com.fredtm.export.Exportable;
import com.fredtm.export.Exportation;
import com.fredtm.export.ExportationErrorExcetion;

public class ExportCollectsController extends BaseController implements
		Initializable {

	@FXML
	private ListView<Collect> listViewcollects;
	@FXML
	private ChoiceBox<Exportation> choiceTiposExportacao;
	@FXML
	private TextField tfDiretorio;

	private List<Collect> collects;
	private File selectedDirectory;

	public void setcollects(List<Collect> collects) {
		this.collects = collects;
		listViewcollects.getItems().addAll(collects);
	}

	@FXML
	void onEscolherDiretorioClicked(ActionEvent event) {
		DirectoryChooser dc = new DirectoryChooser();
		dc.setTitle("Escolha o local para salvar sua exportação");
		selectedDirectory = dc.showDialog(getWindow());
		if (selectedDirectory != null && selectedDirectory.isDirectory()
				&& selectedDirectory.canWrite()) {
			tfDiretorio.setText(selectedDirectory.getAbsolutePath());
		}
	}

	@SuppressWarnings("restriction")
	@FXML
	private void onExportarClicked() {
		if (selectedDirectory == null) {
			return;
		}
		Exportable<Collect> exportador = ExportCollectFactory
				.getExporter(choiceTiposExportacao.getValue());
		try {
			if (collects.size() == 1) {
				exportador.export(collects.get(0),
						selectedDirectory.getAbsolutePath());
			} else {
				exportador.export(collects,
						selectedDirectory.getAbsolutePath());
			}
			JOptionPane.showMessageDialog(null, "Exportação completa!");
		} catch (ExportationErrorExcetion e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		choiceTiposExportacao.getItems().addAll(Exportation.toList());
		choiceTiposExportacao.autosize();
	}

}
