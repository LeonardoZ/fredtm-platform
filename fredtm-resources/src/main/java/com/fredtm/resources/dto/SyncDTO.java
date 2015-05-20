package com.fredtm.resources.dto;

import java.util.Date;

public class SyncDTO extends BaseDTO {

	private Date created;
	private String jsonOldData;
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

	public SyncDTO jsonOldData(String value) {
		this.jsonOldData = value;
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

	public String getJsonOldData() {
		return jsonOldData;
	}

	public void setJsonOldData(String jsonOldData) {
		this.jsonOldData = jsonOldData;
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
		return "SyncResource [created=" + created + ", jsonOldData="
				+ jsonOldData + ", operationId=" + operationId + ", getUuid()="
				+ getUuid() + ", getLinks()=" + getLinks() + "]";
	}

}
