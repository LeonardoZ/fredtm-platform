package com.fredtm.desk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

	private FXMLLoader loader;
	

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        String fxmlFile = "/fxml/main.fxml";
        loader = new FXMLLoader();
        Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

        Scene scene = new Scene(rootNode, 900, 568);
        scene.getStylesheets().add("/styles/main.css");
        stage.setTitle("Fred TM - O ajudante de tempos e movimentos");
        stage.setScene(scene);
        stage.show();
    }

}
