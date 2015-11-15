package com.fredtm.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "time_activity_picture")
public class TimeActivityPicture extends FredEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(nullable = false, name = "time_activity_id")
	private TimeActivity timeActivity;

	@Column(nullable = false, name = "device_shortcut_uri")
	private String deviceShortcutUri;

	public TimeActivityPicture() {

	}

	public TimeActivity getTimeActivity() {
		return timeActivity;
	}

	public void setTimeActivity(TimeActivity timeActivity) {
		this.timeActivity = timeActivity;
	}

	public String getDeviceShortcutUri() {
		return deviceShortcutUri;
	}

	public void setUri(String uri) {
		this.deviceShortcutUri = uri;
	}

}
