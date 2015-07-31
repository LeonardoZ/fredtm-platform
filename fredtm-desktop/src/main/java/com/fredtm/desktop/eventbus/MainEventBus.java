package com.fredtm.desktop.eventbus;

import java.util.List;

import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Operation;
import com.fredtm.desktop.controller.MainController;
import com.fredtm.desktop.controller.utils.GraphicsOptions;

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

	public void eventExportActivities(Operation operation) {
		main.habilitarExportActivities(operation);
	}

	public void eventExportcollects(List<Collect> collects) {
		main.exportCollects(collects);
	}

	public void eventOpenTemposcollectdos(Collect collect) {
		main.openCollectedTimes(collect);
	}

	public void eventTypesGrapficOptions(Collect collect, List<Collect> collects) {
		main.openGraphicTypes(collect, collects);
	}

	public void eventGraphicalAnalises(GraphicsOptions tipo, Collect collect,
			List<Collect> collects) {
		main.openraphicalAnalises(tipo, collect, collects);
	}

}
