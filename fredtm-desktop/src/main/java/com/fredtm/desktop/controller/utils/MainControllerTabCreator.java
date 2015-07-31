package com.fredtm.desktop.controller.utils;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

import com.fredtm.desktop.controller.BaseController;

public class MainControllerTabCreator {

	private TabPane tabPane;

	public MainControllerTabCreator(TabPane tabPane) {
		this.tabPane = tabPane;
	}

	public <T extends BaseController> void criaViewMecanismo(String fxml,
			String titulo, Optional<Consumer<T>> consumidor) {
		Pane p = null;
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
			p = (Pane) fxmlLoader.load();
			if (consumidor.isPresent()) {
				doOnController(p, fxmlLoader, consumidor.get());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		createTabs(p, titulo);
	}

	private <T extends BaseController> void doOnController(Pane p,
			FXMLLoader fxmlLoader, Consumer<T> consumidor) {
		T controller = fxmlLoader.<T> getController();
		Scene scene = p.getScene();
		if (scene != null && scene.getWindow() != null) {
			controller.setWindow(scene.getWindow());
		}
		consumidor.accept(controller);
	}

	private void createTabs(Pane p, String titulo) {
		Tab tab = new Tab(titulo);
		tab.setClosable(true);
		p.setStyle("-fx-background-color: #fff");
		tab.setContent(p);
		tabPane.getTabs().add(tab);
	}
}
