package com.fredtm.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.core.model.Location;
import com.fredtm.core.model.TimeActivity;
import com.fredtm.data.repository.ActivityRepository;
import com.fredtm.data.repository.CollectRepository;
import com.fredtm.data.repository.OperationRepository;
import com.fredtm.data.repository.TimeActivityRepository;
import com.fredtm.resources.TimeActivityDTO;
import com.fredtm.resources.base.ElementParser;

@Component
public class TimeActivityResourceAssembler extends
		ElementParser<TimeActivity, TimeActivityDTO> {


	@Autowired
	private OperationRepository operationRepository;

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private TimeActivityRepository timeActivityRepository;

	
	@Autowired
	private CollectRepository collectRepository;


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

	@Override
	public TimeActivity fromResource(TimeActivityDTO timeResource) {
		TimeActivity time = timeActivityRepository.findByUuid(timeResource.getActivityId())
				.orElseGet(TimeActivity::new);
		
		time.setCollectedAmount(timeResource.getCollectedAmount());
		time.setFinalDate(timeResource.getFinalDate());
		time.setStartDate(timeResource.getStartDate());
		time.setTimed(timeResource.getTimed());
		time.setCollect(collectRepository.findByUuid(timeResource.getCollectId()).get());
		time.setActivity(activityRepository.findByTitleAndOperationId(timeResource.getActivityTitle(), time.getCollect().getOperation().getId()));
		return time;
	}



}
