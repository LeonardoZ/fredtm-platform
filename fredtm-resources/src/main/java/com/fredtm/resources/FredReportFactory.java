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
					if (i == 2){
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

}
