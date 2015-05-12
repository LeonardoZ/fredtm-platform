package com.fredtm.api.resource;

import com.fredtm.api.rest.ActivityResources;
import com.fredtm.core.model.Activity;

public class ActivityResourceAssembler extends
		JaxRsResourceAssemblerSupport<Activity, ActivityResource> {

	public ActivityResourceAssembler() {
		super(ActivityResources.class, ActivityResource.class);
	}

	@Override
	public ActivityResource toResource(Activity entity) {
		ActivityResource ar = new ActivityResource();
		ar.pkId(entity.getId()).title(entity.getTitle())
				.description(entity.getDescription())
				.activityType(entity.getActivityType().getValue())
				.itemName(entity.getItemName())
				.quantitative(entity.isQuantitative());
		return ar;
	}

}
