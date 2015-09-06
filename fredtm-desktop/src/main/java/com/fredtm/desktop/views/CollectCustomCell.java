package com.fredtm.desktop.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import javax.swing.JOptionPane;

import com.fredtm.core.model.ActivityType;
import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Operation;
import com.fredtm.core.model.TimeActivity;
import com.fredtm.core.util.FormatElapsedTime;
import com.fredtm.core.util.FredObjectMapper;
import com.fredtm.desktop.controller.ReportController;
import com.fredtm.desktop.controller.utils.FredCharts;
import com.fredtm.desktop.eventbus.MainEventBus;
import com.fredtm.resources.GeneralCollectsBean;
import com.fredtm.resources.TimeActivityResource;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class CollectCustomCell extends ListCell<Collect> {

	private VBox mainContent, infoContent;
	private HBox structure, line1, line2;
	private Label lbTitleValue, lbTotalTime, lbIcon, lbTotal, lbUnproductive, lbProductive, lbAuxiliary;
	private Button btnCollectedTimes, btnExport;
	private MenuButton btnIndividualAnalysis, btnReports;
	private MenuItem btnTimeByActivity;
	private MenuItem btnClassification, btnSimpleClassification, btnTimes;
	private MenuItem btnCollectedsSimplesReport, btnCollectedsAnalyticReport, 
	btnAreaReport, btnGeneralReport,btnGeneralSimpleReport;

	public CollectCustomCell() {
		super();

		mainContent = new VBox(0.6f);

		lbTitleValue = new Label();
		lbTitleValue.setStyle("-fx-font-weight: bold;");
		lbTotalTime = new Label();

		btnCollectedTimes = new Button("Tempo/Atividade");
		btnCollectedTimes.getStyleClass().addAll("btn-fred-lists", "btn-fred-activities");

		btnExport = new Button("Exportar");
		btnExport.getStyleClass().addAll("btn-fred-lists", "btn-fred-collects");

		btnIndividualAnalysis = new MenuButton("Análises indivíduais");
		btnIndividualAnalysis.getStyleClass().addAll("btn-fred-lists", "btn-fred-collects");

		btnReports = new MenuButton("Relatórios");
		btnReports.getStyleClass().addAll("btn-fred-lists", "btn-fred-reports");

		applyCss(btnCollectedTimes, btnExport, btnIndividualAnalysis, btnReports);

		line1 = new HBox(lbTitleValue);
		line2 = new HBox(btnCollectedTimes, btnExport, btnIndividualAnalysis, btnReports);

		lbIcon = new Label("", new ImageView("/images/ic_action_alarm.png"));
		VBox.getVgrow(lbIcon);
		lbIcon.setAlignment(Pos.CENTER);
		lbIcon.setTextAlignment(TextAlignment.CENTER);
		VBox.getVgrow(lbIcon);

		mainContent.getChildren().addAll(line1, lbTotalTime, line2);
		Border border = new Border(new BorderStroke(javafx.scene.paint.Paint.valueOf("#bababa"),
				BorderStrokeStyle.DASHED, CornerRadii.EMPTY, new BorderWidths(0, 0, 1.0, 0)));

		lbProductive = new Label();
		lbAuxiliary = new Label();
		lbUnproductive = new Label();
		lbTotal = new Label();
		lbTotal.setStyle("-fx-font-weight: bold;");
		infoContent = new VBox(0.2, lbProductive, lbAuxiliary, lbUnproductive, lbTotal);

		Border borderInfo = new Border(new BorderStroke(javafx.scene.paint.Paint.valueOf("#bababa"),
				BorderStrokeStyle.DASHED, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 1.0)));
		infoContent.setBorder(borderInfo);
		infoContent.setPadding(new Insets(0, 0, 5, 20));

		mainContent.setPadding(new Insets(0, 10, 0, 20));

		structure = new HBox(lbIcon, mainContent, infoContent);
		structure.setPadding(new Insets(0.7, 0, 0.7, 0));
		structure.setAlignment(Pos.CENTER_LEFT);
		structure.setBorder(border);

		btnTimeByActivity = new MenuItem("Distribuição Tempo/Atividade");
		btnClassification = new MenuItem("Distribuição Tempo/Classificação");
		btnSimpleClassification = new MenuItem("Distribuição Tempo/Classificação simples");
		btnTimes = new MenuItem("Análise dos tempos");

		btnIndividualAnalysis.getItems().addAll(btnClassification, btnSimpleClassification, btnTimeByActivity,
				btnTimes);

		btnCollectedsSimplesReport = new MenuItem("Relatório de tempos simples");
		btnCollectedsAnalyticReport = new MenuItem("Relatório de tempos analítico");
		btnAreaReport = new MenuItem("Área da coleta");
		btnGeneralReport = new MenuItem("Relatório geral de coleta");
		btnGeneralSimpleReport= new MenuItem("Relatório geral simplificado de coleta ");
		btnReports.getItems().addAll(btnCollectedsSimplesReport, btnCollectedsAnalyticReport, btnAreaReport, btnGeneralReport,btnGeneralSimpleReport);

	}

	@Override
	protected void updateItem(Collect collect, boolean empty) {
		super.updateItem(collect, empty);
		if (empty) {
			clearContent();
		} else {
			addContent(collect);
		}
	}

	private void addContent(Collect co) {
		setText(null);

		lbTitleValue.setText(co.getOperation().toString());
		lbTotalTime.setText(co.getTimeRangeFormatted());

		long totalTimedSeconds = co.getTotalTimed();
		long totalAux = co.getTotalTimedByType(ActivityType.AUXILIARY);
		long totalProd = co.getTotalTimedByType(ActivityType.PRODUCTIVE);
		long totalUnprod = co.getTotalTimedByType(ActivityType.UNPRODUCTIVE);

		String fTotalAux = FormatElapsedTime.format(totalAux);
		String fTotalProd = FormatElapsedTime.format(totalProd);
		String fTotaUnprod = FormatElapsedTime.format(totalUnprod);
		String total = FormatElapsedTime.format(totalTimedSeconds);

		lbProductive.setText("Total produtivo: " + fTotalProd);
		lbUnproductive.setText("Total improdutivo: " + fTotaUnprod);
		lbAuxiliary.setText("Total auxiliar: " + fTotalAux);
		lbTotal.setText("Total geral: " + total);

		btnCollectedTimes.setOnMouseClicked(evt -> MainEventBus.INSTANCE.eventOpenTimeActivity(co));
		btnExport.setOnMousePressed(ev -> MainEventBus.INSTANCE.eventExportCollects(Arrays.asList(co)));

		btnTimeByActivity.setOnAction(
				evt -> MainEventBus.INSTANCE.eventChartAnalyses(FredCharts.TIME_ACTIVITY_DISTRIBUTION, co));
		btnClassification
				.setOnAction(evt -> MainEventBus.INSTANCE.eventChartAnalyses(FredCharts.TIME_BY_CLASSIFICATION, co));
		btnSimpleClassification.setOnAction(
				evt -> MainEventBus.INSTANCE.eventChartAnalyses(FredCharts.TIME_BY_SIMPLE_CLASSIFICATION, co));
		btnTimes.setOnAction(eevt -> MainEventBus.INSTANCE.eventChartAnalyses(FredCharts.TIME_ANALYSYS, co));

		setGraphic(structure);
		configureReportButtons(co);
	}

	private void configureReportButtons(Collect co) {
		List<TimeActivityResource> times = FredObjectMapper.toResourcesFromTimeActivity(co.getTimes());
		btnCollectedsSimplesReport.setOnAction(evt -> {
			Operation operation = co.getOperation();
			String technicalCharacteristics = operation.getTechnicalCharacteristics();
			String info = operation.toString();
			String timeRangeFormatted = co.getTimeRangeFormatted();

			ReportController reportController = new ReportController();
			reportController.fillDataSource(times).fillParam("operation_info", info)
					.fillParam("tech_charac", technicalCharacteristics).fillParam("period", timeRangeFormatted)
					.loadReport("collected_times.jasper").buildAndShow();
		});

		btnCollectedsAnalyticReport.setOnAction((ect) -> {
			Operation operation = co.getOperation();
			String technicalCharacteristics = operation.getTechnicalCharacteristics();
			String info = operation.toString();
			String timeRangeFormatted = co.getTimeRangeFormatted();
			double sum = times.stream().mapToDouble(t -> t.getTimed() / 1000).sum();

			ReportController reportController = new ReportController();
			reportController.fillDataSource(times).fillParam("total", sum).fillParam("operation_info", info)
					.fillParam("tech_charac", technicalCharacteristics).fillParam("period", timeRangeFormatted)
					.loadReport("collected_times_analytics.jasper").buildAndShow();
		});

		btnAreaReport.setOnAction(evt -> {
			Operation operation = co.getOperation();
			String technicalCharacteristics = operation.getTechnicalCharacteristics();
			String info = operation.toString();
			String timeRangeFormatted = co.getTimeRangeFormatted();
			double sum = times.stream().mapToDouble(t -> t.getTimed() / 1000).sum();
			Optional<TimeActivityResource> first = times.stream().filter(t -> !t.getLatitude().isEmpty()).findFirst();
			if (!first.isPresent()) {
				new JOptionPane("Coleta com nenhuma localização regisrada.");
			} else {
				ReportController reportController = new ReportController();
				reportController.fillDataSource(times).fillParam("total", sum).fillParam("operation_info", info)
						.fillParam("tech_charac", technicalCharacteristics).fillParam("period", timeRangeFormatted)
						.fillParam("baseLatitude", Float.valueOf(first.get().getLatitude()))
						.fillParam("baseLongitude", Float.valueOf(first.get().getLongitude()))
						.loadReport("collect_area.jasper").buildAndShow();
			}
		});
		
		btnGeneralReport.setOnAction(evt -> openGeneralReport(co, false));
		btnGeneralSimpleReport.setOnAction(evt -> openGeneralReport(co, true));
	}

	private void openGeneralReport(Collect co, boolean isSimple) {
		Operation operation = co.getOperation();
		String technicalCharacteristics = operation.getTechnicalCharacteristics();
		String info = operation.toString();
		AtomicInteger ai = new AtomicInteger(0);

		List<TimeActivityResource> resourcePro = new ArrayList<>();
		List<TimeActivityResource> resourceAux = new ArrayList<>();
		List<TimeActivityResource> resourceUnpro = new ArrayList<>();

		int index = ai.incrementAndGet();
		List<TimeActivity> pros = co.getTimesByType(ActivityType.PRODUCTIVE);
		List<TimeActivity> auxs = co.getTimesByType(ActivityType.AUXILIARY);
		List<TimeActivity> unprods = co.getTimesByType(ActivityType.UNPRODUCTIVE);

		resourcePro.addAll(configureResources(pros, index));
		resourceAux.addAll(configureResources(auxs, index));
		resourceUnpro.addAll(configureResources(unprods, index));

		List<TimeActivityResource> ttt = new ArrayList<>();
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
				.fillParam("tech_charac", technicalCharacteristics)
				.loadReport(isSimple ? "collect_general_simple.jasper" : "collect_general.jasper").buildAndShow();
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

	private void applyCss(Node... btns) {
		Stream.of(btns).forEach(b -> {
			HBox.setMargin(b, new Insets(5, 10, 5, 0));
			HBox.setHgrow(b, Priority.ALWAYS);
		});
	}

	private void clearContent() {
		setText(null);
		setGraphic(null);
	}

}
