package com.fredtm.desktop.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fredtm.core.model.Activity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class ChoiceList {

	static interface ChoiceCallback {
		void onActivitiesSelecteds(List<Activity> selecteds);
	}

	public ChoiceList(List<Activity> activities, ChoiceCallback callback) {

		ObservableList<Activity> choices = FXCollections.observableArrayList(activities);

		ListView<Activity> lv = new ListView<>(choices);
		lv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		Dialog<List<Activity>> cd = new Dialog<>();
		cd.setHeaderText(
				"Escolha atividades que representam os seu tempo ocioso, de descanso ou necessidades especiais.");
		cd.setContentText("Atividade ociosas");

		cd.getDialogPane().setContent(lv);
		cd.getDialogPane().getButtonTypes().add(ButtonType.OK);
		cd.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		cd.setResultConverter(bt -> {
			if (bt != ButtonType.OK)
				return new ArrayList<Activity>();
			
			return lv.getSelectionModel().getSelectedItems();
		});

		Optional<List<Activity>> someSelected = cd.showAndWait();
		if (someSelected.isPresent()) {
			callback.onActivitiesSelecteds(someSelected.get());
		}
	}

}
