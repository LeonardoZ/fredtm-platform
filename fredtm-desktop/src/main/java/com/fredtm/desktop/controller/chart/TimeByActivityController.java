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
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

public class TimeByActivityController extends BaseController implements Initializable {

	private PieChart timesPizzaChart;
	private BarChart<Number, String> horizontalChart;
	private BarChart<String, Number> verticalChart;

	@FXML
	private VBox rootPane;

	@FXML
	private ChoiceBox<TimeSystems> chcBox;

	@FXML
	private Button btnPizza;

	@FXML
	private Button btnVertical;

	@FXML
	private Button btnHorizontal;

	private Charts selected;

	private TimeSystem collectSystem;

	@FXML
	void onHorizontalClicked(ActionEvent event) {

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
		addChart(this.horizontalChart);

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
		if (chcBox.getSelectionModel().getSelectedItem() == TimeSystems.PCT) {
			numberAxis.setAutoRanging(false);
		}
		return numberAxis;
	}

	CategoryAxis getCategoryAxis() {
		return new CategoryAxis();
	}

	@FXML
	void onPizzaClicked(ActionEvent event) {
		this.selected = Charts.PIZZA;
		configurePizzaChart();
	}

	@FXML
	void onVerticalClicked(ActionEvent event) {

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
		addChart(this.verticalChart);
	}

	public void setCollect(Collect collect) {
		selected = Charts.PIZZA;
		TimeSystems ts = chcBox.getSelectionModel().getSelectedItem();
		this.collectSystem = ts.build(collect);
		configurePizzaChart();
	}

	private void configurePizzaChart() {
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
		addChart(timesPizzaChart);
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
		WritableImage image = node.snapshot(new SnapshotParameters(), null);
		DirectoryChooser dc = new DirectoryChooser();
		dc.setTitle("Escolha o local para salvar sua exportação");
		File directory = dc.showDialog(getWindow());
		if (directory != null && directory.isDirectory() && directory.canWrite()) {
			File f = new File(directory.getAbsolutePath() + "/tempo_atividade_"
					+ collectSystem.getCollect().getOperation().toString() + "_" + System.currentTimeMillis() + ".png");

			try {
				f.createNewFile();
				ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
