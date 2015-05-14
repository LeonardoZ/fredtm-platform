package com.fredtm.api.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fredtm.api.rest.CollectResources;
import com.fredtm.core.model.Activity;
import com.fredtm.core.model.Collect;
import com.fredtm.core.model.TimeActivity;
import com.fredtm.data.repository.CollectRepository;

public class CollectResourceAssembler extends
		JaxRsResourceAssemblerSupport<Collect, CollectResource> {

	@Autowired
	private CollectRepository repository;
	private ActivityResourceAssembler acra = new ActivityResourceAssembler();
	private TimeActivityResourceAssembler tara = new TimeActivityResourceAssembler();

	public CollectResourceAssembler() {
		super(CollectResources.class, CollectResource.class);
	}

	@Override
	public CollectResource toResource(Collect entity) {

		CollectResource cr = new CollectResource();
		List<Activity> activities = entity.getActivities();
		List<ActivityResource> acrs = acra.toResources(activities);
		List<TimeActivity> times = entity.getTimes();
		List<TimeActivityResource> tars = tara.toResources(times);

		cr.setOperationId(entity.getOperation().getId());
		cr.setActivities(acrs);
		cr.setTimes(tars);

		return cr;
	}


	@Override
	public Optional<Collect> fromResource(CollectResource d) {
		Collect c = repository.findOne(d.getUuid());
		if (c == null) {
			return Optional.empty();
		}
		c.setActivities(acra.fromResources(d.getActivities()));
		c.setTimes(tara.fromResources(d.getTimes()));
		return Optional.of(c);
	}

}
