package com.fredtm.desktop.controller.chart;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

import com.fredtm.core.model.ActivityType;
import com.fredtm.core.model.Collect;
import com.fredtm.desktop.controller.BaseController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

@SuppressWarnings("unchecked")
public class TimeByClassificationController extends BaseController {

	@FXML
	private VBox rootNode;

	private BarChart<Object, Object> chart;

	private Collect collect;

	private List<Collect> collects;
	@FXML
	private Button btnComparative;

	@FXML
	private Button btnPizza;

	public void setCollects(List<Collect> collects) {
		this.collects = collects;
		configureChartForCollects(Orientation.HORIZONTAL);
	}

	public void setCollect(Collect collect) {
		this.collect = collect;
		configureDefaultChart();
	}

	private void configureChartForCollects(Orientation orientation) {
		AtomicInteger ai = new AtomicInteger(0);
		List<Series<Object, Object>> seriesList = FXCollections.observableArrayList();

		collects.forEach(collect -> {
			Series<Object, Object> seriesFrom = getSeriesFrom(collect, orientation);
			seriesFrom.setName(ai.incrementAndGet() + "º Coleta (ciclo)");
			seriesList.add(seriesFrom);
		});

		configureNumberAxis(orientation == Orientation.VERTICAL ? chart.getYAxis() : chart.getXAxis());
		chart.setTitle("Tempo/atividade por coleta (ciclo)");
		chart.getData().addAll(seriesList);
		configureChartOnNode(rootNode);
	}

	public void configureNumberAxis(Axis<Object> axis) {
		axis.setAutoRanging(false);
		axis.setLabel("Tempo (%)");
	}

	@FXML
	void onHorizontalClicked(ActionEvent event) {
		if (collect != null)
			configureHorizontalChart();
		else
			configureChartForCollects(Orientation.VERTICAL);
	}

	@FXML
	void onVerticalClicked(ActionEvent event) {
		if (collect != null)
			configureDefaultChart();
		else
			configureChartForCollects(Orientation.VERTICAL);
	}

	enum Orientation {
		HORIZONTAL, VERTICAL
	}

	void configureChart(Collect c, Orientation orientation, Pane rootNode) {
		Series<Object, Object> series = getSeriesFrom(c, orientation);
		chart.getData().add(series);
		configureNumberAxis(orientation == Orientation.VERTICAL ? chart.getYAxis() : chart.getXAxis());
		configureChartOnNode(rootNode);
	}

	private void configureChartOnNode(Pane rootNode) {

		chart.setLegendVisible(false);
		int size = rootNode.getChildren().size();
		if (size > 1) {
			rootNode.getChildren().remove(1, size - 1);
		}
		rootNode.getChildren().add(chart);
	}

	@SuppressWarnings("rawtypes")
	private Series<Object, Object> getSeriesFrom(Collect c, Orientation orientation) {
		final NumberAxis numberAxis = new NumberAxis();
		final CategoryAxis categoryAxis = new CategoryAxis();
		final boolean isVertical = orientation == Orientation.VERTICAL;

		chart = isVertical ? new BarChart(categoryAxis, numberAxis) : new BarChart(numberAxis, categoryAxis);

		XYChart.Data<Object, Object> data1 = getChartData(c, "Improdutivo", ActivityType.UNPRODUCTIVE, orientation);
		XYChart.Data<Object, Object> data2 = getChartData(c, "Produtivo", ActivityType.PRODUCTIVE, orientation);
		XYChart.Data<Object, Object> data3 = getChartData(c, "Auxiliar", ActivityType.AUXILIARY, orientation);

		configureColor(data1, ActivityType.UNPRODUCTIVE);
		configureColor(data2, ActivityType.PRODUCTIVE);
		configureColor(data3, ActivityType.AUXILIARY);

		Series<Object, Object> series = new XYChart.Series<Object, Object>();
		series.getData().addAll(data1, data2, data3);
		return series;
	}

	private XYChart.Data<Object, Object> getChartData(Collect collect, String label, ActivityType type,
			Orientation orientation) {
		Double total = collect.getTotalPercentageOfTimed(type);
		return new XYChart.Data<>(orientation == Orientation.VERTICAL ? label : total,
				orientation == Orientation.VERTICAL ? total : label);

	}

	private void configureDefaultChart() {
		configureChart(collect, Orientation.VERTICAL, rootNode);
	}

	private void configureHorizontalChart() {
		configureChart(collect, Orientation.HORIZONTAL, rootNode);
	}

	@FXML
	void onComperativeClicked(ActionEvent event) {
		int size = rootNode.getChildren().size();
		if (size > 1) {
			rootNode.getChildren().remove(1, size);
		}
		HBox hBoxBars = new HBox();
		HBox hBoxCircles = new HBox();

		PieChart pieChart = generatePieChart();
		PieChart pieSimpleChart = generateSimplesPieChart();
		
		hBoxCircles.getChildren().addAll(pieChart,pieSimpleChart);
		
		configureChart(collect, Orientation.VERTICAL, hBoxBars);
		configureChart(collect, Orientation.HORIZONTAL, hBoxBars);
		rootNode.getChildren().addAll(hBoxBars, hBoxCircles);

	}

	@FXML
	void onPizzaClicked(ActionEvent event) {
		PieChart pieChart = generatePieChart();

		int size = rootNode.getChildren().size();
		if (size > 1) {
			rootNode.getChildren().remove(1, size);
		}
		rootNode.getChildren().add(pieChart);
	}

	@FXML
	void onSimplePizzaClicked(ActionEvent event) {
		PieChart pieChart = generateSimplesPieChart();

		int size = rootNode.getChildren().size();
		if (size > 1) {
			rootNode.getChildren().remove(1, size);
		}
		rootNode.getChildren().add(pieChart);
	}

	private PieChart generatePieChart() {
		PieChart pieChart = new PieChart();
		Double totalUn = collect.getTotalPercentageOfTimed(ActivityType.UNPRODUCTIVE);
		Double totalAux = collect.getTotalPercentageOfTimed(ActivityType.AUXILIARY);
		Double totalProd = collect.getTotalPercentageOfTimed(ActivityType.PRODUCTIVE);

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

	private PieChart generateSimplesPieChart() {
		PieChart pieChart = new PieChart();
		Double totalUn = collect.getTotalPercentageOfTimed(ActivityType.UNPRODUCTIVE);
		Double totalAux = collect.getTotalPercentageOfTimed(ActivityType.AUXILIARY);
		Double totalProd = collect.getTotalPercentageOfTimed(ActivityType.PRODUCTIVE);

		BigDecimal notProdVal = new BigDecimal(totalAux).add(BigDecimal.valueOf(totalUn)).setScale(2,
				RoundingMode.DOWN);

		Data dataNotProd = new PieChart.Data(configureLabelPercantage("Não-produtivo", notProdVal.doubleValue()),
				notProdVal.doubleValue());
		Data dataProd = new PieChart.Data(configureLabelPercantage("Produtivo ", totalProd), totalProd);

		pieChart.getData().addAll(dataProd,dataNotProd);
		dataNotProd.getNode().setStyle("-fx-pie-color: #ce77ff;");
		dataProd.getNode().setStyle("-fx-pie-color:" + ActivityType.PRODUCTIVE.getHexColor() + ";");

		pieChart.setLegendVisible(false);

		return pieChart;
	}

	String configureLabelPercantage(String text, Double value) {
		BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.DOWN);
		return new StringBuilder().append(text).append(" (").append(bd.toString()).append("%)").toString();
	}

	@FXML
	public void saveAsPng() {
		WritableImage image = chart.snapshot(new SnapshotParameters(), null);
		// TODO: probably use a file chooser here
		DirectoryChooser dc = new DirectoryChooser();
		dc.setTitle("Escolha o local para salvar sua exportação");
		File directory = dc.showDialog(getWindow());
		if (directory != null && directory.isDirectory() && directory.canWrite()) {
			File f = new File(directory.getAbsolutePath() + "/tempo_classific_op.png");

			try {
				f.createNewFile();
				ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", f);
			} catch (IOException e) {
				// TODO: handle exception here
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

}
