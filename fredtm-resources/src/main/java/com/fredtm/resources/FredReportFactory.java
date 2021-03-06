package com.fredtm.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FredReportFactory {

	public static List<GeneralCollectsBean> createMultipleTimes() {
		ArrayList<TimeActivityDTO> finaltars = new ArrayList<>();

		for (int i = 1; i <= 5; i++) {
			finaltars.addAll(createTimesWithIndex(Integer.toString(i)));
		}

		ArrayList<TimeActivityDTO> aux = new ArrayList<>();
		ArrayList<TimeActivityDTO> prod = new ArrayList<>();
		ArrayList<TimeActivityDTO> unprod = new ArrayList<>();

		for (TimeActivityDTO timeActivityResource : finaltars) {
			if (timeActivityResource.getActivityType().equals("AUXILIARY")) {
				aux.add(timeActivityResource);
			} else if (timeActivityResource.getActivityType().equals("PRODUCTIVE")) {
				prod.add(timeActivityResource);
			} else {
				unprod.add(timeActivityResource);
			}
		}

		GeneralCollectsBean gcb = new GeneralCollectsBean();
		gcb.setAuxiliaryTimes(aux);
		gcb.setProductiveTimes(prod);
		gcb.setUnproductiveTimes(unprod);

		ArrayList<TimeActivityDTO> ttts = new ArrayList<>();
		ttts.addAll(aux);
		ttts.addAll(prod);
		ttts.addAll(unprod);

		gcb.setTimes(ttts);
		ArrayList<GeneralCollectsBean> gcbs = new ArrayList<>();
		gcbs.add(gcb);

		return gcbs;
	}

	public static List<TimeActivityDTO> createTimesWithIndex(String colIndex) {
		List<String> acts = new ArrayList<>();
		ArrayList<TimeActivityDTO> tars = new ArrayList<>();
		// latitude":"-22.7504339","longitude":"-48.5698403",
		for (int i = 0; i < 15; i++) {
			acts.add(Integer.toOctalString(i));
		}
		long start = new Date().getTime();
		long end = new Date().getTime();

		for (int i = 1; i < 30; i++) {
			for (String nums : acts) {
				TimeActivityDTO tar = new TimeActivityDTO();
				tar.setTimed(i * 10_000);
				tar.setActivityTitle(nums);

				end = start + tar.getTimed();
				tar.setStartDate(start);
				tar.setFinalDate(end);

				start = end;
				// "latitude":"-22.7504329","longitude":"-48.5698419"
				tar.setCollectIndex(colIndex);
				if (i % 5 == 0) {
					tar.setActivityType("AUXILIARY");
					tar.setLatitude("-22.7504339");
					tar.setLongitude("-48.5698403");
				} else if (i % 2 == 0) {
					tar.setActivityType("PRODUCTIVE");
					tar.setLatitude("-22.7504329");
					tar.setLongitude("-48.5698419");
					if (i == 2) {
						tar.setCollectedAmount(12);
						tar.setItemName("Plants");
					}
				} else {
					tar.setActivityType("UNPRODUCTIVE");
					tar.setLatitude("-22.7504339");
					tar.setLongitude("-48.5698403");
				}
				tars.add(tar);
			}
		}

		return tars;
	}

	public static List<TimeActivityDTO> createTimes() {
		return createTimesWithIndex("1");
	}

	public static List<TimeActivityDTO> createSimplerTimes() {
		List<String> acts = new ArrayList<>();
		List<TimeActivityDTO> tars = new ArrayList<>();
		// latitude":"-22.7504339","longitude":"-48.5698403",
		for (int i = 0; i < 4; i++) {
			acts.add(Integer.toOctalString(i));
		}
		long start = new Date().getTime();
		long end = new Date().getTime();

		for (int i = 1; i < 5; i++) {
			for (String nums : acts) {
				TimeActivityDTO tar = new TimeActivityDTO();
				tar.setTimed(i * 10_000);
				tar.setActivityTitle(nums);

				end = start + tar.getTimed();
				tar.setStartDate(start);
				tar.setFinalDate(end);

				start = end;
				// "latitude":"-22.7504329","longitude":"-48.5698419"
				tar.setCollectIndex("1");
				if (i % 5 == 0) {
					tar.setActivityType("AUXILIARY");
				} else if (i % 2 == 0) {
					tar.setActivityType("PRODUCTIVE");
					if (i == 2) {
						tar.setCollectedAmount(12);
						tar.setItemName("Plants");
					}
				} else {
					tar.setActivityType("UNPRODUCTIVE");
				}
				tars.add(tar);
			}
		}
		return tars;
	}

	public static List<ActivityDTO> createActivities() {
		List<ActivityDTO> acts = new ArrayList<>();
		for (int i = 0; i < 15; i++) {
			ActivityDTO ar = new ActivityDTO();
			ar.setTitle(Integer.toHexString(i));
			ar.setDescription(Integer.toBinaryString(i * 120));
			if (i % 5 == 0) {
				ar.setActivityType(1);
			} else if (i % 2 == 0) {
				ar.setActivityType(2);
			} else {
				ar.setActivityType(0);
			}
			acts.add(ar);
		}
		return acts;
	}

	public static List<CollectDTO> createCollects() {
		ArrayList<CollectDTO> cols = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			CollectDTO c = new CollectDTO();
			c.setGeneralSpeed(100);
			c.setMean(12);
			c.setNormalTime(3.4);
			c.setOperationalEfficiency(3.5);
			c.setProductivity(54.5);
			c.setUtilizationEfficiency(67.6);
			c.setStandardTime(4.5);
			c.setTotal(12121);
			c.setTotalProduction(212);
			c.setIndex(i);
			cols.add(c);
		}
		return cols;
	}

}
