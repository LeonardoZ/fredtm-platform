package com.fredtm.api.resource;

import org.springframework.stereotype.Component;

import com.fredtm.core.model.Activity;
import com.fredtm.core.model.ActivityType;
import com.fredtm.resources.ActivityResource;
import com.fredtm.resources.base.ElementParser;

@Component
public class ActivityResourceAssembler extends
		ElementParser<Activity, ActivityResource> {


	@Override
	public ActivityResource toResource(Activity entity) {
		ActivityResource ar = new ActivityResource();
		ar.uuid(entity.getId()).title(entity.getTitle())
				.description(entity.getDescription())
				.activityType(entity.getActivityType().getActivityType())
				.itemName(entity.getItemName())
				.quantitative(entity.isQuantitative())
				.operationId(entity.getOperation().getId());
		return ar;
	}

	@Override
	public Activity toEntity(ActivityResource r) {
		Activity act = new Activity();
		act.setDescription(r.getDescription());
		act.setTitle(r.getTitle());
		act.setActivityType(ActivityType.getById(r.getActivityType()).orElse(
				ActivityType.PRODUCTIVE));
		act.setItemName(r.getItemName());
		act.setIsQuantitative(r.getQuantitative());
		return act;
	}

}
