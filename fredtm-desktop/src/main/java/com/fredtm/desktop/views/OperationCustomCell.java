package com.fredtm.desktop.views;

import java.util.stream.Stream;

import com.fredtm.core.model.Operation;
import com.fredtm.desktop.eventbus.MainEventBus;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
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

public class OperationCustomCell extends ListCell<Operation> {

	private VBox mainContent;
	private HBox structure, line1, line2;
	private Label lbTitle, lbTitleValue, lbCharacteristics,lbIc;
	private Button btActivities, btCollects;

	public OperationCustomCell() {
		super();

		mainContent = new VBox(0.6f);
		lbTitle = new Label("Título: ");
		lbTitle.setStyle("-fx-font-weight: bold;");

		lbTitleValue = new Label();
		lbCharacteristics = new Label();

		btActivities = new Button("Atividades");
		btActivities.getStyleClass().addAll("btn-fred-lists", "btn-fred-activities");

		btCollects = new Button("Coletas");
		btCollects.getStyleClass().addAll("btn-fred-lists", "btn-fred-collects");

		applyCss(btActivities, btCollects);
		
		line1 = new HBox(lbTitle,lbTitleValue);
		line2 = new HBox(btActivities, btCollects);
		
		lbIc = new Label("", new ImageView("/images/ic_ops.png"));
		VBox.getVgrow(lbIc);
		lbIc.setAlignment(Pos.CENTER);
		lbIc.setTextAlignment(TextAlignment.CENTER);
		
		mainContent.getChildren().addAll(line1, lbCharacteristics, line2);
		Border border = new Border(
				new BorderStroke(
						javafx.scene.paint.Paint.valueOf("#bababa"), 
						BorderStrokeStyle.DASHED, 
						CornerRadii.EMPTY, 
						new BorderWidths(0, 0, 1.0, 0)));
		structure = new HBox(lbIc, mainContent);
		structure.setPadding(new Insets(0, 0, 0, 0));
		structure.setAlignment(Pos.CENTER_LEFT);
		structure.setBorder(border);

	}

	@Override
	protected void updateItem(Operation operation, boolean empty) {
		super.updateItem(operation, empty);
		if (empty) {
			clearContent();
		} else {
			addContent(operation);
		}
	}

	private void addContent(Operation op) {
		setText(null);
		lbTitleValue.setText(op.getName() + ": " + op.getCompany());
		lbCharacteristics.setText(op.getTechnicalCharacteristics());
		btActivities.setOnMouseClicked(evt -> MainEventBus.INSTANCE.eventOpenActivities(op));
		btCollects.setOnMouseClicked(evt -> MainEventBus.INSTANCE.eventOpenCollects(op));
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
