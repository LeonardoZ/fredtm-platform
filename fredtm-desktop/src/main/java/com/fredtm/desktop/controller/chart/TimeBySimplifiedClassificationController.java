package com.fredtm.desktop.controller.chart;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.fredtm.core.decorator.TimeSystem;
import com.fredtm.core.decorator.TimeSystems;
import com.fredtm.core.model.ActivityType;
import com.fredtm.core.model.Collect;
import com.fredtm.desktop.controller.BaseController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

@SuppressWarnings("unchecked")
public class TimeBySimplifiedClassificationController extends BaseController implements Initializable {

	@FXML
	private VBox rootNode;

	@FXML
	private ChoiceBox<TimeSystems> chcBox;

	@FXML
	private Button btnComparative;

	@FXML
	private Button btnPizza;

	enum Charts {
		V_BARS, H_BARS, PIZZA, ALL;
	}

	private Charts selected = Charts.V_BARS;

	private BarChart<Object, Object> chart;

	private TimeSystem collectSystem;

	private List<TimeSystem> collectsSystem;

	public void setCollects(List<Collect> collects) {
		int selectedIndex = chcBox.getSelectionModel().getSelectedIndex();
		selectedIndex = selectedIndex == -1 ? 0 : selectedIndex;
		this.collectsSystem = chcBox.getSelectionModel().getSelectedItem().buildList(collects);
		configureChartForCollects(Orientation.HORIZONTAL);
		disableButtons();
	}

	private void disableButtons(){
		btnPizza.setVisible(false);
		btnComparative.setVisible(false);
	}
	
	public void setCollect(Collect collect) {
		int selectedIndex = chcBox.getSelectionModel().getSelectedIndex();
		selectedIndex = selectedIndex == -1 ? 0 : selectedIndex;
		this.collectSystem = chcBox.getItems().get(selectedIndex).build(collect);
		configureDefaultChart();
	}

	private void configureChartForCollects(Orientation orientation) {
		AtomicInteger ai = new AtomicInteger(0);
		List<Series<Object, Object>> seriesList = FXCollections.observableArrayList();

		collectsSystem.forEach(collect -> {
			Series<Object, Object> seriesFrom = getSeriesFrom(collect, orientation);
			seriesFrom.setName(ai.incrementAndGet() + "º Coleta (ciclo)");
			seriesList.add(seriesFrom);
		});

		configureNumberAxis(orientation == Orientation.VERTICAL ? chart.getYAxis() : chart.getXAxis());
		configureCategoryAxis(orientation == Orientation.HORIZONTAL ? chart.getYAxis() : chart.getXAxis());
		chart.setTitle("Tempo/atividade por coleta (ciclo)");
		chart.getData().addAll(seriesList);
		configureChartOnNode(rootNode);
	}

	public void configureNumberAxis(Axis<Object> axis) {
		TimeSystems ts = chcBox.getSelectionModel().getSelectedItem();
		boolean isPct = ts == TimeSystems.PCT;
		axis.setAutoRanging(!isPct);
		axis.setLabel("Tempo total (" + ts.getValue() + ")");
	}

	private void configureCategoryAxis(Axis<Object> axis) {
		axis.setLabel("Classificações simplificadas");
	}
	
	@FXML
	void onHorizontalClicked(ActionEvent event) {
		selected = Charts.H_BARS;
		if (collectSystem != null)
			configureHorizontalChart();
		else
			configureChartForCollects(Orientation.HORIZONTAL);
	}

	@FXML
	void onVerticalClicked(ActionEvent event) {
		selected = Charts.V_BARS;
		if (collectSystem != null)
			configureDefaultChart();
		else
			configureChartForCollects(Orientation.VERTICAL);
	}

	enum Orientation {
		HORIZONTAL, VERTICAL
	}

	void configureChart(TimeSystem c, Orientation orientation, Pane rootNode) {
		Series<Object, Object> series = getSeriesFrom(c, orientation);
		chart.getData().add(series);
		configureNumberAxis(orientation == Orientation.VERTICAL ? chart.getYAxis() : chart.getXAxis());
		configureCategoryAxis(orientation == Orientation.HORIZONTAL ? chart.getYAxis() : chart.getXAxis());
		configureChartOnNode(rootNode);
	}

	private void configureChartOnNode(Pane rootNode) {
		chart.setLegendVisible(false);
		chart.setAnimated(true);
		chart.setBarGap(1.0);
		removeNodes(rootNode);
		rootNode.getChildren().add(chart);
	}

	private void removeNodes(Pane rootNode) {
		int size = rootNode.getChildren().size();
		if (size > 1) {
			rootNode.getChildren().remove(1, size);
		}
	}

	@SuppressWarnings("rawtypes")
	private Series<Object, Object> getSeriesFrom(TimeSystem c, Orientation orientation) {
		final NumberAxis numberAxis = new NumberAxis();
		final CategoryAxis categoryAxis = new CategoryAxis();
		final boolean isVertical = orientation == Orientation.VERTICAL;

		chart = isVertical ? new BarChart(categoryAxis, numberAxis) : new BarChart(numberAxis, categoryAxis);

		XYChart.Data<Object, Object> data1 = getChartData(c, "Produtivo", orientation);
		XYChart.Data<Object, Object> data2 = getNotProductiveChartData(c, "Não-Produtivo", orientation);

		configureColor(data1, ActivityType.PRODUCTIVE);
		configureColor(data2, ActivityType.NOT_PRODUCTIVE);

		Series<Object, Object> series = new XYChart.Series<Object, Object>();
		series.getData().addAll(data1, data2);
		return series;
	}

	private XYChart.Data<Object, Object> getChartData(TimeSystem time, String label, Orientation orientation) {
		Double total = time.getValue(ActivityType.PRODUCTIVE);
		return new XYChart.Data<>(orientation == Orientation.VERTICAL ? label : total,
				orientation == Orientation.VERTICAL ? total : label);
	}

	private XYChart.Data<Object, Object> getNotProductiveChartData(TimeSystem time, String label,
			Orientation orientation) {
		Double total = time.getValueSimplified();
		return new XYChart.Data<>(orientation == Orientation.VERTICAL ? label : total,
				orientation == Orientation.VERTICAL ? total : label);
	}

	private void configureDefaultChart() {
		configureChart(collectSystem, Orientation.VERTICAL, rootNode);
	}

	private void configureHorizontalChart() {
		configureChart(collectSystem, Orientation.HORIZONTAL, rootNode);
	}

	@FXML
	void onComperativeClicked(ActionEvent event) {
		selected = Charts.ALL;
		removeNodes(rootNode);
		HBox hBoxBars = new HBox();
		HBox hBoxCircles = new HBox();

		PieChart pieChart = generatePieChart();
		PieChart pieSimpleChart = generateSimplePieChart();

		hBoxCircles.getChildren().addAll(pieChart, pieSimpleChart);

		configureChart(collectSystem, Orientation.VERTICAL, hBoxBars);
		configureChart(collectSystem, Orientation.HORIZONTAL, hBoxBars);
		rootNode.getChildren().addAll(hBoxBars, hBoxCircles);

	}

	@FXML
	void onPizzaClicked(ActionEvent event) {
		selected = Charts.PIZZA;
		PieChart pieChart = generateSimplePieChart();
		removeNodes(rootNode);
		rootNode.getChildren().add(pieChart);
	}


	private PieChart generatePieChart() {
		PieChart pieChart = new PieChart();

		Double totalUn = collectSystem.getValue(ActivityType.UNPRODUCTIVE);
		Double totalAux = collectSystem.getValue(ActivityType.AUXILIARY);
		Double totalProd = collectSystem.getValue(ActivityType.PRODUCTIVE);

		Data dataUn = new PieChart.Data(configureLabelPercantage("Improdutivo ", totalUn), totalUn);
		Data dataAux = new PieChart.Data(configureLabelPercantage("Auxiliar", totalAux), totalAux);
		Data dataProd = new PieChart.Data(configureLabelPercantage("Produtivo ", totalProd), totalProd);

		pieChart.getData().addAll(dataUn, dataAux, dataProd);
		dataUn.getNode().setStyle("-fx-pie-color:" + ActivityType.UNPRODUCTIVE.getHexColor() + ";");
		dataAux.getNode().setStyle("-fx-pie-color:" + ActivityType.AUXILIARY.getHexColor() + ";");
		dataProd.getNode().setStyle("-fx-pie-color:" + ActivityType.PRODUCTIVE.getHexColor() + ";");

		pieChart.setLegendVisible(false);

		return pieChart;
	}

	private PieChart generateSimplePieChart() {
		PieChart pieChart = new PieChart();
		Double totalUn = collectSystem.getValueSimplified();
		Double totalProd = collectSystem.getValue(ActivityType.PRODUCTIVE);

		BigDecimal notProdVal = new BigDecimal(totalUn).setScale(1, RoundingMode.FLOOR);

		Data dataNotProd = new PieChart.Data(configureLabelPercantage("Não-produtivo", notProdVal.doubleValue()),
				notProdVal.doubleValue());
		Data dataProd = new PieChart.Data(configureLabelPercantage("Produtivo ", totalProd), totalProd);

		pieChart.getData().addAll(dataProd, dataNotProd);
		dataNotProd.getNode().setStyle("-fx-pie-color: #ce77ff;");
		dataProd.getNode().setStyle("-fx-pie-color:" + ActivityType.PRODUCTIVE.getHexColor() + ";");

		pieChart.setLegendVisible(false);

		return pieChart;
	}

	String configureLabelPercantage(String text, Double value) {
		BigDecimal bd = new BigDecimal(value).setScale(1, RoundingMode.FLOOR);
		return new StringBuilder().append(text).append(" (").append(bd.toString()).append("" + collectSystem.getSymbol() + ")")
				.toString();
	}

	@FXML
	public void saveAsPng() {
		WritableImage image = chart.snapshot(new SnapshotParameters(), null);
		DirectoryChooser dc = new DirectoryChooser();
		dc.setTitle("Escolha o local para salvar sua exportação");
		File directory = dc.showDialog(getWindow());
		if (directory != null && directory.isDirectory() && directory.canWrite()) {
			File f = new File(directory.getAbsolutePath() + "/tempo_classific_op.png");

			try {
				f.createNewFile();
				ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void configureColor(@SuppressWarnings("rawtypes") XYChart.Data dado, ActivityType tipo) {
		dado.nodeProperty().addListener(new ChangeListener<Node>() {
			@Override
			public void changed(ObservableValue<? extends Node> ov, Node oldNode, Node newNode) {
				if (newNode != null) {
					newNode.setStyle("-fx-bar-fill:" + tipo.getHexColor() + ";");
				}
			}
		});

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
				} else {
					collectsSystem = collectsSystem.stream().map(cs -> newValue.build(cs.getCollect())).collect(Collectors.toList());
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
					onComperativeClicked(null);
					break;
				default:
					break;
				}
			}
		});
	}

}
