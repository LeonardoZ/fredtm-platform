package com.fredtm.desktop.views;

import java.util.List;
import java.util.Optional;

import com.fredtm.core.decorator.TimeMeasure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class TimeChoiceList {

	public interface TimeChoiceCallback {
		void onMeasureSelected(TimeMeasure selected);
	}

	public TimeChoiceList(List<TimeMeasure> timeMeasures, TimeChoiceCallback callback) {

		ObservableList<TimeMeasure> choices = FXCollections.observableArrayList(timeMeasures);

		ListView<TimeMeasure> lv = new ListView<>(choices);
		lv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		Dialog<TimeMeasure> cd = new Dialog<>();
		cd.setTitle("Medida de Tempo");
		cd.setHeaderText(
				"Escolha à medida de tempo desejada.");
		cd.setContentText("Atividade ociosas");
		cd.getDialogPane().setContent(lv);
		cd.getDialogPane().getButtonTypes().add(ButtonType.OK);
		cd.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		cd.setResultConverter(bt -> {
			if (bt != ButtonType.OK)
				return null;
			
			return lv.getSelectionModel().getSelectedItem();
		});

		Optional<TimeMeasure> someSelected = cd.showAndWait();
		if (someSelected.isPresent()) {
			callback.onMeasureSelected(someSelected.get());
		}
	}

}
