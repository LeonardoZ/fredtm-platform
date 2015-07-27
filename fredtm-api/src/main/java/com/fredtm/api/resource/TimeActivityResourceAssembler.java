package com.fredtm.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.core.model.Activity;
import com.fredtm.core.model.TimeActivity;
import com.fredtm.data.repository.ActivityRepository;
import com.fredtm.data.repository.TimeActivityRepository;
import com.fredtm.resources.TimeActivityResource;
import com.fredtm.resources.base.ElementParser;

@Component
public class TimeActivityResourceAssembler extends
		ElementParser<TimeActivity, TimeActivityResource> {

	private String operationId = "";
	@Autowired
	private TimeActivityRepository repository;
	@Autowired
	private ActivityRepository activityRepository;

	@Override
	public TimeActivityResource toResource(TimeActivity entity) {
		TimeActivityResource tar = new TimeActivityResource();
		tar.uuid(entity.getId()).activityTitle(entity.getActivity().getTitle())
				.collectId(entity.getCollect().getId())
				.startDate(entity.getStartDate()).timed(entity.getTimed())
				.activityId(entity.getActivity().getId())
				.finalDate(entity.getFinalDate())
				.latitude(Long.valueOf(entity.getLatitude()))
				.longitude(Long.valueOf(entity.getLongitude()))
				.collectedAmount(entity.getCollectedAmount());
		return tar;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	@Override
	public TimeActivity toEntity(TimeActivityResource d) {
		TimeActivity ta = hasValidUuid(d) ? repository.findOne(d.getUuid())
				: new TimeActivity();
		if (!operationId.isEmpty() && d.getActivityId().isEmpty()) {
			Activity activity = activityRepository.findByTitleAndOperationId(
					d.getActivityTitle(), operationId);
			ta.setActivity(activity);
		} else {
			Activity activity = activityRepository.findOne(d.getActivityId());
			ta.setActivity(activity);
		}

		ta.setCollectedAmount(d.getCollectedAmount());
		ta.setFinalDate(d.getFinalDate());
		ta.setStartDate(ta.getStartDate());
		ta.setTimed(ta.getTimed());
		ta.setLongitude(String.valueOf(d.getLongitude()));
		ta.setLatitude(String.valueOf(d.getLatitude()));
		return ta;
	}

}
