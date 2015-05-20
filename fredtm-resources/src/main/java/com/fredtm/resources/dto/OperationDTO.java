package com.fredtm.resources.dto;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class OperationDTO extends BaseDTO {

	private String name;
	private String company;
	private String technicalCharacteristics;
	private String accountId;
	private Date modification;
	private Set<ActivityDTO> activities;
	private Set<CollectDTO> collects;
	private Set<SyncDTO> syncs;

	public OperationDTO() {
		// activities = new HashMap<Long, String>();
		// collects = new HashMap<Long, String>();
		// syncs = new HashMap<Long, String>();
	}

	public OperationDTO uuid(String value) {
		setUuid(value);
		return this;
	}

	public OperationDTO name(String value) {
		this.name = value;
		return this;
	}

	public OperationDTO company(String value) {
		this.company = value;
		return this;
	}

	public OperationDTO technicalCharacteristics(String value) {
		this.technicalCharacteristics = value;
		return this;
	}

	public OperationDTO modification(Date value) {
		this.modification = value;
		return this;
	}

	public OperationDTO syncs(Set<SyncDTO> value) {
		this.syncs = value;
		return this;
	}

	public OperationDTO collects(Set<CollectDTO> value) {
		this.collects = value;
		return this;
	}

	public OperationDTO activities(Set<ActivityDTO> value) {
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

	public Set<ActivityDTO> getActivities() {
		return activities;
	}

	public void setActivities(Set<ActivityDTO> activities) {
		this.activities = activities;
	}

	public Set<CollectDTO> getCollects() {
		return collects;
	}

	public void setCollects(Set<CollectDTO> collects) {
		this.collects = collects;
	}

	public Set<SyncDTO> getSyncs() {
		return syncs;
	}

	public void setSyncs(Set<SyncDTO> syncs) {
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
		OperationDTO other = (OperationDTO) obj;
		return new EqualsBuilder()
				.append(getUuid(), other.getUuid())
				.append(name, other.name)
				.append(company, other.company)
				.append(technicalCharacteristics,
						other.technicalCharacteristics)
				.append(modification, other.getModification()).isEquals();
	}

}
