package com.fredtm.core.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="collect_activity_speed")
@IdClass(value=CollectActivityPk.class)
public class Speed  {

	@EmbeddedId
	private CollectActivityPk speedPk;
	
	@Id
	@ManyToOne
	@JoinColumn(name="activity_id")
	private Activity activity;
	

	@Id
	@ManyToOne
	@JoinColumn(name="collect_id")
	private Collect collect;
	

	@Column(nullable=false)
	private int speed;
	
	public Speed() {
	}

	
	public Speed(Activity activity, Collect collect, int speed) {
		super();
		this.activity = activity;
		this.collect = collect;
		this.speed = speed;
		this.speedPk = new CollectActivityPk();
	}



	public CollectActivityPk getSpeedPk() {
		return this.speedPk;
	}
	
	public void setSpeedPk(CollectActivityPk speedPk) {
		this.speedPk = speedPk;
	}
	
	public Activity getActivity() {
		return this.activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Collect getCollect() {
		return this.collect;
	}

	public void setCollect(Collect collect) {
		this.collect = collect;
	}

	public int getSpeed() {
		return this.speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
