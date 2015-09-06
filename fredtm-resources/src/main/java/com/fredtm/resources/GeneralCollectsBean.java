package com.fredtm.resources;

import java.util.List;

public class GeneralCollectsBean {

	private List<TimeActivityResource> times;
	private List<TimeActivityResource> productiveTimes;
	private List<TimeActivityResource> auxiliaryTimes;
	private List<TimeActivityResource> unproductiveTimes;

	public List<TimeActivityResource> getTimes() {
		return this.times;
	}

	public void setTimes(List<TimeActivityResource> times) {
		this.times = times;
	}

	public List<TimeActivityResource> getProductiveTimes() {
		return this.productiveTimes;
	}

	public void setProductiveTimes(List<TimeActivityResource> productiveTimes) {
		this.productiveTimes = productiveTimes;
	}

	public List<TimeActivityResource> getAuxiliaryTimes() {
		return this.auxiliaryTimes;
	}

	public void setAuxiliaryTimes(List<TimeActivityResource> auxiliaryTimes) {
		this.auxiliaryTimes = auxiliaryTimes;
	}

	public List<TimeActivityResource> getUnproductiveTimes() {
		return this.unproductiveTimes;
	}

	public void setUnproductiveTimes(List<TimeActivityResource> unproductiveTimes) {
		this.unproductiveTimes = unproductiveTimes;
	}

	@Override
	public String toString() {
		return "GeneralCollectsBean [productiveTimes=" + this.productiveTimes + ", auxiliaryTimes="
				+ this.auxiliaryTimes + ", unproductiveTimes=" + this.unproductiveTimes + "]";
	}

}
