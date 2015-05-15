package com.fredtm.core.model;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "sync")
public class Sync extends FredEntity {

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date created;

	@Lob
	@Column(name = "json_old_data")
	private byte[] jsonOldData;

	@ManyToOne
	@JoinColumn(name = "operation_id")
	private Operation operation;

	public Sync() {

	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public byte[] getJsonOldData() {
		return jsonOldData;
	}

	public void setJsonOldData(byte[] jsonOldData) {
		this.jsonOldData = jsonOldData;
	}
	
	public Operation getOperation() {
		return operation;
	}
	
	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(created).append(jsonOldData).append(operation)
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sync other = (Sync) obj;
		return new EqualsBuilder().append(getCreated(), other.getCreated())
				.append(getJsonOldData(), other.getJsonOldData())
				.append(getOperation(), other.getOperation()).isEquals();
	}

	@Override
	public String toString() {
		return "Sync [created=" + created + ", jsonOldData="
				+ Arrays.toString(jsonOldData) + ", operation=" + operation
				+ "]";
	}

	
	
}
