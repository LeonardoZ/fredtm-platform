package com.fredtm.resources;

import java.util.Date;

import com.fredtm.resources.base.FredDTOSupport;

public class SyncDTO extends FredDTOSupport {

	private Date created;
	private String operationId;

	public SyncDTO() {

	}

	public SyncDTO uuid(String uuid) {
		setUuid(uuid);
		return this;
	}

	public SyncDTO created(Date value) {
		this.created = value;
		return this;
	}

	public SyncDTO operationId(String value) {
		this.operationId = value;
		return this;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date when) {
		this.created = when;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	@Override
	public String toString() {
		return "SyncResource [created=" + created + ", operationId=" + operationId + ", getUuid()="
				+ getUuid() + ", getLinks()=" + null + "]";
	}

}
