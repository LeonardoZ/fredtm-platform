package com.fredtm.api.dto;

import java.util.List;

public class SyncResource {

	private long pkId;
	private String when;
	private String jsonOldData;
	private String jsonNewData;
	private long accountId;
	private List<Long> operationsIds;

	public SyncResource() {

	}
	
	public long getPkId() {
		return pkId;
	}

	public void setPkId(long pkId) {
		this.pkId = pkId;
	}

	public String getWhen() {
		return when;
	}

	public void setWhen(String when) {
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
