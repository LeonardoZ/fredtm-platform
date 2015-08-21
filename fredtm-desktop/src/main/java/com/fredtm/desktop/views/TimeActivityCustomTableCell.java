package com.fredtm.desktop.views;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import com.fredtm.core.model.Location;
import com.fredtm.core.model.TimeActivity;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class TimeActivityCustomTableCell extends TableCell<TimeActivity, Optional<Location>>
		implements MapComponentInitializedListener {

	private Location location;
	private GoogleMapView view;

	protected void updateItem(Location value, boolean empty) {
		if (empty)
			return;
		this.location = value;
		setText("");
		setGraphic(null);
		ImageView image = null;
		try {
			InputStream newInputStream = Files.newInputStream(Paths.get("src/main/resources/images/ic_maps_place.png"));
			image = new ImageView();
			image.setImage(new Image(newInputStream));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setOnMouseClicked(evt -> {
			createMapView();
		});
		setGraphic(image);
	}

	@Override
	public void mapInitialized() {
		Double latitude = Double.valueOf(location.getLatitude());
		Double longitude = Double.valueOf(location.getLongitude());

		MapOptions mapOptions = new MapOptions();
		mapOptions.center(new LatLong(latitude, longitude)).overviewMapControl(true).panControl(true)
				.rotateControl(true).scaleControl(true).streetViewControl(true).zoomControl(true).zoom(12);

		GoogleMap map = view.createMap(mapOptions);

		// Add a marker to the map
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(new LatLong(latitude, longitude)).visible(Boolean.TRUE);
		Marker marker = new Marker(markerOptions);
		map.addMarker(marker);
	}

	public void createMapView() {
		view = new GoogleMapView();
		Stage stage = new Stage();
		stage.setScene(new Scene(view));
		stage.setTitle("Localização");
		stage.show();
		view.addMapInializedListener(this);
	}
}
