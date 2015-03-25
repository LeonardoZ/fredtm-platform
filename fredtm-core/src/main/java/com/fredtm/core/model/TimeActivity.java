package com.fredtm.core.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fredtm.core.util.FormatElapsedTime;

public class TimeActivity extends Entity {

	private static final long serialVersionUID = 1L;
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss", new Locale("pt", "BR"));
	private Activity activity;
	private Collect collect;
	private Calendar dataManager = GregorianCalendar.getInstance();
	private long finalDate = 0l;
	private long startDate = 0l;
	private long timed = 0l;
	private int collectedAmount;

	public TimeActivity() {
	}

	public TimeActivity(Activity activity, Collect collect) {
		super();
		this.activity = activity;
		this.collect = collect;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		validation.isNullValue(activity);
		this.activity = activity;
	}

	public Long getTimed() {
		return timed - (timed % 1000);
	}

	public void setTimed(Long timed) {
		validation.isNullValue(timed);
		this.timed = timed;
	}

	public Long getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Long finalDate) {
		validation.isNullValue(finalDate);
		this.finalDate = finalDate;
	}

	public String getFormattedStartDate() {
		return sdf.format(new Date(startDate));
	}

	public String[] getSplitedStartDate() {
		return sdf.format(new Date(startDate)).split(" ");
	}

	public String getFormattedEndDate() {
		return sdf.format(new Date(finalDate));
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		validation.isNullValue(startDate);
		this.startDate = startDate;
	}

	public Date getFullStartDate() {
		return new Date(startDate);
	}

	public Collect getCollect() {
		return collect;
	}

	public void setCollect(Collect collect) {
		validation.isNullValue(collect);
		this.collect = collect;
	}

	public Calendar getDataManager() {
		return dataManager;
	}

	public void setDataManager(Calendar dataManager) {
		validation.isNullValue(dataManager);
		this.dataManager = dataManager;
	}

	public Long getElapsedTime() {
		return (finalDate - startDate);
	}

	public Integer getCollectedAmount() {
		return collectedAmount != 0 ? collectedAmount : 0;
	}

	public void setCollectedAmount(Integer collectedAmount) {
		if (activity == null)
			return;
		if (this.activity.isQuantitative()) {
			this.collectedAmount = collectedAmount;
		}

	}

	public String getFullElapsedTime() {
		String start = sdf.format(new Date(startDate));
		String end = sdf.format(new Date(finalDate));
		return start + " - " + end + " : " + getFormattedEllapsedTime(false);
	}

	public String getFormattedEllapsedTime(boolean breakLine) {
		StringBuilder formatted = new StringBuilder();
			formatted.append(FormatElapsedTime.format(timed)
		);
			
		String line = breakLine ? "\n" : " - ";
		
		formatted.append(
				activity.isQuantitative() ? 
						(line+ activity.getItemName() + ": " + getCollectedAmount()) : "");
		
		return formatted.toString();
	}

	public String getSimpleEllapsedTime() {
		StringBuilder formatado = new StringBuilder();
		formatado.append(FormatElapsedTime.format(timed));
		return formatado.toString();
	}

	public void resetDates() {
		setFinalDate(0l);
		setStartDate(0l);
	}

	public String getFormattedCollectedAmount() {
		return activity.getItemName() + ": " + getCollectedAmount();
	}

	public long getEllapsedTimeInSeconds() {
		long t = timed / 1000;
		return t;
	}

	@Override
	public String toString() {
		return activity.getTitle() + " - " + getFormattedEllapsedTime(false);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(activity).append(collect)
				.append(finalDate).append(startDate).append(timed).build();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeActivity other = (TimeActivity) obj;
		return new EqualsBuilder().append(activity, other.activity)
				.append(collect, other.collect)
				.append(finalDate, other.finalDate)
				.append(startDate, other.startDate).append(timed, other.timed)
				.isEquals();
	}

}
