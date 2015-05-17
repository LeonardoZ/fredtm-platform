package com.fredtm.api.resource;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class OperationResource extends FredResourceSupport {

	private String name;
	private String company;
	private String technicalCharacteristics;
	private String accountId;
	private Date modification;
	private Set<ActivityResource> activities;
	private Set<CollectResource> collects;
	private Set<SyncResource> syncs;

	public OperationResource() {
		// activities = new HashMap<Long, String>();
		// collects = new HashMap<Long, String>();
		// syncs = new HashMap<Long, String>();
	}

	public OperationResource uuid(String value) {
		setUuid(value);
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

	public OperationResource modification(Date value) {
		this.modification = value;
		return this;
	}

	public OperationResource syncs(Set<SyncResource> value) {
		this.syncs = value;
		return this;
	}

	public OperationResource collects(Set<CollectResource> value) {
		this.collects = value;
		return this;
	}

	public OperationResource activities(Set<ActivityResource> value) {
		this.activities = value;
		return this;
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

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Date getModification() {
		return modification;
	}

	public void setModification(Date modification) {
		this.modification = modification;
	}

	public Set<ActivityResource> getActivities() {
		return activities;
	}

	public void setActivities(Set<ActivityResource> activities) {
		this.activities = activities;
	}

	public Set<CollectResource> getCollects() {
		return collects;
	}

	public void setCollects(Set<CollectResource> collects) {
		this.collects = collects;
	}

	public Set<SyncResource> getSyncs() {
		return syncs;
	}

	public void setSyncs(Set<SyncResource> syncs) {
		this.syncs = syncs;
	}

	@Override
	public String toString() {
		return "OperationResource [uuid=" + getUuid() + ", name=" + name
				+ ", company=" + company + ", technicalCharacteristics="
				+ technicalCharacteristics + ", accountId=" + accountId
				+ ", modification=" + modification + ", acitiviesSet="
				+ activities + ", collectsSet=" + collects.toString()
				+ ", syncsSet=" + syncs + "]";
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getUuid()).append(name)
				.append(company).append(technicalCharacteristics)
				.append(modification).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperationResource other = (OperationResource) obj;
		return new EqualsBuilder()
				.append(getUuid(), other.getUuid())
				.append(name, other.name)
				.append(company, other.company)
				.append(technicalCharacteristics,
						other.technicalCharacteristics)
				.append(modification, other.getModification()).isEquals();
	}

}
