package com.fredtm.api.resource;

import org.springframework.stereotype.Component;

import com.fredtm.core.model.Activity;
import com.fredtm.resources.ActivityDTO;
import com.fredtm.resources.base.ElementParser;

@Component
public class ActivityResourceAssembler extends
		ElementParser<Activity, ActivityDTO> {


	@Override
	public ActivityDTO toResource(Activity entity) {
		ActivityDTO ar = new ActivityDTO();
		ar.uuid(entity.getUuid()).title(entity.getTitle())
				.description(entity.getDescription())
				.activityType(entity.getActivityType().getActivityType())
				.itemName(entity.getItemName())
				.quantitative(entity.isQuantitative())
				.operationId(entity.getOperation().getUuid());
		return ar;
	}


}
