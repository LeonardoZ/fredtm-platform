package com.fredtm.core.model;

import java.io.Serializable;

public class Entidade implements Serializable {

	private static final long serialVersionUID = 1L;
	protected Long id = 0l;

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
