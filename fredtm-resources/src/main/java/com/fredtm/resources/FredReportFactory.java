package com.fredtm.resources;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FredReportFactory {

	public static List<GeneralCollectsBean> createMultipleTimes() {
		ArrayList<TimeActivityResource> finaltars = new ArrayList<>();

		for (int i = 1; i <= 5; i++) {
			finaltars.addAll(createTimesWithIndex(Integer.toString(i)));
		}

		ArrayList<TimeActivityResource> aux = new ArrayList<>();
		ArrayList<TimeActivityResource> prod = new ArrayList<>();
		ArrayList<TimeActivityResource> unprod = new ArrayList<>();

		for (TimeActivityResource timeActivityResource : finaltars) {
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

		ArrayList<TimeActivityResource> ttts = new ArrayList<>();
		ttts.addAll(aux);
		ttts.addAll(prod);
		ttts.addAll(unprod);

		gcb.setTimes(ttts);
		ArrayList<GeneralCollectsBean> gcbs = new ArrayList<>();
		gcbs.add(gcb);

		return gcbs;
	}

	public static List<TimeActivityResource> createTimesWithIndex(String colIndex) {
		List<String> acts = new ArrayList<>();
		ArrayList<TimeActivityResource> tars = new ArrayList<>();
		// latitude":"-22.7504339","longitude":"-48.5698403",
		for (int i = 0; i < 15; i++) {
			acts.add(Integer.toOctalString(i));
		}
		long start = new Date().getTime();
		long end = new Date().getTime();

		for (int i = 1; i < 30; i++) {
			for (String nums : acts) {
				TimeActivityResource tar = new TimeActivityResource();
				tar.setTimed(i * 10_000);
				tar.setActivityTitle(nums);

				end = start + tar.getTimed();
				tar.setStartDate(start);
				tar.setFinalDate(end);

				start = end;
				//"latitude":"-22.7504329","longitude":"-48.5698419"
				tar.setCollectIndex(colIndex);
				if (i % 5 == 0) {
					tar.setActivityType("AUXILIARY");
					tar.setLatitude("-22.7504339");
					tar.setLongitude("-48.5698403");
				} else if (i % 2 == 0) {
					tar.setActivityType("PRODUCTIVE");
					tar.setLatitude("-22.7504329");
					tar.setLongitude("-48.5698419");
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

	public static List<TimeActivityResource> createTimes() {
		return createTimesWithIndex("1");
	}

}
