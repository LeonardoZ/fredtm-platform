package com.fredtm.api.resource;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class OperationResource extends ResourceSupport {

	private String uuid;
	private String name;
	private String company;
	private String technicalCharacteristics;
	private long accountId;
	private String accountHref;
	private Date modification;
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

	public OperationResource modification(Date value) {
		this.modification = value;
		return this;
	}

	public OperationResource syncs(List<SyncResource> value) {
		this.syncsList = value;
		return this;
	}

	public OperationResource collects(List<CollectResource> value) {
		this.collectsList = value;
		return this;
	}
	
	public OperationResource activities(List<ActivityResource> value) {
		this.acitiviesList = value;
		return this;
	}
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

	public Date getModification() {
		return modification;
	}

	public void setModification(Date modification) {
		this.modification = modification;
	}

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
