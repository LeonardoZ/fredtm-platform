package com.fredtm.core.report;

import java.util.HashMap;
import java.util.Map;

import com.fredtm.core.model.Collect;

import values.ActivityType;

public class GeneralCollectBean {

	private double totalProductiveTime;
	private double totalUnproductiveTime;
	private double totalAuxiliaryTime;
	private double totalActivityTime;

	private Map<String, Double> productiveActivityTime;
	private Map<String, Double> unproductiveActivityTime;
	private Map<String, Double> auxiliaryActivityTime;
	
	public static GeneralCollectBean factory(Collect collect){
		GeneralCollectBean gcb = new GeneralCollectBean();
		
		gcb.setTotalAuxiliaryTime(collect.getTotalPercentageOfTimed(ActivityType.AUXILIARY));
		gcb.setTotalUnproductiveTime(collect.getTotalPercentageOfTimed(ActivityType.UNPRODUCTIVE));
		gcb.setTotalProductiveTime(collect.getTotalPercentageOfTimed(ActivityType.PRODUCTIVE));
		
		gcb.setAuxiliaryActivityTime(collect.getSumOfTimesByActivity(ActivityType.AUXILIARY));
		gcb.setUnproductiveActivityTime(collect.getSumOfTimesByActivity(ActivityType.UNPRODUCTIVE));
		gcb.setProductiveActivityTime(collect.getSumOfTimesByActivity(ActivityType.PRODUCTIVE));
		
		return gcb;
	}
	
	public GeneralCollectBean() {
		productiveActivityTime = new HashMap<>();
		unproductiveActivityTime = new HashMap<>();
		auxiliaryActivityTime = new HashMap<>();
	}

	public double getTotalProductiveTime() {
		return this.totalProductiveTime;
	}

	public void setTotalProductiveTime(double totalProductiveTime) {
		this.totalProductiveTime = totalProductiveTime;
	}

	public double getTotalUnproductiveTime() {
		return this.totalUnproductiveTime;
	}

	public void setTotalUnproductiveTime(double totalUnproductiveTime) {
		this.totalUnproductiveTime = totalUnproductiveTime;
	}

	public double getTotalAuxiliaryTime() {
		return this.totalAuxiliaryTime;
	}

	public void setTotalAuxiliaryTime(double totalAuxiliaryTime) {
		this.totalAuxiliaryTime = totalAuxiliaryTime;
	}

	public double getTotalActivityTime() {
		return this.totalActivityTime;
	}

	public void setTotalActivityTime(double totalActivityTime) {
		this.totalActivityTime = totalActivityTime;
	}

	public Map<String, Double> getProductiveActivityTime() {
		return this.productiveActivityTime;
	}

	public void setProductiveActivityTime(Map<String, Double> productiveActivityTime) {
		this.productiveActivityTime = productiveActivityTime;
	}

	public Map<String, Double> getUnproductiveActivityTime() {
		return this.unproductiveActivityTime;
	}

	public void setUnproductiveActivityTime(Map<String, Double> unproductiveActivityTime) {
		this.unproductiveActivityTime = unproductiveActivityTime;
	}

	public Map<String, Double> getAuxiliaryActivityTime() {
		return this.auxiliaryActivityTime;
	}

	public void setAuxiliaryActivityTime(Map<String, Double> auxiliaryActivityTime) {
		this.auxiliaryActivityTime = auxiliaryActivityTime;
	}

}
