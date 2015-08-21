package com.fredtm.desktop.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.fredtm.core.model.Activity;
import com.fredtm.core.model.Location;
import com.fredtm.core.model.TimeActivity;
import com.fredtm.desktop.views.TimeActivityCustomTableCell;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class CollectedTimesController extends BaseController implements Initializable {

	@FXML
	private TableView<TimeActivity> tbActivities;

	@FXML
	private TableColumn<TimeActivity, String> colTimeRange;

	@FXML
	private TableColumn<TimeActivity, Activity> colActivity;

	@FXML
	private TableColumn<TimeActivity, String> colTotal;

	@FXML
	private TableColumn<TimeActivity, String> colQuantified;

	@FXML
	private TableColumn<TimeActivity, Optional<Location>> colGeo;

	public void setTempos(List<TimeActivity> times) {
		this.tbActivities.getItems().addAll(times);
	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		colActivity.setCellValueFactory(new PropertyValueFactory<TimeActivity, Activity>("activity"));
		colTimeRange.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFullElapsedTime()));
		colGeo.setCellValueFactory(new PropertyValueFactory<TimeActivity, Optional<Location>>("location"));

		colTotal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSimpleEllapsedTime()));
		colQuantified
				.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFormattedCollectedAmount()));

		colGeo.setCellFactory(new Callback<TableColumn<TimeActivity, Optional<Location>>, TableCell<TimeActivity, Optional<Location>>>() {

			@Override
			public TableCell<TimeActivity, Optional<Location>> call(TableColumn<TimeActivity, Optional<Location>> param) {
				return new TimeActivityCustomTableCell();
			}
		});
		colActivity.setStyle("-fx-alignment: CENTER-LEFT;");
		colTimeRange.setStyle("-fx-alignment: CENTER-LEFT;");
		colTotal.setStyle("-fx-alignment: CENTER;");
		colQuantified.setStyle("-fx-alignment: CENTER;");
		colGeo.setStyle("-fx-alignment: CENTER;");

	}

}
