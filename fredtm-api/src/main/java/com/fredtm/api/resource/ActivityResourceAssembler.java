package com.fredtm.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.core.model.Activity;
import com.fredtm.core.model.ActivityType;
import com.fredtm.data.repository.ActivityRepository;
import com.fredtm.data.repository.OperationRepository;
import com.fredtm.resources.ActivityDTO;
import com.fredtm.resources.base.ElementParser;

@Component
public class ActivityResourceAssembler extends
		ElementParser<Activity, ActivityDTO> {

	@Autowired
	private OperationRepository operationRepository;

	@Autowired
	private ActivityRepository activityRepository;
	
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

	@Override
	public Activity fromResource(ActivityDTO activityResource) {
		Activity activity = activityRepository
					.findByUuid(activityResource.getUuid())
					.orElseGet(Activity::new);
		
		activity.setDescription(activityResource.getDescription());
		activity.setTitle(activityResource.getTitle());
		activity.setActivityType(
				ActivityType.getById(activityResource.getActivityType()).orElse(ActivityType.PRODUCTIVE));
		activity.setItemName(activityResource.getItemName());
		activity.setIsQuantitative(activityResource.getQuantitative());
		activity.setOperation(operationRepository.findByUuid(activityResource.getOperationId()));
		return activity;
	}


}
