package com.fredtm.desktop.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Operation;
import com.fredtm.core.model.TimeActivity;
import com.fredtm.core.util.FredObjectMapper;
import com.fredtm.desktop.controller.utils.FredCharts;
import com.fredtm.desktop.eventbus.MainEventBus;
import com.fredtm.desktop.views.CollectCustomCell;
import com.fredtm.resources.GeneralCollectsBean;
import com.fredtm.resources.TimeActivityDTO;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import values.ActivityType;

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
		this.collects = new ArrayList<>(operation.getCollectsList());
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
				.setOnAction(ev -> MainEventBus.INSTANCE.eventExportCollects(new ArrayList<>(operation.getCollectsList())));

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
		MainEventBus.INSTANCE.eventExportCollects(new ArrayList<>(operation.getCollectsList()));
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
	void onGeneralSimplifiedReportClicked(ActionEvent event) {
		openGeneralReport(true);
	}

	@FXML
	void onGeneralReportClicked(ActionEvent event) {
		openGeneralReport(false);
	}

	private void openGeneralReport(boolean isSimple) {
		if (collects.isEmpty())
			return;

		Collect firstCollect = collects.get(0);
		Operation operation = firstCollect.getOperation();

		String technicalCharacteristics = operation.getTechnicalCharacteristics();
		String timeRange = operation.getTimeRange();
		String info = operation.toString();
		AtomicInteger ai = new AtomicInteger(0);

		List<TimeActivityDTO> resourcePro = new ArrayList<>();
		List<TimeActivityDTO> resourceAux = new ArrayList<>();
		List<TimeActivityDTO> resourceUnpro = new ArrayList<>();

		for (Collect co : collects) {
			int index = ai.incrementAndGet();
			List<TimeActivity> pros = co.getTimesByType(ActivityType.PRODUCTIVE);
			List<TimeActivity> auxs = co.getTimesByType(ActivityType.AUXILIARY);
			List<TimeActivity> unprods = co.getTimesByType(ActivityType.UNPRODUCTIVE);

			resourcePro.addAll(configureResources(pros, index));
			resourceAux.addAll(configureResources(auxs, index));
			resourceUnpro.addAll(configureResources(unprods, index));
		}

		List<TimeActivityDTO> ttt = new ArrayList<>();
		ttt.addAll(resourceUnpro);
		ttt.addAll(resourcePro);
		ttt.addAll(resourceAux);

		GeneralCollectsBean gcb = new GeneralCollectsBean();
		gcb.setAuxiliaryTimes(resourceAux);
		gcb.setProductiveTimes(resourcePro);
		gcb.setUnproductiveTimes(resourceUnpro);
		gcb.setTimes(ttt);

		ReportController reportController = new ReportController();
		reportController.fillDataSource(Arrays.asList(gcb)).fillParam("operation_info", info)
				.fillParam("period", timeRange).fillParam("tech_charac", technicalCharacteristics)
				.loadReport(isSimple ? "collect_general_simple.jasper" : "collect_general.jasper").buildAndShow();
		resourcePro = null;
		resourceAux = null;
		resourceUnpro = null;
		gcb = null;
	}

	@FXML
	void onTimeInCollectsClicked(ActionEvent event) {
		MainEventBus.INSTANCE.eventChartAnalyses(FredCharts.MULTIPLE_TIME_ANALYSYS, collects);
	}


    @FXML
    void onProductionReportClicked(ActionEvent event) {
    	if (collects.isEmpty())
			return;

		Collect firstCollect = collects.get(0);
		Operation operation = firstCollect.getOperation();

		String technicalCharacteristics = operation.getTechnicalCharacteristics();
		String timeRange = operation.getTimeRange();
		String info = operation.toString();
		AtomicInteger ai = new AtomicInteger(0);

		List<TimeActivityDTO> resourcePro = new ArrayList<>();

		for (Collect co : collects) {
			int index = ai.incrementAndGet();
			List<TimeActivity> pros = co.getTimesByType(ActivityType.PRODUCTIVE)
					.stream().filter(p -> p.getActivity().isQuantitative())
					.collect(Collectors.toList());
			if(pros.isEmpty()){
				JOptionPane.showMessageDialog(null, "Nenhuma atividade quantificável encontrada.");
				return;
			}
			resourcePro.addAll(configureResources(pros, index));
		}

		List<TimeActivityDTO> ttt = new ArrayList<>();
		ttt.addAll(resourcePro);

		GeneralCollectsBean gcb = new GeneralCollectsBean();
		gcb.setProductiveTimes(resourcePro);
		gcb.setTimes(ttt);

		ReportController reportController = new ReportController();
		reportController.fillDataSource(Arrays.asList(gcb)).fillParam("operation_info", info)
		.fillParam("itemName", ttt.stream().findFirst().get().getItemName())		
		.fillParam("period", timeRange).fillParam("tech_charac", technicalCharacteristics)
				.loadReport("productivity.jasper").buildAndShow();
		resourcePro = null;
		gcb = null;
    }
    

    @FXML
    void onRelativeProductionReportClicked(ActionEvent event) {
    	if (collects.isEmpty())
			return;

		Collect firstCollect = collects.get(0);
		Operation operation = firstCollect.getOperation();

		String technicalCharacteristics = operation.getTechnicalCharacteristics();
		String timeRange = operation.getTimeRange();
		String info = operation.toString();
		AtomicInteger ai = new AtomicInteger(0);

		List<TimeActivityDTO> resourcePro = new ArrayList<>();

		for (Collect co : collects) {
			int index = ai.incrementAndGet();
			List<TimeActivity> pros = co.getTimesByType(ActivityType.PRODUCTIVE)
					.stream().filter(p -> p.getActivity().isQuantitative())
					.collect(Collectors.toList());
			if(pros.isEmpty()){
				JOptionPane.showMessageDialog(null, "Nenhuma atividade quantificável encontrada.");
				return;
			}
			resourcePro.addAll(configureResources(pros, index));
		}

		List<TimeActivityDTO> ttt = new ArrayList<>();
		ttt.addAll(resourcePro);
		double sum = resourcePro.stream().mapToDouble(t -> t.getTimed() / 1000).sum();
		
		resourcePro.forEach(t-> t.setActivityTitle("Coleta "+t.getCollectIndex()+" - "+t.getActivityTitle()));
		
		ReportController reportController = new ReportController();
		reportController.fillDataSource(resourcePro).fillParam("operation_info", info)
			.fillParam("itemName", ttt.stream().findFirst().get().getItemName())		
			.fillParam("period", timeRange)
			.fillParam("tech_charac", technicalCharacteristics)
			.fillParam("total", sum)
				.loadReport("collected_amount_analytics.jasper")
				.buildAndShow();
		resourcePro = null;
    }
	
	public List<TimeActivityDTO> configureResources(List<TimeActivity> tas, Integer colIndex) {
		List<TimeActivityDTO> tars = FredObjectMapper.toResourcesFromTimeActivity(tas);
		tars.forEach(t -> t.setCollectIndex(colIndex.toString()));
		return tars;
	}

}
