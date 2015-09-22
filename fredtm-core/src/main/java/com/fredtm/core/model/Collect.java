package com.fredtm.core.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
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

	@ManyToOne
	@JoinColumn(nullable = false, name = "operation_id")
	private Operation operation;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE,
			CascadeType.DETACH }, fetch = FetchType.EAGER, mappedBy = "collect", orphanRemoval = true)
	private List<TimeActivity> times;

	@Transient
	private List<Activity> activities;

	@Transient
	private HashMap<Integer, List<TimeActivity>> collectedTimes;

	public Collect() {
		activities = new ArrayList<Activity>();
		collectedTimes = new HashMap<Integer, List<TimeActivity>>();
		times = new ArrayList<TimeActivity>();
	}

	public Collect(Collect collect) {
		this();
		this.setId(collect.getId());
		this.setOperation(collect.getOperation());
	}

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

	public Map<String, Long> getSumOfTimesByActivity() {
		Map<String, Long> collect = times.stream().collect(Collectors.groupingBy(t -> t.getActivity().getTitle(),
				Collectors.reducing(0l, TimeActivity::getEllapsedTimeInSeconds, Long::sum)));
		return collect;
	}

	public Map<String, Double> getSumOfTimesByActivity(ActivityType type) {
		Map<String, Double> collected = times.stream().filter(ta -> ta.getActivity().getActivityType().equals(type))
				.collect(Collectors.groupingBy(t -> t.getActivity().getTitle(), Collectors
						.mapping(tt -> Double.valueOf(tt.getTimed()), Collectors.reducing(0.0, (x, y) -> x + y))));
		return collected;
	}

	public List<TimeActivity> getCollectedTimes() {
		return times;
	}

	public List<TimeActivity> getTimes() {
		return times;
	}

	public void addTimes(Set<TimeActivity> fromResources) {
		fromResources.forEach(times::add);
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

	public Date getFirstTime() throws NoSuchElementException {
		List<TimeActivity> times = getTimeInChronologicalOrder();
		if (times.size() == 0) {
			throw new NoSuchElementException();
		}
		return times.get(0).getFullStartDate();
	}

	public Date getLastTime() throws NoSuchElementException {
		List<TimeActivity> times = getTimeInChronologicalOrder();
		int size = times.size();
		if (size == 0) {
			throw new NoSuchElementException();
		}
		return times.get(--size).getFullFinalDate();
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

	public long getTotalTimed() {
		return times.stream().mapToLong(ta -> ta.getTimed()).sum();
	}

	public long getTotalTimedSeconds() {
		return times.stream().mapToLong(ta -> ta.getEllapsedTimeInSeconds()).sum();
	}

	public long getTotalTimedByType(ActivityType type) {
		long sum = times.stream().filter(tp -> tp.getActivity().getActivityType().equals(type))
				.mapToLong(ta -> ta.getTimed()).sum();
		return sum;
	}

	public long getTotalTimedSecondsByType(ActivityType type) {
		long sum = times.stream().filter(tp -> tp.getActivity().getActivityType().equals(type))
				.mapToLong(ta -> ta.getEllapsedTimeInSeconds()).sum();
		return sum;
	}

	public double getTotalPercentageOfTimed(ActivityType type) {
		long totalSegsType = times.stream().filter(tp -> tp.getActivity().getActivityType().equals(type))
				.mapToLong(ta -> ta.getEllapsedTimeInSeconds()).sum();
		long totalSegs = getTotalTimedSeconds();
		double totalD = Double.valueOf(totalSegs);
		double totalType = Double.valueOf(totalSegsType);
		if (totalD == 0 || totalType == 0)
			return 0;
		return (totalType / totalD) * 100;
	}

	public List<TimeActivity> getTimesByType(ActivityType type) {
		return times.stream().filter(t -> t.getActivity().getActivityType().equals(type)).collect(Collectors.toList());
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
		return new StringBuilder().append(formattedStartDate).append(" - ").append(formattedFinalDate).toString();
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
		return new HashCodeBuilder().append(getId()).append(operation).toHashCode();
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
		return new EqualsBuilder().append(getId(), other.getId()).append(getOperation(), other.getOperation())
				.append(getTimes(), other.getTimes()).isEquals();
	}

}
