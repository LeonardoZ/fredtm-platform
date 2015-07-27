package com.fredtm.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="time_activity_picture")
public class TimeActivityPicture extends FredEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne		
	@JoinColumn(nullable = false, name = "pictures")
	private TimeActivity timeActivity;

	@Column(nullable = false)
	private String uri;

	public TimeActivityPicture() {
		
	}

	public TimeActivity getTimeActivity() {
		return timeActivity;
	}

	public void setTimeActivity(TimeActivity timeActivity) {
		this.timeActivity = timeActivity;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
