package com.fredtm.core.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class FredEntity implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id = 0l;
	
	@Transient
	protected final Validation validation;

	public FredEntity() {
		validation = new Validation();
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
