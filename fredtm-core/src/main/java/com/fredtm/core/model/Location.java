package com.fredtm.core.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.PROPERTY)
public class Location {
	private String latitude;
	private String longitude;

	public Location() {
		this(0, 0);
	}

	public Location(long latitude, long longitude) {
		this.latitude = String.valueOf(latitude);
		this.longitude = String.valueOf(longitude);
	}

	public Location(String latitude, String longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getLongitude() {
		return longitude == null || longitude.isEmpty() ? "0" : longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude == null || latitude.isEmpty() ? "0" : latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
}