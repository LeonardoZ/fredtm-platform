package com.fredtm.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class FredEntity implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	// @Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(generator = "system-uuid") @Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 37)
	protected String id;

	@Transient
	protected final Validation validation;

	public FredEntity() {
		validation = new Validation();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
