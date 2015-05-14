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
	private Date modification;
	private List<ActivityResource> activities;
	private List<CollectResource> collects;
	private List<SyncResource> syncs;

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

	public OperationResource modification(Date value) {
		this.modification = value;
		return this;
	}

	public OperationResource syncs(List<SyncResource> value) {
		this.syncs = value;
		return this;
	}

	public OperationResource collects(List<CollectResource> value) {
		this.collects = value;
		return this;
	}

	public OperationResource activities(List<ActivityResource> value) {
		this.activities = value;
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

	public Date getModification() {
		return modification;
	}

	public void setModification(Date modification) {
		this.modification = modification;
	}

	public List<ActivityResource> getAcitiviesList() {
		return activities;
	}

	
	public List<ActivityResource> getActivities() {
		return activities;
	}

	public void setActivities(List<ActivityResource> activities) {
		this.activities = activities;
	}

	public List<CollectResource> getCollects() {
		return collects;
	}

	public void setCollects(List<CollectResource> collects) {
		this.collects = collects;
	}

	public List<SyncResource> getSyncs() {
		return syncs;
	}

	public void setSyncs(List<SyncResource> syncs) {
		this.syncs = syncs;
	}

	@Override
	public String toString() {
		return "OperationResource [uuid=" + uuid + ", name=" + name
				+ ", company=" + company + ", technicalCharacteristics="
				+ technicalCharacteristics + ", accountId=" + accountId
				+ ", modification=" + modification + ", acitiviesList="
				+ activities + ", collectsList=" + collects.toString() + ", syncsList="
				+ syncs + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result;
		result = prime * result + (int) (accountId ^ (accountId >>> 32));
		result = prime * result
				+ ((activities == null) ? 0 : activities.hashCode());
		result = prime * result
				+ ((collects == null) ? 0 : collects.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result
				+ ((modification == null) ? 0 : modification.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((syncs == null) ? 0 : syncs.hashCode());
		result = prime
				* result
				+ ((technicalCharacteristics == null) ? 0
						: technicalCharacteristics.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperationResource other = (OperationResource) obj;
		if (accountId != other.accountId)
			return false;
		if (activities == null) {
			if (other.activities != null)
				return false;
		} else if (!activities.equals(other.activities))
			return false;
		if (collects == null) {
			if (other.collects != null)
				return false;
		} else if (!collects.equals(other.collects))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (modification == null) {
			if (other.modification != null)
				return false;
		} else if (!modification.equals(other.modification))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (syncs == null) {
			if (other.syncs != null)
				return false;
		} else if (!syncs.equals(other.syncs))
			return false;
		if (technicalCharacteristics == null) {
			if (other.technicalCharacteristics != null)
				return false;
		} else if (!technicalCharacteristics
				.equals(other.technicalCharacteristics))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}
