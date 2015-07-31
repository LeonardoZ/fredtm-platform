package com.fredtm.core.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "collect")
public class Collect extends FredEntity {

	@Transient
	private static final long serialVersionUID = 4085712607350133267L;

	@Transient
	private Comparator<TimeActivity> comparator = (lhs, rhs) -> rhs.getStartDate().compareTo(lhs.getStartDate());

	@Transient
	private Comparator<TimeActivity> comparatorReverse = comparator.reversed();

	@Transient
	private Comparator<Activity> comparatorActivities = (lhs, rhs) -> {
		if (rhs.getActivityType().equals(lhs.getActivityType())) {
			return lhs.getTitle().compareTo(rhs.getTitle());
		}
		return rhs.getActivityType().compareTo(lhs.getActivityType());
	};

	@PostConstruct
	public synchronized void organizeTimeActivity() {
		for (Activity act : activities) {
			List<TimeActivity> timesOf = getTimesOf(act);
			addActivity(act, timesOf);
		}
	}

	private List<TimeActivity> getTimesOf(Activity act) {
		return times.stream().filter(t -> t.getActivity().equals(act)).collect(Collectors.toList());
	}

	@ManyToOne
	@JoinColumn(nullable = false, name = "operation_id")
	private Operation operation;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "collect", orphanRemoval = true)
	private List<TimeActivity> times;

	@Transient
	private List<Activity> activities;

	@Transient
	private HashMap<String, List<TimeActivity>> collectedTimes;

	public Collect() {
		activities = new ArrayList<Activity>();
		collectedTimes = new HashMap<String, List<TimeActivity>>();
		times = new ArrayList<TimeActivity>();
	}

	public Collect(Collect collect) {
		this();
		this.setId(collect.getId());
		this.setOperation(collect.getOperation());
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		validation.isNullValue(operation);
		this.operation = operation;
	}

	public Activity getQuantitativeActivity() {
		return operation.getQuantitativeActivity();
	}

	public TimeActivity addNewTime(Activity activity, Long startDate, Long finalDate, Long timed) {
		TimeActivity activityTime = new TimeActivity(activity, this);
		activityTime.setStartDate(startDate);
		activityTime.setFinalDate(finalDate);
		activityTime.setTimed(timed);
		List<TimeActivity> activitiesTime = collectedTimes.get(activity.getId());
		activitiesTime.add(activityTime);
		organizeTimes(activitiesTime);
		return activityTime;
	}

	private void organizeTimes(List<TimeActivity> times) {
		Collections.sort(times, comparator);
	}

	private void organizeActivities() {
		Collections.sort(activities, comparatorActivities);
	}

	public TimeActivity updateFinal(Activity activity, Long startDate, Long finalDate, Long timed) {
		for (TimeActivity ta : collectedTimes.get(activity.getId())) {
			if (ta.getStartDate().equals(startDate)) {
				ta.setFinalDate(finalDate);
				ta.setTimed(timed);
				return ta;
			}
		}
		return null;
	}

	public List<TimeActivity> getCollectedTimes(Activity param) {
		return collectedTimes.get(param.getId());
	}

	public void setTimes(List<TimeActivity> times) {
		this.times = times;
		this.times.forEach(t -> t.setCollect(this));
	}

	public HashMap<String, Long> getSumOfTimes() {
		List<Long> calculados = collectedTimes
				// Stream<List<TimeActivity>>
				.values().stream()
				// Stream<Stream<Long>>
				.map(m -> m.stream().map((TimeActivity ml) -> ml.getEllapsedTimeInSeconds()))
				// Stream<List<Long>>
				.map(s -> s.collect(Collectors.toList()))
				// LongStream
				.mapToLong(g -> g.stream().reduce(Long::sum).orElse(0l))
				// Stream<Long>
				.boxed()
				// List<Long>
				.collect(Collectors.toList());

		HashMap<String, Long> hashMap = new HashMap<String, Long>();
		for (int i = 0; i < activities.size(); i++) {
			hashMap.put(activities.get(i).getTitle(), calculados.get(i));
		}
		return hashMap;
	}

	public List<TimeActivity> getCollectedTimes() {
		return collectedTimes.values().stream().flatMap(tss -> tss.stream()).collect(Collectors.toList());
	}

	public List<TimeActivity> getTimes() {
		return times;
	}

	public void addTimes(Set<TimeActivity> fromResources) {
		fromResources.forEach(t -> times.add(t));
	}

	public void removeTimeActivity(TimeActivity time) {
		collectedTimes.values().stream().filter(lta -> lta.contains(time)).iterator().remove();
	}

	public List<TimeActivity> getTimeInChronologicalOrder() {
		Collections.sort(times, comparatorReverse);
		return times;
	}

	public String getFirstFormattedDate() throws NoSuchElementException {
		try {
			Date date = getFirstTime();
			return new SimpleDateFormat("dd/MM/yyyy").format(date);
		} catch (NoSuchElementException nse) {
			return "";
		}
	}

	public String getFirstFormattedHour() {
		Date date = getFirstTime();
		return new SimpleDateFormat("hh:mm:ss").format(date);
	}

	private Date getFirstTime() throws NoSuchElementException {
		List<TimeActivity> times = getTimeInChronologicalOrder();
		if (times.size() == 0) {
			throw new NoSuchElementException();
		}
		return times.get(0).getFullStartDate();
	}

	public void addActivity(Activity atv, List<TimeActivity> times) {
		this.collectedTimes.put(atv.getId(), times);
		organizeTimes(times);
	}

	public void removeActivity(Activity at) {
		activities.remove(at);
		collectedTimes.remove(at.getId());
	}

	public List<Activity> listActivities() {
		organizeActivities();
		return activities;
	}

	public List<Activity> getActivities() {
		Collections.sort(activities, comparatorActivities);
		return activities;
	}

	public void setActivities(Set<Activity> activities) {
		activities.forEach(this::addNewActivity);
	}

	public void addNewActivity(Activity activity) {
		activities.add(activity);
		collectedTimes.put(activity.getId(), new ArrayList<TimeActivity>());
	}

	public String getAllCollectedTimes() {
		return collectedTimes.toString();
	}

	public long getTotalTimedSeconds() {
		return collectedTimes.values().stream().flatMap(fl -> fl.stream())
				.mapToLong(ta -> ta.getEllapsedTimeInSeconds()).sum();
	}

	public long getTotalTimedSecondsByType(ActivityType type) {
		long sum = collectedTimes.values().stream().flatMap(fl -> fl.stream())
				.filter(tp -> tp.getActivity().getActivityType().equals(type))
				.mapToLong(ta -> ta.getEllapsedTimeInSeconds()).sum();
		return sum;
	}

	public double getTotalPercentageOfTimed(ActivityType type) {
		long totalSegsType = collectedTimes.values().stream().flatMap(fl -> fl.stream())
				.filter(tp -> tp.getActivity().getActivityType().equals(type))
				.mapToLong(ta -> ta.getEllapsedTimeInSeconds()).sum();
		long totalSegs = getTotalTimedSeconds();
		double totalD = Double.valueOf(totalSegs);
		double totalType = Double.valueOf(totalSegsType);
		if (totalD == 0 || totalType == 0)
			return 0;
		return (totalType / totalD) * 100;
	}

	public String getTimeRangeFormatted() {
		List<TimeActivity> timeInChronologicalOrder = getTimeInChronologicalOrder();
		if (timeInChronologicalOrder.isEmpty())
			return "0 - 0";

		TimeActivity first = timeInChronologicalOrder.get(0);
		int size = timeInChronologicalOrder.size();
		
		TimeActivity last = timeInChronologicalOrder.get(--size);
		String formattedStartDate = first.getFormattedStartDate();
		String formattedFinalDate = last.getFormattedFinalDate();
		return new StringJoiner(formattedStartDate, " - ", formattedFinalDate).toString();
	}

	@Override
	public String toString() {
		String firstFormattedDate = "";
		try {
			firstFormattedDate = getFirstFormattedDate();
		} catch (NoSuchElementException nsee) {
			nsee.printStackTrace();
			firstFormattedDate = "<sem data>";
		}
		return operation + " - " + firstFormattedDate;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Collect other = (Collect) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

}
