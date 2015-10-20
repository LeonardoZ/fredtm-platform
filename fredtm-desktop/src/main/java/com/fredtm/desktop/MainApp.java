package com.fredtm.desktop;

import java.nio.charset.Charset;

import com.fredtm.desktop.controller.MainController;
import com.fredtm.desktop.eventbus.MainEventBus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

	private FXMLLoader loader;
	private Scene scene;

	public static void main(String[] args) throws Exception {
		 launch(args);
//		doHere();
//		System.exit(0);
	}

//	private static void doHere() {
//		// TODO Auto-generated method stub
//		List<TimeActivityDTO> times = FredReportFactory.createSimplerTimes();
//		times.stream().forEach(t -> System.out.print((t.getTimed() / 1000) + " "));
//
//		ToLongFunction<TimeActivityDTO> mapper = t -> t.getTimed() / 1000;
//
//		final int size = times.size();
//		final double avg = times.stream().mapToLong(mapper).average().getAsDouble();
//
//		// variance
//		double sum = times.stream().mapToLong(mapper).mapToDouble(v -> Math.pow(v - avg, 2)).sum();
//		double variance = sum / size;
//		double dp = Math.sqrt(variance);
//		double cv = (dp / avg) * 100;
//
//		double density = jdistlib.T.quantile(0.75, 1, true, false);
//		System.err.println("\nDensi "+density);
//		double numericalMean  = 0;
//		
//				System.out.println();
//		System.out.println("Size " + size);
//		System.out.println("Mean " + avg);
//		System.out.println("SUM: " + sum + "\nV:" + variance);
//		System.out.println("DP " + dp);
//		System.out.println("CV " + cv);
//		System.out.println("T's Students " + numericalMean);
//	}

	public void start(Stage stage) throws Exception {

		String fxmlFile = "/fxml/main.fxml";
		loader = new FXMLLoader();
		loader.setCharset(Charset.forName("UTF8"));
		Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
		MainController controller = loader.getController();
		MainEventBus.INSTANCE.registrarOuvinte(controller);

		scene = new Scene(rootNode, 1024, 500);
		scene.getStylesheets().addAll("/styles/main.css");
		stage.setTitle("Fred TM Helper - O ajudante de tempos e movimentos");
		stage.setScene(scene);
		stage.show();

	}

}
