package com.fredtm.desktop.controller.chart;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.fredtm.core.decorator.TimeSystem;
import com.fredtm.core.decorator.TimeSystems;
import com.fredtm.core.model.Collect;
import com.fredtm.desktop.controller.BaseController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class TimeByActivityController extends BaseController implements Initializable {

	private PieChart timesPizzaChart;
	private BarChart<Number, String> horizontalChart;
	private BarChart<String, Number> verticalChart;

	private ScrollPane allNode;
	
	@FXML
	private VBox rootPane;

	@FXML
	private ChoiceBox<TimeSystems> chcBox;

    @FXML
    private Button btnSave;
	
	@FXML
	private Button btnPizza;

	@FXML
	private Button btnVertical;

	@FXML
	private Button btnHorizontal;

	private Charts selected;

	private TimeSystem collectSystem;

	@FXML
	void onComparativeClicked(ActionEvent event) {
		this.selected = Charts.ALL;
		this.btnSave.setDisable(true);
		
		VBox father = new VBox();

		allNode = new ScrollPane();
		allNode.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		allNode.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		allNode.setMaxWidth(Double.MAX_VALUE);

		generateVertical();
		generateHorizontal();
		generatePizza();
		
		father.getChildren().addAll(verticalChart,timesPizzaChart,horizontalChart);
		
		VBox.setVgrow(father, Priority.ALWAYS);
		VBox.setVgrow(allNode, Priority.ALWAYS);

		allNode.setContent(father);
		allNode.setFitToWidth(true);
		addChart(allNode);

	}
	
	@FXML
	void onHorizontalClicked(ActionEvent event) {
		this.btnSave.setDisable(false);
		generateHorizontal();
		addChart(this.horizontalChart);

	}

	private void generateHorizontal() {
		this.selected = Charts.H_BARS;
		this.horizontalChart = new BarChart<>(getNumberAxis(), getCategoryAxis());
		
		Map<String, Optional<Double>> timeByActivities = collectSystem.getTimeByActivities();

		XYChart.Series<Number, String> series = new Series<>();
		timeByActivities.forEach((k, v) -> {
			XYChart.Data<Number, String> data = new XYChart.Data<>(v.orElse(0.0), k);
			series.getData().add(data);
		});

		this.horizontalChart.getData().add(series);
		this.horizontalChart.setLegendVisible(false);
	}

	private void addChart(Node chartNode) {
		int size = rootPane.getChildren().size();
		if (size > 2) {
			rootPane.getChildren().remove(2, size);
		}
		rootPane.getChildren().add(chartNode);
	}

	NumberAxis getNumberAxis() {
		NumberAxis numberAxis = new NumberAxis();
		TimeSystems selectedItem = chcBox.getSelectionModel().getSelectedItem();
		if (selectedItem == TimeSystems.PCT) {
			numberAxis.setAutoRanging(false);
		}
		numberAxis.setLabel("Tempo total (" + selectedItem.getValue() + ")");
		return numberAxis;
	}

	CategoryAxis getCategoryAxis() {
		CategoryAxis categoryAxis = new CategoryAxis();
		categoryAxis.setLabel("Atividades");
		return categoryAxis;
	}

	@FXML
	void onPizzaClicked(ActionEvent event) {
		this.btnSave.setDisable(false);
		this.selected = Charts.PIZZA;
		configurePizzaChart();
	}

	@FXML
	void onVerticalClicked(ActionEvent event) {
		this.btnSave.setDisable(false);
		generateVertical();
		addChart(this.verticalChart);
	}

	private void generateVertical() {
		this.selected = Charts.V_BARS;
		this.verticalChart = new BarChart<>(getCategoryAxis(), getNumberAxis());
		Map<String, Optional<Double>> timeByActivities = collectSystem.getTimeByActivities();

		XYChart.Series<String, Number> series = new Series<>();
		timeByActivities.forEach((k, v) -> {
			XYChart.Data<String, Number> data = new XYChart.Data<>(k, v.orElse(0.0));
			series.getData().add(data);
		});

		this.verticalChart.getData().add(series);
		this.verticalChart.setLegendVisible(false);
	}

	public void setCollect(Collect collect) {
		selected = Charts.PIZZA;
		TimeSystems ts = chcBox.getSelectionModel().getSelectedItem();
		this.collectSystem = ts.build(collect);
		configurePizzaChart();
	}

	private void configurePizzaChart() {
		generatePizza();
		addChart(timesPizzaChart);
	}

	private void generatePizza() {
		this.selected = Charts.PIZZA;
		timesPizzaChart = new PieChart();

		ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
		Map<String, Optional<Double>> timeByActivities = collectSystem.getTimeByActivities();
		
		timeByActivities.forEach((k, v) -> {
			list.add(new PieChart.Data(configureLabelPercantage(k, v.get()), v.get()));
		});

		
		timesPizzaChart.setData(list);
		timesPizzaChart.setLegendVisible(false);
		timesPizzaChart.setAnimated(true);
	}

	String configureLabelPercantage(String text, Double value) {
		BigDecimal bd = new BigDecimal(value).setScale(1, RoundingMode.FLOOR);
		return new StringBuilder().append(text).append(" (").append(bd.toString())
				.append("" + collectSystem.getSymbol() + ")").toString();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chcBox.setItems(FXCollections.observableArrayList(TimeSystems.values()));
		chcBox.getSelectionModel().select(3);
		chcBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TimeSystems>() {

			@Override
			public void changed(ObservableValue<? extends TimeSystems> observable, TimeSystems oldValue,
					TimeSystems newValue) {
				if (collectSystem != null) {
					collectSystem = newValue.build(collectSystem.getCollect());
				}

				switch (selected) {
				case H_BARS:
					onHorizontalClicked(null);
					break;
				case V_BARS:
					onVerticalClicked(null);
					break;
				case PIZZA:
					onPizzaClicked(null);
					break;
				case ALL:
					onComparativeClicked(null);
				default:
					break;
				}
			}
		});
	}

	@FXML
	void saveAsPng(ActionEvent event) {

		switch (selected) {
		case H_BARS:
			saveImage(horizontalChart);
			break;
		case V_BARS:
			saveImage(verticalChart);
			break;
		case PIZZA:
			saveImage(timesPizzaChart);
			break;

		default:
			return;
		}
	}

	public void saveImage(Node node) {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setInitialFileName("Gráfico de Tempo e Atividade - "+System.currentTimeMillis());
		// Show save file dialog
		File file = fileChooser.showSaveDialog(getWindow());
		if (file != null) {
			try {
				WritableImage snapshot = node.snapshot(new SnapshotParameters(), null);
				file.createNewFile();
				ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
