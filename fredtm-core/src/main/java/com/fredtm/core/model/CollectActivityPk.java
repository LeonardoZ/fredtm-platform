package com.fredtm.core.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class CollectActivityPk implements Serializable {

	private static final long serialVersionUID = 1L;

	private int collect;
	private int activity;

	public int getCollectId() {
		return this.collect;
	}

	public CollectActivityPk() {

	}
	
	

	public int getCollect() {
		return this.collect;
	}

	public void setCollect(int collect) {
		this.collect = collect;
	}

	public int getActivity() {
		return this.activity;
	}

	public void setActivity(int activity) {
		this.activity = activity;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(activity).append(collect).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CollectActivityPk other = (CollectActivityPk) obj;
		return new EqualsBuilder().append(activity, other.activity).append(collect, other.collect).isEquals();

	}

}
