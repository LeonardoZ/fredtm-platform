package com.fredtm.desktop.views;

import java.util.stream.Stream;

import com.fredtm.core.model.ActivityType;
import com.fredtm.core.model.Collect;
import com.fredtm.core.util.FormatElapsedTime;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
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
	private Button btCollectedTimes, btExport, btExportAll;

	public CollectCustomCell() {
		super();

		mainContent = new VBox(0.6f);

		lbTitleValue = new Label();
		lbTitleValue.setStyle("-fx-font-weigth: bold;");
		lbTotalTime = new Label();

		btCollectedTimes = new Button("Atividades");
		btCollectedTimes.getStyleClass().addAll("btn-fred-lists", "btn-fred-activities");

		btExport = new Button("Export");
		btExport.getStyleClass().addAll("btn-fred-lists", "btn-fred-collects");

		btExportAll = new Button("ExportAll");
		btExportAll.getStyleClass().addAll("btn-fred-lists", "btn-fred-collects");

		applyCss(btCollectedTimes, btExport, btExportAll);

		line1 = new HBox(lbTitleValue);
		line2 = new HBox(btCollectedTimes, btExport, btExportAll);

		lbIcon = new Label("");
		lbIcon.setAlignment(Pos.CENTER);
		lbIcon.setTextAlignment(TextAlignment.CENTER);
		VBox.getVgrow(lbIcon);

		mainContent.getChildren().addAll(line1, lbTotalTime, line2);
		Border border = new Border(new BorderStroke(javafx.scene.paint.Paint.valueOf("#323638"),
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 1.0, 0)));
		
		lbProductive = new Label();
		lbAuxiliary= new Label();
		lbUnproductive= new Label();
		lbTotal= new Label();
		infoContent = new VBox(0.4, lbProductive, lbAuxiliary, lbUnproductive, lbTotal);

		structure = new HBox(lbIcon, mainContent, infoContent);
		structure.setPadding(new Insets(0.7, 0, 0.7, 0));
		structure.setAlignment(Pos.CENTER_LEFT);
		structure.setBorder(border);

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

		co.organizeTimeActivity();
		lbTitleValue.setText(co.getOperation().toString());
		lbTotalTime.setText(co.getTimeRangeFormatted());
		
		long totalTimedSeconds = co.getTotalTimedSeconds();
		
		long totalAux = co.getTotalTimedSecondsByType(ActivityType.AUXILIARY);
		long totalProd = co.getTotalTimedSecondsByType(ActivityType.PRODUCTIVE);
		long totalUnprod = co.getTotalTimedSecondsByType(ActivityType.IMPRODUCTIVE);
		
		String fTotalAux = FormatElapsedTime.format(totalAux);
		String fTotalProd= FormatElapsedTime.format(totalProd);
		String fTotaUnprod = FormatElapsedTime.format(totalUnprod);
		String total = FormatElapsedTime.format(totalTimedSeconds);
		
		lbProductive.setText(fTotalProd);
		lbUnproductive.setText(fTotaUnprod);
		lbAuxiliary.setText(fTotalAux);
		lbTotal.setText(total);
		
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
