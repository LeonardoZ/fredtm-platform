package com.fredtm.api.resource;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class SyncResource extends ResourceSupport {

	private long pkId;
	private Date when;
	private String jsonOldData;
	private String jsonNewData;
	private long accountId;
	private List<Long> operationsIds;

	public SyncResource() {

	}

	public SyncResource pkId(long value) {
		this.pkId = value;
		return this;
	}

	public SyncResource when(Date value) {
		this.when = value;
		return this;
	}

	public SyncResource jsonOldData(String value) {
		this.jsonOldData = value;
		return this;
	}

	public SyncResource jsonNewData(String value) {
		this.jsonNewData = value;
		return this;
	}

	public SyncResource accountId(long value) {
		this.accountId = value;
		return this;
	}

	public SyncResource operationsIds(List<Long> value) {
		this.operationsIds = value;
		return this;
	}

	public long getPkId() {
		return pkId;
	}

	public void setPkId(long pkId) {
		this.pkId = pkId;
	}

	public Date getWhen() {
		return when;
	}

	public void setWhen(Date when) {
		this.when = when;
	}

	public String getJsonOldData() {
		return jsonOldData;
	}

	public void setJsonOldData(String jsonOldData) {
		this.jsonOldData = jsonOldData;
	}

	public String getJsonNewData() {
		return jsonNewData;
	}

	public void setJsonNewData(String jsonNewData) {
		this.jsonNewData = jsonNewData;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public List<Long> getOperationsIds() {
		return operationsIds;
	}

	public void setOperationsIds(List<Long> operationsIds) {
		this.operationsIds = operationsIds;
	}

}
