package com.fredtm.core.model;

import java.io.Serializable;

public class Entity implements Serializable {

	private static final long serialVersionUID = 1L;
	protected Long id = 0l;
	protected final Validation validation;

	public Entity() {
		validation = new Validation();
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
