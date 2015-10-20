package com.fredtm.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.core.model.Activity;
import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Speed;
import com.fredtm.data.repository.ActivityRepository;
import com.fredtm.data.repository.CollectRepository;
import com.fredtm.resources.SpeedDTO;
import com.fredtm.resources.base.ElementParser;

@Component
public class SpeedResourceAssembler extends ElementParser<Speed, SpeedDTO> {

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private CollectRepository collectRepository;

	@Override
	public SpeedDTO toResource(Speed entity) {
		SpeedDTO speedDTO = new SpeedDTO();
		speedDTO.setActivityTitle(entity.getActivity().getTitle());
		speedDTO.setActivityUuid(entity.getActivity().getUuid());
		speedDTO.setSpeed(entity.getSpeed());
		speedDTO.setCollectUuid(entity.getCollect().getUuid());
		return speedDTO;
	}

	@Override
	public Speed fromResource(SpeedDTO resource) {
		Activity activity = activityRepository.findByUuid(resource.getActivityUuid()).get();
		Collect collect = collectRepository.findByUuid(resource.getCollectUuid()).get();
		int speed = resource.getSpeed();
		return new Speed(activity, collect, speed);
	}

}
