package com.fredtm.api.resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.core.model.Activity;
import com.fredtm.core.model.Collect;
import com.fredtm.core.model.TimeActivity;
import com.fredtm.resources.ActivityResource;
import com.fredtm.resources.CollectResource;
import com.fredtm.resources.TimeActivityResource;
import com.fredtm.resources.base.ElementParser;

@Component
public class CollectResourceAssembler extends
		ElementParser<Collect, CollectResource> {

	@Autowired
	private ActivityResourceAssembler acra;
	@Autowired
	private TimeActivityResourceAssembler tara;

	@Override
	public CollectResource toResource(Collect entity) {

		CollectResource cr = new CollectResource();
		cr.setUuid(entity.getId());
		List<Activity> activities = entity.getActivities();
		List<ActivityResource> acrs = acra.toResources(activities);
		List<TimeActivity> times = entity.getTimes();
		List<TimeActivityResource> tars = tara.toResources(times);

		cr.setOperationId(entity.getOperation().getId());
		cr.setActivities(new HashSet<ActivityResource>(acrs));
		cr.setTimes(new HashSet<TimeActivityResource>(tars));

		return cr;
	}

	@Override
	public Collect toEntity(CollectResource r) {
		Collect c = new Collect();
		List<TimeActivity> times = tara.toEntities(r.getTimes());
		List<TimeActivity> activities = new ArrayList<>(times);
		c.setTimes(activities);

		return c;
	}
}
