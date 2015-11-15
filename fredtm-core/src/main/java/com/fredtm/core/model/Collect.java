package com.fredtm.core.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import java.util.stream.IntStream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fredtm.core.decorator.TimeMeasure;

import values.ActivityType;
import values.ToleranceFactor;

@Entity
@Table(name = "collect")
public class Collect extends FredEntity {

	@Transient
	private static final long serialVersionUID = 4085712607350133267L;

	@Transient
	private static Comparator<TimeActivity> comparator = (lhs, rhs) -> rhs.getStartDate().compareTo(lhs.getStartDate());

	@Transient
	private static Comparator<TimeActivity> comparatorReverse = comparator.reversed();

	@Transient
	private static Comparator<Activity> comparatorActivities = (lhs, rhs) -> {
		if (rhs.getActivityType().equals(lhs.getActivityType())) {
			return lhs.getTitle().compareTo(rhs.getTitle());
		}
		return rhs.getActivityType().compareTo(lhs.getActivityType());
	};

	@ManyToOne
	@JoinColumn(nullable = false, name = "operation_id")
	private Operation operation;

	@Column(nullable = false, name = "general_speed")
	private int generalSpeed;

	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "collect", orphanRemoval = true)
	private List<TimeActivity> times;

	@Transient
	private List<Activity> activities;

	@Transient
	private HashMap<Integer, List<TimeActivity>> collectedTimes;

	public Collect() {
		activities = new ArrayList<Activity>();
		collectedTimes = new HashMap<Integer, List<TimeActivity>>();
		times = new ArrayList<>();
		generalSpeed = 100;
	}

	public Collect(Collect collect) {
		this();
		this.setId(collect.getId());
		this.setOperation(collect.getOperation());
	}

	@PostLoad
	public synchronized void organizeTimeActivity() {
		this.activities = operation.getActivitiesList();
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

	public BigDecimal getMeanTime(TimeMeasure measure) {
		return BigDecimal.valueOf(times.stream().mapToLong(t -> t.getTimed()).average().orElseGet(() -> 0.0))
				.setScale(2, RoundingMode.HALF_UP)
				.divide(measure.bigfromMillisConverterFactor(), 2, RoundingMode.HALF_UP);
	}

	public double getTotalTimed(TimeMeasure measure) {
		return getTotalTimed() / measure.getFromMillisConverterFactor();
	}

	public BigDecimal getNormalTime(TimeMeasure measure) {
		return getNormalTime().divide(measure.bigfromMillisConverterFactor(), 3, RoundingMode.HALF_UP);
	}

	private BigDecimal getNormalTime() {
		double totalTimed = times.stream().filter(t -> !t.getActivity().getIsIdleActivity())
				.mapToDouble(t -> t.getTimed()).sum();
		double percentSpeed = (((double) generalSpeed) / 100);
		return new BigDecimal(totalTimed).setScale(4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(percentSpeed));
	}

	public BigDecimal getStandardTime(TimeMeasure measure) {
		BigDecimal normalTimeInHours = getNormalTime();

		BigDecimal toleranceFactor = new ToleranceFactor().workingTimes(getWorkTimes())
				.intervalTimes(getIntervalTimes()).calculate();

		return normalTimeInHours.multiply(toleranceFactor).setScale(2, RoundingMode.HALF_UP)
				.divide(measure.bigfromMillisConverterFactor(), 2, RoundingMode.HALF_UP);
	}

	public BigDecimal getUtilizationEfficiency() {
		double aux = getTotalTimedByType(ActivityType.AUXILIARY);
		double prod = getTotalTimedByType(ActivityType.PRODUCTIVE);
		aux = aux / TimeMeasure.HOURS.getFromMillisConverterFactor();
		prod = prod / TimeMeasure.HOURS.getFromMillisConverterFactor();

		double totalTimed = getTotalTimed(TimeMeasure.HOURS);
		if (totalTimed == 0)
			totalTimed = 1;
		double result = (prod - aux) / totalTimed;
		return BigDecimal.valueOf(result * 100).setScale(3, RoundingMode.HALF_UP);
	}

	public BigDecimal getOperationalEfficiency() {
		long unprod = getTotalTimedByType(ActivityType.UNPRODUCTIVE);
		long prod = getTotalTimedByType(ActivityType.PRODUCTIVE);
		double totalTimed = getTotalTimed();
		double value = unprod + totalTimed;
		if (value == 0)
			value = 1;
		double result = prod / value;
		return BigDecimal.valueOf(result * 100).setScale(3, RoundingMode.HALF_UP);
	}

	public BigDecimal getProductivity(TimeMeasure measure) {
		double sum = times.stream().filter(t -> t.getActivity().isQuantitative())
				.flatMapToInt(t -> IntStream.of(t.getCollectedAmount())).sum();

		double totalTimed = getTotalTimed(measure);
		totalTimed = totalTimed == 0.0 ? 1.0 : totalTimed;
		return BigDecimal.valueOf(sum / totalTimed).setScale(3, RoundingMode.HALF_UP);
	}

	public List<TimeActivity> getWorkTimes() {
		List<Activity> work = getActivities().stream()
				.filter(a -> a.getIsIdleActivity() != null && !a.getIsIdleActivity()).collect(Collectors.toList());

		return getTimes().stream().filter(t -> work.contains(t.getActivity())).collect(Collectors.toList());
	}

	public List<TimeActivity> getIntervalTimes() {
		List<Activity> interval = getActivities().stream().filter(a -> a.getIsIdleActivity() != null)
				.filter(Activity::getIsIdleActivity).collect(Collectors.toList());
		return getTimes().stream().filter(t -> interval.contains(t.getActivity())).collect(Collectors.toList());
	}

	public int getGeneralSpeed() {
		return this.generalSpeed == 0 ? 100 : this.generalSpeed;
	}

	public void setGeneralSpeed(int generalSpeed) {
		this.generalSpeed = generalSpeed;
	}

	public long getTotalProduction() {
		return times.stream().filter(t -> t.getActivity().isQuantitative()).map(t2 -> t2.getCollectedAmount())
				.reduce(Integer::sum).orElseGet(() -> new Integer(0));
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
