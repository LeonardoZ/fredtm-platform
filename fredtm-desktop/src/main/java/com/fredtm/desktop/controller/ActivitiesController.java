package com.fredtm.desktop.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.fredtm.core.model.Activity;
import com.fredtm.core.model.Operation;
import com.fredtm.core.util.FredObjectMapper;
import com.fredtm.resources.ActivityDTO;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import values.ActivityType;

public class ActivitiesController extends BaseController implements Initializable {

	@FXML
	private TableView<Activity> tbActivities;

	@FXML
	private TableColumn<Activity, String> colTitle;

	@FXML
	private TableColumn<Activity, String> colDescription;

	@FXML
	private TableColumn<Activity, ActivityType> colType;

	@FXML
	private TableColumn<Activity, String> colQuantitative;

	private List<Activity> activities;

	private Operation operation;

	public void setOperacao(Operation operation) {
		this.operation = operation;
		this.activities = operation.getActivitiesList();
		this.tbActivities.setItems(FXCollections.observableArrayList(activities));
	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		colTitle.setCellValueFactory(new PropertyValueFactory<Activity, String>("title"));
		colDescription.setCellValueFactory(new PropertyValueFactory<Activity, String>("description"));
		colType.setCellValueFactory(new PropertyValueFactory<Activity, ActivityType>("activityType"));

		colType.setCellFactory(new Callback<TableColumn<Activity, ActivityType>, TableCell<Activity, ActivityType>>() {

			@Override
			public TableCell<Activity, ActivityType> call(TableColumn<Activity, ActivityType> param) {
				return new TableCell<Activity, ActivityType>() {
					@Override
					protected void updateItem(ActivityType type, boolean empty) {
						super.updateItem(type, empty);
						if (type != null && !empty) {
							setText(type.getValue());
							setStyle("-fx-background-color: " + type.getHexColor()
									+ "; -fx-font-size: 10px; -fx-font-weight: bold;" + "; -fx-alignment: center;");
						}
					}
				};
			}
		});

		colQuantitative.setCellValueFactory(value -> new SimpleStringProperty(
				value.getValue() != null ? value.getValue().getItemName() : "Não quantitativa"));
		ContextMenu menu = new ContextMenu();
		MenuItem menuExport = new MenuItem("Exportar");
		menu.getItems().addAll(menuExport);

		tbActivities.setContextMenu(menu);

	}

	@FXML
	void onReportClicked(ActionEvent event) {
		String technicalCharacteristics = operation.getTechnicalCharacteristics();
		String info = operation.toString();
		Set<ActivityDTO> acts = FredObjectMapper.toResourcesActivities(activities);
		
		ReportController reportController = new ReportController();
		reportController.fillDataSource(acts)
				.fillParam("operation_info", info)
				.fillParam("tech_charac", technicalCharacteristics)
				.loadReport("operation_activities.jasper")
				.buildAndShow();
	}

}
