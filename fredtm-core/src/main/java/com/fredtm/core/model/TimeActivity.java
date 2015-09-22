package com.fredtm.core.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fredtm.core.util.FormatElapsedTime;

@Entity
@Table(name = "time_activity")
public class TimeActivity extends FredEntity {

	@Transient
	private static final long serialVersionUID = 1L;

	@Transient
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss", new Locale("pt", "BR"));

	@ManyToOne()
	@JoinColumn(nullable = false, name = "activity_id")
	private Activity activity;

	@ManyToOne()
	@JoinColumn(nullable = false, name = "collect_id")
	private Collect collect;

	@Transient
	private Calendar dataManager = GregorianCalendar.getInstance();

	@Column(name = "final_date")
	private long finalDate = 0l;

	@Column(name = "start_date")
	private long startDate = 0l;

	@Column
	private long timed = 0l;

	@Column(name = "collected_amount")
	private int collectedAmount;

	@Embedded
	private Location location;

	@OneToMany(cascade = { CascadeType.ALL },fetch=FetchType.EAGER, mappedBy = "timeActivity")
	private List<TimeActivityPicture>	 pictures;
	
	
	public TimeActivity() {
		location = new Location(0, 0);
		activity = new Activity();
		pictures = new LinkedList<>();
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
		System.out.println(startDate);
		return sdf.format(new Date(startDate));
	}

	public String[] getSplitedStartDate() {
		return sdf.format(new Date(startDate)).split(" ");
	}

	public String getFormattedFinalDate() {
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

	public Date getFullFinalDate() {
		return new Date(finalDate);
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
	
	public String getTimeRange() {
		String start = sdf.format(new Date(startDate));
		String end = sdf.format(new Date(finalDate));
		return start + " - " + end;
	}

	public String getFullElapsedTime() {
		String start = sdf.format(new Date(startDate));
		String end = sdf.format(new Date(finalDate));
		return start + " - " + end + " : " + getFormattedEllapsedTime(false);
	}

	public String getFormattedEllapsedTime(boolean breakLine) {
		StringBuilder formatted = new StringBuilder();
		formatted.append(FormatElapsedTime.format(timed));

		String line = breakLine ? "\n" : " - ";

		formatted.append(activity.isQuantitative() ? (line
				+ activity.getItemName() + ": " + getCollectedAmount()) : "");

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

	public void setLocation(Location location){
		this.location = location;
	}
	
	public Optional<Location> getLocation() {
		return Optional.ofNullable(location);
	}
	
	public List<TimeActivityPicture> getPictures() {
		return pictures;
	}
	
	public void setPictures(List<TimeActivityPicture> pictures) {
		this.pictures = pictures;
	}
	

    public void addPictures(List<String> uris) {
        for (String s : uris) {
            addPicture(s);
        }
    }


    public void addPicture(String uri) {
        TimeActivityPicture tap = new TimeActivityPicture();
        tap.setTimeActivity(this);
        tap.setUri(uri);
        addPicture(tap);
    }

    public void addPicture(TimeActivityPicture pic) {
        this.pictures.add(pic);
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
				.append(collect.getId(), other.getCollect().getId())
				.append(finalDate, other.finalDate)
				.append(startDate, other.startDate)
				.append(timed, other.timed)
				.isEquals();
	}

	

}
