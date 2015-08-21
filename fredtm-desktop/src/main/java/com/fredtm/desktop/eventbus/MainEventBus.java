package com.fredtm.desktop.eventbus;

import java.util.List;

import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Operation;
import com.fredtm.desktop.controller.MainController;
import com.fredtm.desktop.controller.utils.FredCharts;

public class MainEventBus {

	public static MainEventBus INSTANCE = new MainEventBus();
	private MainController main;

	private MainEventBus() {
	}

	public void registrarOuvinte(MainController main) {
		if (this.main != null) {
			throw new IllegalArgumentException("Ouvinte já registrado!");
		}
		this.main = main;
	}

	public void eventOpenActivities(Operation operation) {
		main.openActivities(operation);
	}

	public void eventOpenCollects(Operation operation) {
		main.openCollects(operation);
	}

	public void eventExportCollects(List<Collect> collects) {
		main.exportCollects(collects);
	}

	public void eventOpenTimeActivity(Collect collect) {
		main.openCollectedTimes(collect);
	}

	public void eventChartAnalyses(FredCharts type,
			List<Collect> collects) {
		main.openGraphicalAnalisys(type,  collects);
	}
	
	public void eventChartAnalyses(FredCharts type, Collect collect) {
		main.openGraphicalAnalisys(type, collect);
	}
		

}
