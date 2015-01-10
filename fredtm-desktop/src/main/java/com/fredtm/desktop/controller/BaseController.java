package com.fredtm.desktop.controller;

import javafx.stage.Window;

public class BaseController {

	private Window window;

	public void setWindow(Window window) {
		this.window = window;
	}

	public Window getWindow() {
		return window;
	}

}
