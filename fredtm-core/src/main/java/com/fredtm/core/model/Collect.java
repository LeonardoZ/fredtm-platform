package com.fredtm.core.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name="collect")
public class Collect extends FredEntity {

	@Transient
	private static final long serialVersionUID = 4085712607350133267L;

	@Transient
	private Comparator<TimeActivity> comparator = (lhs, rhs) -> rhs
			.getStartDate().compareTo(lhs.getStartDate());

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
	@JoinColumn(name="operation_id")
	private Operation operation;
	
	@Transient
	private HashMap<Long, List<TimeActivity>> collectedTimes;
	
	@OneToMany(mappedBy="collect")
	private List<TimeActivity> times;
	
	@Transient
	private List<Activity> activities;

	public Collect() {
		activities = new ArrayList<Activity>();
		collectedTimes = new HashMap<Long, List<TimeActivity>>();
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

	public TimeActivity addNewTime(Activity activity, Long startDate,
			Long finalDate, Long timed) {
		TimeActivity activityTime = new TimeActivity(activity, this);
		activityTime.setStartDate(startDate);
		activityTime.setFinalDate(finalDate);
		activityTime.setTimed(timed);
		List<TimeActivity> activitiesTime = collectedTimes.get(activity
				.getId());
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

	public TimeActivity updateFinal(Activity activity, Long startDate,
			Long finalDate, Long timed) {
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

	public HashMap<String, Long> getSumOfTimes() {
		List<Long> calculados = collectedTimes
		// Stream<List<TimeActivity>>
				.values().stream()
				// Stream<Stream<Long>>
				.map(m -> m.stream().map(
						(TimeActivity ml) -> ml
								.getEllapsedTimeInSeconds()))
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

	public List<TimeActivity> getTimes() {
		return collectedTimes.values()
				.stream()
				.flatMap(tss -> tss.stream())
				.collect(Collectors.toList());
	}

	public void remove(TimeActivity time) {
		collectedTimes.values()
			.stream()
			.filter(lta -> lta.contains(time))
			.iterator().remove();
	}

	public List<TimeActivity> getTimeInChronologicalOrder() {
		List<TimeActivity> times = getTimes();
		Collections.sort(times, comparatorReverse);
		return times;
	}

	public String getFirstFormattedDate() throws NoSuchElementException {
		Date date = getFirstTime();
		return new SimpleDateFormat("dd/MM/yyyy").format(date);
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
		this.activities.add(atv);
		this.collectedTimes.put(atv.getId(), times);
		organizeTimes(times);
	}

	public List<Activity> listActivities() {
		organizeActivities();
		return activities;
	}

	public List<Activity> getActivities() {
		Collections.sort(activities, comparatorActivities);
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		for (Activity a : activities) {
			addNewActivity(a);
		}
	}

	public void addNewActivity(Activity activity) {
		activities.add(activity);
		collectedTimes.put(activity.getId(), new ArrayList<TimeActivity>());
	}

	public void removeActivity(Activity at) {
		activities.remove(at);
		collectedTimes.remove(at.getId());
	}

	public String getAllCollectedTimes() {
		return collectedTimes.toString();
	}

	public long getTotalTimedSeconds() {
		return collectedTimes.values()
				.stream()
				.flatMap(fl -> fl.stream())
				.mapToLong(ta -> ta.getEllapsedTimeInSeconds())
				.sum();
	}

	public long getTotalTimedSecondsByType(ActivityType type) {
		long sum = collectedTimes
				.values()
				.stream()
				.flatMap(fl -> fl.stream())
				.filter(tp -> tp.getActivity().getActivityType().equals(type))
				.mapToLong(ta -> ta.getEllapsedTimeInSeconds()).sum();
		return sum;
	}

	public double getTotalPercentegeOfTimed(ActivityType type) {
		long totalSegsType = collectedTimes
				.values()
				.stream()
				.flatMap(fl -> fl.stream())
				.filter(tp -> tp.getActivity().getActivityType().equals(type))
				.mapToLong(ta -> ta.getEllapsedTimeInSeconds()).sum();
		long totalSegs = getTotalTimedSeconds();
		double totalD = Double.valueOf(totalSegs);
		double totalType = Double.valueOf(totalSegsType);
		if(totalD == 0 || totalType == 0) return 0;
		return (totalType / totalD) * 100;
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
		return operation.getName()
				+ " - "
				+ firstFormattedDate;
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
