package com.fredtm.api.resource;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fredtm.api.rest.ActivityResources;
import com.fredtm.core.model.Activity;
import com.fredtm.core.model.ActivityType;
import com.fredtm.data.repository.ActivityRepository;

public class ActivityResourceAssembler extends
		JaxRsResourceAssemblerSupport<Activity, ActivityResource> {

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

	@Autowired
	private ActivityRepository repository;
	
	@Override
	public Optional<Activity> fromResource(ActivityResource d) {
		Activity act = repository.findOne(d.getUuid());
		if(act == null){
			return Optional.empty();
		}
		act.setDescription(d.getDescription());
		act.setTitle(d.getTitle());
		act.setActivityType(ActivityType.getById(d.getActivityType()).orElse(ActivityType.PRODUCTIVE));
		act.setItemName(d.getItemName());
		act.setIsQuantitative(d.getQuantitative());
		
		return Optional.of(act);
	}

}
