package com.fredtm.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "operation")
public class Operation extends FredEntity {

	@Transient
	private static final long serialVersionUID = 1L;

	@Column(columnDefinition = "BINARY(16)", name = "uuid")
	private UUID uuid;

	@Column(nullable = false, length = 120, unique = true)
	private String name;

	@Column(nullable = false, length = 120)
	private String company;

	@Column(nullable = false, name = "technical_characteristics")
	private String technicalCharacteristics;

	@OneToMany(mappedBy = "operation")
	private List<Activity> activities;

	@OneToMany(mappedBy = "operation")
	private List<Collect> collects;

	@JoinColumn(name = "account_id")
	@ManyToOne(optional = true)
	private Account account;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	public Operation(String name, String company,
			String technicalCharacteristics) {
		this();
		this.name = name;
		this.company = company;
		this.technicalCharacteristics = technicalCharacteristics;
	}

	public Operation() {
		activities = new ArrayList<Activity>();
		collects = new ArrayList<Collect>();
		modified = GregorianCalendar.getInstance().getTime();
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	public void configureUUID(){
		this.uuid = UUID.randomUUID();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		validation.isValidString(name);
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		validation.isValidString(company);
		this.company = company;
	}

	public String getTechnicalCharacteristics() {
		return technicalCharacteristics;
	}

	public void setTechnicalCharacteristics(String technicalCharacteristics) {
		this.technicalCharacteristics = technicalCharacteristics;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public List<Collect> getCollects() {
		return collects;
	}

	public void setCollects(List<Collect> collects) {
		this.collects = collects;
	}

	public void addCollect(Collect c) {
		if (!collects.contains(c)) {
			this.collects.add(c);
		} else {
			throw new IllegalArgumentException("Collect já adicionada");
		}
	}

	public void removeCollect(Collect c) {
		this.collects.remove(c);
	}

	public List<Activity> getPredifinedActivities() {
		return activities;
	}

	public void setPartialActivities(List<Activity> atividadesParciais) {
		this.activities = atividadesParciais;
	}

	public void addActivity(Activity activity) {
		if (activity.isQuantitative() && hasQuantitativeActivity()) {
			throw new IllegalArgumentException(
					"Activity quantitativa já adicionada.");
		} else if (!activities.contains(activity)) {
			this.activities.add(activity);
		}
	}

	public void removeActivity(Activity activity) {
		this.activities.remove(activity);
	}

	public boolean hasQuantitativeActivity() {
		for (Activity a : activities) {
			if (a.getActivityType().equals(ActivityType.PRODUCTIVE)
					&& a.isQuantitative()) {
				return true;
			}
		}
		return false;
	}

	public Activity getQuantitativeActivity() {
		for (Activity a : activities) {
			if (a.getActivityType().equals(ActivityType.PRODUCTIVE)
					&& a.isQuantitative()) {
				return a;
			}
		}
		return null;
	}

	public Date getModified() {
		return modified;
	}
	
	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	public boolean wasModifiedAfter(Date date){
		return modified.after(date);
	}
	
	public boolean wasModifiedBefore(Date date){
		return modified.before(date);
	}
	
	@Override
	public String toString() {
		return name + " - " + company;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name).append(company)
				.append(technicalCharacteristics).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operation other = (Operation) obj;
		return new EqualsBuilder()
				.append(name, other.name)
				.append(company, other.company)
				.append(technicalCharacteristics,
						other.technicalCharacteristics).isEquals();
	}

}
