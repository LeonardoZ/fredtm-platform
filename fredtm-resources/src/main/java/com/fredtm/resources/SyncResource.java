package com.fredtm.resources;

import java.util.Date;

import com.fredtm.resources.base.FredResourceSupport;

public class SyncResource extends FredResourceSupport {

	private Date created;
	private String operationId;

	public SyncResource() {

	}

	public SyncResource uuid(String uuid) {
		setUuid(uuid);
		return this;
	}

	public SyncResource created(Date value) {
		this.created = value;
		return this;
	}

	public SyncResource operationId(String value) {
		this.operationId = value;
		return this;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date when) {
		this.created = when;
	}

	public String getAccountId() {
		return operationId;
	}

	public void setAccountId(String accountId) {
		this.operationId = accountId;
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
