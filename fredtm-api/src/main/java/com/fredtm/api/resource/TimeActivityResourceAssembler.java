package com.fredtm.api.resource;

import com.fredtm.api.rest.AccountResources;
import com.fredtm.core.model.TimeActivity;

public class TimeActivityResourceAssembler extends
		JaxRsResourceAssemblerSupport<TimeActivity, TimeActivityResource> {

	public TimeActivityResourceAssembler() {
		super(AccountResources.class, TimeActivityResource.class);
	}

	@Override
	public TimeActivityResource toResource(TimeActivity entity) {
		TimeActivityResource tar = new TimeActivityResource();
		tar.collectId(entity.getCollect().getId())
				.startDate(entity.getStartDate()).timed(entity.getTimed())
				.activityId(entity.getActivity().getId())
				.finalDate(entity.getFinalDate())
				.collectedAmount(entity.getCollectedAmount());
		return tar;
	}

}
