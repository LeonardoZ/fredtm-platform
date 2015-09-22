package com.fredtm.api.resource;

import org.springframework.stereotype.Component;

import com.fredtm.core.model.Location;
import com.fredtm.core.model.TimeActivity;
import com.fredtm.resources.TimeActivityDTO;
import com.fredtm.resources.base.ElementParser;

@Component
public class TimeActivityResourceAssembler extends
		ElementParser<TimeActivity, TimeActivityDTO> {

	

	@Override
	public TimeActivityDTO toResource(TimeActivity entity) {
		TimeActivityDTO tar = new TimeActivityDTO();
		Location location = new Location(0, 0);
		tar.uuid(entity.getUuid()).activityTitle(entity.getActivity().getTitle())
				.collectId(entity.getCollect().getUuid())
				.startDate(entity.getStartDate()).timed(entity.getTimed())
				.activityId(entity.getActivity().getUuid())
				.finalDate(entity.getFinalDate())
				.latitude(entity.getLocation().orElseGet(()->location).getLatitude())
				.longitude(entity.getLocation().orElseGet(()->location).getLongitude())
				.collectedAmount(entity.getCollectedAmount());
		return tar;
	}



}
