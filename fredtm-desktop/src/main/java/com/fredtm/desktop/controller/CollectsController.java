package com.fredtm.desktop.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import com.fredtm.core.model.ActivityType;
import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Operation;
import com.fredtm.core.model.TimeActivity;
import com.fredtm.core.util.FredObjectMapper;
import com.fredtm.desktop.controller.utils.FredCharts;
import com.fredtm.desktop.eventbus.MainEventBus;
import com.fredtm.desktop.views.CollectCustomCell;
import com.fredtm.resources.GeneralCollectsBean;
import com.fredtm.resources.TimeActivityResource;

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

		MenuItem menuGraphicsOptions = new MenuItem("Análise gráfica");

		MenuItem menuExportCollect = new MenuItem("Exportar coleta");
		MenuItem menuExportAllCollects = new MenuItem("Export todas coletas");

		menuCollecteds.setOnAction(ev -> MainEventBus.INSTANCE.eventOpenTimeActivity(collect));
		// TODO - Export todas collects
		menuExportCollect.setOnAction(ev -> MainEventBus.INSTANCE.eventExportCollects(Arrays.asList(collect)));
		menuExportAllCollects
				.setOnAction(ev -> MainEventBus.INSTANCE.eventExportCollects(new ArrayList<>(operation.getCollects())));

		ContextMenu contextMenu = new ContextMenu(menuCollecteds, menuExportCollect, menuExportAllCollects,
				menuGraphicsOptions);

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
		MainEventBus.INSTANCE.eventChartAnalyses(FredCharts.TIME_BY_CLASSIFICATION, collects);
	}

	@FXML
	void onSimpleClassificationPerCollectClicked(ActionEvent event) {
		MainEventBus.INSTANCE.eventChartAnalyses(FredCharts.TIME_BY_SIMPLE_CLASSIFICATION, collects);
	}

	@FXML
	void onGeneralReportClicked(ActionEvent event) {
		Operation operation = collects.get(0).getOperation();
		String technicalCharacteristics = operation.getTechnicalCharacteristics();
		String info = operation.toString();
		AtomicInteger ai = new AtomicInteger(0);

		List<TimeActivityResource> resourcePro = new ArrayList<>();
		List<TimeActivityResource> resourceAux = new ArrayList<>();
		List<TimeActivityResource> resourceUnpro = new ArrayList<>();

		for (Collect co : collects) {
			int index = ai.incrementAndGet();
			List<TimeActivity> pros = co.getTimesByType(ActivityType.PRODUCTIVE);
			List<TimeActivity> auxs = co.getTimesByType(ActivityType.AUXILIARY);
			List<TimeActivity> unprods = co.getTimesByType(ActivityType.UNPRODUCTIVE);

			resourcePro.addAll(configureResources(pros, index));
			resourceAux.addAll(configureResources(auxs, index));
			resourceUnpro.addAll(configureResources(unprods, index));
		}

		GeneralCollectsBean gcb = new GeneralCollectsBean();
		gcb.setAuxiliaryTimes(resourceAux);
		gcb.setProductiveTimes(resourcePro);
		gcb.setUnproductiveTimes(resourceUnpro);

		ReportController reportController = new ReportController();
		reportController.fillDataSource(Arrays.asList(gcb)).fillParam("operation_info", info)
				.fillParam("tech_charac", technicalCharacteristics).loadReport("collect_general.jasper").buildAndShow();
		resourcePro = null;
		resourceAux = null;
		resourceUnpro = null;
		gcb = null;
	}

	public List<TimeActivityResource> configureResources(List<TimeActivity> tas, Integer colIndex) {
		List<TimeActivityResource> tars = FredObjectMapper.toResourcesFromTimeActivity(tas);
		tars.forEach(t -> t.setCollectIndex(colIndex.toString()));
		return tars;
	}

}
