package com.fredtm.desktop.views;

import com.fredtm.core.decorator.TimeMeasure;
import com.fredtm.core.model.Collect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public class CollectInformationView {

	private Collect collect;

	public CollectInformationView(Collect collect) {
		this.collect = collect;
		init();
	}

	public void init() {
		Dialog<Collect> dialog = new Dialog<>();
		dialog.getDialogPane().getStylesheets().add("/styles/dialog.css");

		VBox lines = new VBox(0.5);
		HBox fstLine = new HBox();
		HBox scdLine = new HBox();

		Label lbTitle = new Label("Coleta: ");
		lbTitle.getStyleClass().add("fancy-label");

		Label lbTitleResponse = new Label(collect.toString());
		lbTitleResponse.getStyleClass().add("fancy-label-content");

		ObservableList<TimeMeasure> systems = FXCollections.observableArrayList(TimeMeasure.values());
		systems.remove(3);

		Label lbScale = new Label("Escala de tempo ");
		lbScale.getStyleClass().add("fancy-label");
		ChoiceBox<TimeMeasure> choices = new ChoiceBox<>(systems);

		fstLine.getChildren().addAll(lbTitle,lbTitleResponse);
		scdLine.getChildren().addAll(lbScale, choices);

		lines.getChildren().addAll(fstLine, scdLine);

		dialog.setTitle("Indicadores dos resultados da Coleta");
		dialog.getDialogPane().setContent(lines);
		dialog.getDialogPane().getStyleClass().addAll("fancy-dialog");
		dialog.initModality(Modality.NONE);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		dialog.show();
	}

	public void fancyLabel(Label... labels) {

	}

}
