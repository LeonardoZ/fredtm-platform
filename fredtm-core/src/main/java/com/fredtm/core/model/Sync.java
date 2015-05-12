package com.fredtm.core.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
	private Date when;

	@Lob
	@Column(name = "json_old_data")
	private byte[] jsonOldData;

	@Lob
	@Column(name = "json_new_data")
	private byte[] jsonNewData;

	@ManyToOne
	private Account account;

	@ManyToMany
	@JoinTable(name = "sync_operations")
	private List<Operation> operations;

	public Sync() {

	}

	public Date getWhen() {
		return when;
	}

	public void setWhen(Date when) {
		this.when = when;
	}

	public byte[] getJsonOldData() {
		return jsonOldData;
	}

	public void setJsonOldData(byte[] jsonOldData) {
		this.jsonOldData = jsonOldData;
	}

	public byte[] getJsonNewData() {
		return jsonNewData;
	}

	public void setJsonNewData(byte[] jsonNewData) {
		this.jsonNewData = jsonNewData;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(when).append(jsonNewData)
				.append(jsonOldData).append(account).toHashCode();
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
		return new EqualsBuilder().append(getWhen(), other.getWhen())
				.append(getJsonNewData(), other.getJsonNewData())
				.append(getJsonOldData(), other.getJsonOldData())
				.append(getAccount(), other.getAccount()).isEquals();
	}

}
