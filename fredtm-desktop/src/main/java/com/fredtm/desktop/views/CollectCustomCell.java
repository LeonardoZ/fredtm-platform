package com.fredtm.desktop.views;

import java.util.Arrays;
import java.util.stream.Stream;

import com.fredtm.core.model.ActivityType;
import com.fredtm.core.model.Collect;
import com.fredtm.core.util.FormatElapsedTime;
import com.fredtm.desktop.controller.utils.FredCharts;
import com.fredtm.desktop.eventbus.MainEventBus;

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
	private Button btCollectedTimes, btExport;
	private MenuButton btIndividualAnalysis;
	private MenuItem btnChartDistributionPizza;
	private MenuItem btnChartClassificationBars;

	public CollectCustomCell() {
		super();

		mainContent = new VBox(0.6f);

		lbTitleValue = new Label();
		lbTitleValue.setStyle("-fx-font-weight: bold;");
		lbTotalTime = new Label();

		btCollectedTimes = new Button("Tempo/Atividade");
		btCollectedTimes.getStyleClass().addAll("btn-fred-lists", "btn-fred-activities");

		btExport = new Button("Exportar");
		btExport.getStyleClass().addAll("btn-fred-lists", "btn-fred-collects");
		
		btIndividualAnalysis = new MenuButton("Análises indivíduais");
		btIndividualAnalysis.getStyleClass().addAll("btn-fred-lists", "btn-fred-collects");
		
		applyCss(btCollectedTimes, btExport, btIndividualAnalysis);

		line1 = new HBox(lbTitleValue);
		line2 = new HBox(btCollectedTimes, btExport, btIndividualAnalysis);

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

		btnChartDistributionPizza = new MenuItem("Distribuição Tempo/Atividade");
		btnChartClassificationBars = new MenuItem("Distribuição Tempo/Classificação");
		btIndividualAnalysis.getItems().addAll(btnChartClassificationBars, btnChartDistributionPizza);
		
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

		btCollectedTimes.setOnMouseClicked(evt -> MainEventBus.INSTANCE.eventOpenTimeActivity(co));
		btExport.setOnMousePressed(ev -> MainEventBus.INSTANCE.eventExportCollects(Arrays.asList(co)));
		
		btnChartDistributionPizza.setOnAction(
				evt -> MainEventBus.INSTANCE.eventChartAnalyses(FredCharts.TIME_ACTIVITY_DISTRIBUTION, co));
		btnChartClassificationBars.setOnAction(
				evt -> MainEventBus.INSTANCE.eventChartAnalyses(FredCharts.BARS_CLASSIFICATION, co));
		
		setGraphic(structure);
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
