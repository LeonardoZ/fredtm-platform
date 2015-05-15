package com.fredtm.api.resource;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.api.rest.AccountResources;
import com.fredtm.core.model.TimeActivity;
import com.fredtm.data.repository.TimeActivityRepository;

@Component
public class TimeActivityResourceAssembler extends
		JaxRsResourceAssemblerSupport<TimeActivity, TimeActivityResource> {

	@Autowired
	private TimeActivityRepository repository;
	
	public TimeActivityResourceAssembler() {
		super(AccountResources.class, TimeActivityResource.class);
	}

	@Override
	public TimeActivityResource toResource(TimeActivity entity) {
		TimeActivityResource tar = new TimeActivityResource();
		tar.uuid(entity.getId())
			.activityTitle(entity.getActivity().getTitle()).collectId(entity.getCollect().getId())
				.startDate(entity.getStartDate()).timed(entity.getTimed())
				.activityId(entity.getActivity().getId())
				.finalDate(entity.getFinalDate())
				.collectedAmount(entity.getCollectedAmount());
		return tar;
	}

	@Override
	public Optional<TimeActivity> fromResource(TimeActivityResource d) {
		TimeActivity ta  = d.getId() != null ? repository.findOne(d.getUuid()) : new TimeActivity();
		ta.setCollectedAmount(d.getCollectedAmount());
		ta.setFinalDate(d.getFinalDate());
		ta.setStartDate(ta.getStartDate());
		ta.setTimed(ta.getTimed());
		return Optional.of(ta);
	}

}
