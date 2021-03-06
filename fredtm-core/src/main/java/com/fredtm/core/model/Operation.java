package com.fredtm.core.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import values.ActivityType;

@Entity
@Table(name = "operation")
public class Operation extends FredEntity {

	@Transient
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 120)
	private String name;

	@Column(nullable = false, length = 120)
	private String company;

	@Column(nullable = false, name = "technical_characteristics")
	private String technicalCharacteristics;

	@JoinColumn(nullable = false, name = "account_id")
	@ManyToOne(optional = true)
	private Account account;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "operation")
	private Set<Activity> activities;

	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "operation")
	private Set<Collect> collects;

	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER, mappedBy = "operation")
	private List<Sync> syncs;

	public Operation(String name, String company, String technicalCharacteristics) {
		this();
		this.name = name;
		this.company = company;
		this.technicalCharacteristics = technicalCharacteristics;
	}

	public Operation() {
		activities = new HashSet<Activity>();
		collects = new HashSet<Collect>();
		syncs = new ArrayList<Sync>();
		modified = GregorianCalendar.getInstance().getTime();
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

	public Set<Activity> getActivities() {
		return activities;
	}

	public List<Activity> getActivitiesList() {
		return new ArrayList<Activity>(activities);
	}

	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
		this.activities.forEach(a -> a.setOperation(this));
	}

	public void setActivities(List<Activity> activities) {
		this.activities = new HashSet<Activity>(activities);
		this.activities.forEach(a -> a.setOperation(this));
	}

	public Set<Collect> getCollects() {
		return this.collects;
	}

	public List<Collect> getCollectsList() {
		return new ArrayList<>(collects);
	}

	public void setCollects(List<Collect> collects) {
		this.collects = new HashSet<>(collects);
		this.collects.forEach(c -> c.setOperation(this));
	}

	public void addCollect(Collect c) {
		this.collects.add(c);
		c.setOperation(this);
	}

	public void removeCollect(Collect c) {
		this.collects.remove(c);
	}

	public Set<Activity> getPredifinedActivities() {
		return activities;
	}

	public void setPartialActivities(Set<Activity> atividadesParciais) {
		this.activities = atividadesParciais;
	}

	public void addActivity(Activity activity) {
		if (activity.isQuantitative() && hasQuantitativeActivity()) {
			throw new IllegalArgumentException("Activity quantitativa já adicionada.");
		} else if (!activities.contains(activity)) {
			this.activities.add(activity);
		}
	}

	public void removeActivity(Activity activity) {
		this.activities.remove(activity);
	}

	public boolean hasQuantitativeActivity() {
		for (Activity a : activities) {
			if (a.getActivityType().equals(ActivityType.PRODUCTIVE) && a.isQuantitative()) {
				return true;
			}
		}
		return false;
	}

	public Activity getQuantitativeActivity() {
		for (Activity a : activities) {
			if (a.getActivityType().equals(ActivityType.PRODUCTIVE) && a.isQuantitative()) {
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

	public void modified() {
		this.modified = new Date();
	}

	public boolean wasModifiedAfter(Date date) {
		return modified.after(date);
	}

	public boolean wasModifiedBefore(Date date) {
		return modified.before(date);
	}

	public List<Sync> getSyncs() {
		return syncs;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}

	public void setSyncs(List<Sync> syncs) {
		this.syncs = syncs;
		this.syncs.forEach(s -> s.setOperation(this));
	}

	public void addSync(Sync sync) {
		this.syncs.add(sync);
	}

	public boolean numberOfCollectsIsRight(double error) {
		List<TimeActivity> times = getCollects().stream().flatMap(c -> c.getTimes().stream())
				.collect(Collectors.toList());

		SummaryStatistics summaryStatistics = new SummaryStatistics();

		times.stream().mapToLong(t -> t.getTimed() / 1000).forEach(r -> {
			summaryStatistics.addValue(r);
		});

		int collectsSize = getCollects().size();
		final double avg = summaryStatistics.getMean();

		double dp = summaryStatistics.getStandardDeviation();
		double cv = (dp / avg);

		double student = 1.085;
		double result = (Math.pow(student, 2) * Math.pow(cv, 2)) / Math.pow(0.05, 2);
		return collectsSize >= result;
	}

	public String getTimeRange() {
		if (collects.isEmpty()) {
			return "0 - 0";
		}
		Iterator<Collect> iterator = collects.iterator();
		Collect first = iterator.next();
		Collect last = null;
		while (iterator.hasNext()) {
			last = iterator.next();
		}
		if(last == null) last = first;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("pt", "BR"));

		Date firstTime = first.getFirstTime();
		Date lastTime = last.getLastTime();
		StringBuilder sb = new StringBuilder();
		sb.append(sdf.format(firstTime));
		sb.append(" - ");
		sb.append(sdf.format(lastTime));
		return sb.toString();
	}

	@PrePersist
	public void setModificate() {
		setModified(new Date());
	}

	@Override
	public String toString() {
		return name + " - " + company + " - " + collects.size();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name).append(company).append(technicalCharacteristics).toHashCode();
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
		return new EqualsBuilder().append(name, other.name).append(company, other.company)
				.append(technicalCharacteristics, other.technicalCharacteristics).isEquals();
	}

	public Sync getLastSync() {
		Collections.sort(syncs);
		return syncs != null && !syncs.isEmpty() ? syncs.get(0) : null;
	}

}
