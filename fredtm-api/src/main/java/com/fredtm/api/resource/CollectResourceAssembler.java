package com.fredtm.api.resource;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.core.decorator.TimeMeasure;
import com.fredtm.core.model.Activity;
import com.fredtm.core.model.Collect;
import com.fredtm.core.model.TimeActivity;
import com.fredtm.data.repository.CollectRepository;
import com.fredtm.data.repository.OperationRepository;
import com.fredtm.resources.ActivityDTO;
import com.fredtm.resources.CollectDTO;
import com.fredtm.resources.TimeActivityDTO;
import com.fredtm.resources.base.ElementParser;

@Component
public class CollectResourceAssembler extends
		ElementParser<Collect, CollectDTO> {

	@Autowired
	private ActivityResourceAssembler acra;
	
	@Autowired
	private TimeActivityResourceAssembler tara;
	
	@Autowired
	private CollectRepository collectRepository;

	@Autowired
	private OperationRepository operationRepository;
	
	
	@Override
	public CollectDTO toResource(Collect entity) {

		CollectDTO cr = new CollectDTO();
		cr.setUuid(entity.getUuid());
		cr.setGeneralSpeed(entity.getGeneralSpeed());
		List<Activity> activities = entity.getActivities();
		List<ActivityDTO> acrs = acra.toResources(activities);
		List<TimeActivity> times = entity.getTimes();
		List<TimeActivityDTO> tars = tara.toResources(times);
		TimeMeasure selected = TimeMeasure.SECONDS;
		cr.setGeneralSpeed(entity.getGeneralSpeed());
		cr.setMean(entity.getMeanTime(selected).doubleValue());
		cr.setNormalTime(entity.getNormalTime(selected).doubleValue());
		cr.setOperationalEfficiency(entity.getOperationalEfficiency().doubleValue());
		cr.setUtilizationEfficiency(entity.getUtilizationEfficiency().doubleValue());
		cr.setStandardTime(entity.getStandardTime(selected).doubleValue());
		cr.setProductivity(entity.getProductivity(selected).doubleValue());
		cr.setTotalProduction(entity.getTotalProduction());
		cr.setTotal(entity.getTotalTimed(selected));
		
		cr.setOperationId(entity.getOperation().getUuid());
		cr.setActivities(new HashSet<ActivityDTO>(acrs));
		cr.setTimes(new HashSet<TimeActivityDTO>(tars));

		return cr;
	}

	@Override
	public Collect fromResource(CollectDTO resource) {
		Collect c = collectRepository.findByUuid(resource.getUuid())
				.orElseGet(Collect::new);
		c.setOperation(operationRepository.findByUuid(resource.getOperationId()));
		c.setUuid(resource.getUuid());
		c.setGeneralSpeed(resource.getGeneralSpeed());
		return c;
	}

}
