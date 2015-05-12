package com.fredtm.api.resource;

import java.util.List;

import com.fredtm.api.rest.CollectResources;
import com.fredtm.core.model.Activity;
import com.fredtm.core.model.Collect;
import com.fredtm.core.model.TimeActivity;

public class CollectResourceAssembler extends
		JaxRsResourceAssemblerSupport<Collect, CollectResource> {

	public CollectResourceAssembler() {
		super(CollectResources.class, CollectResource.class);
	}

	@Override
	public CollectResource toResource(Collect entity) {
		ActivityResourceAssembler acAssem = new ActivityResourceAssembler();
		TimeActivityResourceAssembler atAssem = new TimeActivityResourceAssembler();

		CollectResource cr = new CollectResource();
		List<Activity> activities = entity.getActivities();
		List<ActivityResource> acrs = acAssem.toResources(activities);
		List<TimeActivity> times = entity.getTimes();
		List<TimeActivityResource> tars = atAssem.toResources(times);
		
		
		cr.setOperationId(entity.getOperation().getId());
		cr.setActivities(acrs);
		cr.setTimes(tars);
		
		return cr;
	}

}
