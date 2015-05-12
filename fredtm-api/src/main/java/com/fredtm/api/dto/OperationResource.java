package com.fredtm.api.dto;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class OperationResource extends ResourceSupport {

	private String uuid;
	private String name;
	private String company;
	private String technicalCharacteristics;
	private long accountId;
	private String accountHref;
	private String modification;
	// private Map<Long, String> activities;
	// private Map<Long, String> collects;
	// private Map<Long, String> syncs;
	private List<ActivityResource> acitiviesList;
	private List<CollectResource> collectsList;
	private List<SyncResource> syncsList;

	public OperationResource() {
		// activities = new HashMap<Long, String>();
		// collects = new HashMap<Long, String>();
		// syncs = new HashMap<Long, String>();
	}

	public OperationResource uuid(String value) {
		this.uuid = value;
		return this;
	}

	public OperationResource name(String value) {
		this.name = value;
		return this;
	}

	public OperationResource company(String value) {
		this.company = value;
		return this;
	}

	public OperationResource technicalCharacteristics(String value) {
		this.technicalCharacteristics = value;
		return this;
	}

	public OperationResource accountHref(String value) {
		this.accountHref = value;
		return this;
	}

	public OperationResource modification(String value) {
		this.modification = value;
		return this;
	}

	// public OperationResource putActivity(Long id, String value) {
	// activities.put(id, value);
	// return this;
	// }
	//
	// public OperationResource putCollect(Long id, String value) {
	// collects.put(id, value);
	// return this;
	// }
	//
	// public OperationResource putSync(Long id, String value) {
	// syncs.put(id, value);
	// return this;
	// }

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTechnicalCharacteristics() {
		return technicalCharacteristics;
	}

	public void setTechnicalCharacteristics(String technicalCharacteristics) {
		this.technicalCharacteristics = technicalCharacteristics;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getAccountHref() {
		return accountHref;
	}

	public void setAccountHref(String accountHref) {
		this.accountHref = accountHref;
	}

	public String getModification() {
		return modification;
	}

	public void setModification(String modification) {
		this.modification = modification;
	}

	// public Map<Long, String> getActivities() {
	// return activities;
	// }
	//
	// public void setActivities(Map<Long, String> activities) {
	// this.activities = activities;
	// }
	//
	// public Map<Long, String> getCollects() {
	// return collects;
	// }
	//
	// public void setCollects(Map<Long, String> collects) {
	// this.collects = collects;
	// }
	//
	// public Map<Long, String> getSyncs() {
	// return syncs;
	// }
	//
	// public void setSyncs(Map<Long, String> syncs) {
	// this.syncs = syncs;
	// }

	public List<ActivityResource> getAcitiviesList() {
		return acitiviesList;
	}

	public void setAcitiviesList(List<ActivityResource> acitiviesList) {
		this.acitiviesList = acitiviesList;
	}

	public List<CollectResource> getCollectsList() {
		return collectsList;
	}

	public void setCollectsList(List<CollectResource> collectsList) {
		this.collectsList = collectsList;
	}

	public List<SyncResource> getSyncsList() {
		return syncsList;
	}

	public void setSyncsList(List<SyncResource> syncsList) {
		this.syncsList = syncsList;
	}

}
