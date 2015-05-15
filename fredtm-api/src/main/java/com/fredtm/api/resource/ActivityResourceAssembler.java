package com.fredtm.api.resource;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.api.rest.ActivityResources;
import com.fredtm.core.model.Activity;
import com.fredtm.core.model.ActivityType;
import com.fredtm.data.repository.ActivityRepository;
import com.fredtm.data.repository.OperationRepository;

@Component
public class ActivityResourceAssembler extends
		JaxRsResourceAssemblerSupport<Activity, ActivityResource> {
	
	@Autowired
	private ActivityRepository repository;
	@Autowired
	private OperationRepository operationRepository;

	public ActivityResourceAssembler() {
		super(ActivityResources.class, ActivityResource.class);
	}

	@Override
	public ActivityResource toResource(Activity entity) {
		ActivityResource ar = new ActivityResource();
		ar.uuid(entity.getId()).title(entity.getTitle())
				.description(entity.getDescription())
				.activityType(entity.getActivityType().getValue())
				.itemName(entity.getItemName())
				.quantitative(entity.isQuantitative());
		return ar;
	}

	@Override
	public Optional<Activity> fromResource(ActivityResource d) {
		Activity act = d.getId() != null ? repository.findOne(d.getUuid())
				: new Activity();
		act.setDescription(d.getDescription());
		act.setTitle(d.getTitle());
		act.setActivityType(ActivityType.getById(d.getActivityType()).orElse(
				ActivityType.PRODUCTIVE));
		act.setItemName(d.getItemName());
		act.setIsQuantitative(d.getQuantitative());

		return Optional.of(act);
	}

}
