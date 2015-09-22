package com.fredtm.resources;

import java.util.List;

public class GeneralCollectsBean {

	private List<TimeActivityDTO> times;
	private List<TimeActivityDTO> productiveTimes;
	private List<TimeActivityDTO> auxiliaryTimes;
	private List<TimeActivityDTO> unproductiveTimes;

	public List<TimeActivityDTO> getTimes() {
		return this.times;
	}

	public void setTimes(List<TimeActivityDTO> times) {
		this.times = times;
	}

	public List<TimeActivityDTO> getProductiveTimes() {
		return this.productiveTimes;
	}

	public void setProductiveTimes(List<TimeActivityDTO> productiveTimes) {
		this.productiveTimes = productiveTimes;
	}

	public List<TimeActivityDTO> getAuxiliaryTimes() {
		return this.auxiliaryTimes;
	}

	public void setAuxiliaryTimes(List<TimeActivityDTO> auxiliaryTimes) {
		this.auxiliaryTimes = auxiliaryTimes;
	}

	public List<TimeActivityDTO> getUnproductiveTimes() {
		return this.unproductiveTimes;
	}

	public void setUnproductiveTimes(List<TimeActivityDTO> unproductiveTimes) {
		this.unproductiveTimes = unproductiveTimes;
	}

	@Override
	public String toString() {
		return "GeneralCollectsBean [productiveTimes=" + this.productiveTimes + ", auxiliaryTimes="
				+ this.auxiliaryTimes + ", unproductiveTimes=" + this.unproductiveTimes + "]";
	}

}
