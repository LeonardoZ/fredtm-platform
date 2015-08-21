package com.fredtm.desktop.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Operation;
import com.fredtm.desktop.controller.utils.FredCharts;
import com.fredtm.desktop.eventbus.MainEventBus;
import com.fredtm.desktop.views.CollectCustomCell;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class CollectsController extends BaseController implements Initializable {

	@FXML
	private ListView<Collect> listViewcollects;

	@FXML
	private MenuButton btnGraphicOption;

	private Collect collect;
	private List<Collect> collects;
	private Operation operation;

	public void setOperacao(Operation operation) {
		this.operation = operation;
		this.collects = new ArrayList<>(operation.getCollects());
		this.listViewcollects.setItems(FXCollections.observableArrayList(collects));

	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		MenuItem menuCollecteds = new MenuItem("Ver tempos coletados");

		MenuItem menuGrapgicsOptions = new MenuItem("Análise gráfica");

		MenuItem menuExportCollect = new MenuItem("Exportar coleta");
		MenuItem menuExportAllCollects = new MenuItem("Export todas coletas");

		menuCollecteds.setOnAction(ev -> MainEventBus.INSTANCE.eventOpenTimeActivity(collect));
		// TODO - Export todas collects
		menuExportCollect.setOnAction(ev -> MainEventBus.INSTANCE.eventExportCollects(Arrays.asList(collect)));
		menuExportAllCollects
				.setOnAction(ev -> MainEventBus.INSTANCE.eventExportCollects(new ArrayList<>(operation.getCollects())));

		ContextMenu contextMenu = new ContextMenu(menuCollecteds, menuExportCollect, menuExportAllCollects,
				menuGrapgicsOptions);

		contextMenu.centerOnScreen();
		contextMenu.setStyle("-fx-background-color: #fff");
		contextMenu.setAutoFix(true);
		listViewcollects.setContextMenu(contextMenu);

		listViewcollects.setOnMouseClicked(event -> {
			collect = this.listViewcollects.getSelectionModel().getSelectedItem();
		});

		listViewcollects.setCellFactory((ListView<Collect> ls) -> new CollectCustomCell());
	}

	@FXML
	void onExportAllPressed(ActionEvent event) {
		MainEventBus.INSTANCE.eventExportCollects(new ArrayList<>(operation.getCollects()));
	}

	@FXML
	void onGeneralAnalysesClicked(ActionEvent event) {
		
	}

	@FXML
	void onClassificationPerCollectClicked(ActionEvent event) {
		MainEventBus.INSTANCE.eventChartAnalyses(FredCharts.BARS_CLASSIFICATION, collects);
	}

}
