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
	}

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
