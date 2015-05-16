package com.fredtm.api.resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.api.rest.CollectResources;
import com.fredtm.core.model.Activity;
import com.fredtm.core.model.Collect;
import com.fredtm.core.model.TimeActivity;
import com.fredtm.data.repository.CollectRepository;

@Component
public class CollectResourceAssembler extends
		JaxRsResourceAssemblerSupport<Collect, CollectResource> {

	@Autowired
	private CollectRepository repository;
	@Autowired
	private ActivityResourceAssembler acra;
	@Autowired
	private TimeActivityResourceAssembler tara;

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
		cr.setActivities(new HashSet<ActivityResource>(acrs));
		cr.setTimes(new HashSet<TimeActivityResource>(tars));

		return cr;
	}

	@Override
	public Optional<Collect> fromResource(CollectResource d) {
		Collect c = d.getId() != null ? repository.findOne(d.getUuid())
				: new Collect();
		c.setActivities(acra.fromResources(d.getActivities()));

		c.getActivities()
				.forEach(
						a -> {
							List<TimeActivityResource> collected = d
									.getTimes()
									.stream()
									.filter(at -> at.getActivityTitle().equals(
											a.getTitle()))
									.collect(Collectors.toList());
							Set<TimeActivity> fromResources = tara
									.fromResources(collected);
							fromResources.forEach(t -> t.setActivity(a));
							c.addTimes(fromResources);
						});
		Set<TimeActivity> times = tara.fromResources(d.getTimes());
		ArrayList<TimeActivity> activities = new ArrayList<>(times);
		c.setTimes(activities);

		return Optional.of(c);
	}
}
