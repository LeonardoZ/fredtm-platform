package com.fredtm.desktop.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.fredtm.core.model.Activity;
import com.fredtm.core.model.TimeActivity;
public class CollectedTimesController extends BaseController implements
		Initializable {

	@FXML
	private TableView<TimeActivity> tbActivities;

	@FXML
	private TableColumn<TimeActivity, String> colInitial;

	@FXML
	private TableColumn<TimeActivity, Activity> colActivity;

	@FXML
	private TableColumn<TimeActivity, String> colCollected;

	@FXML
	private TableColumn<TimeActivity, String> colFinal;

	@FXML
	private TableColumn<TimeActivity, String> colQuantified;

	@SuppressWarnings("unused")
	private List<TimeActivity> times;

	public void setTempos(List<TimeActivity> times) {
		this.times = times;
		tbActivities.getItems().addAll(times);
	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		colActivity
				.setCellValueFactory(new PropertyValueFactory<TimeActivity, Activity>(
						"activity"));
		colInitial.setCellValueFactory(data -> new SimpleStringProperty(data
				.getValue().getFormattedStartDate()));
		colFinal.setCellValueFactory(data -> new SimpleStringProperty(data
				.getValue().getFormattedFinalDate()));
		colCollected.setCellValueFactory(data -> new SimpleStringProperty(data
				.getValue().getSimpleEllapsedTime()));
		colQuantified.setCellValueFactory(data -> new SimpleStringProperty(
				data.getValue().getFormattedCollectedAmount()));
		// Cell change
		colActivity
				.setCellValueFactory(new PropertyValueFactory<TimeActivity, Activity>(
						"activity"));

	}

}
