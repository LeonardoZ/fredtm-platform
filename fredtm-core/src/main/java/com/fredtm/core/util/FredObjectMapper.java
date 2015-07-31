package com.fredtm.core.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fredtm.core.model.Activity;
import com.fredtm.core.model.ActivityType;
import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Operation;
import com.fredtm.core.model.TimeActivity;
import com.fredtm.resources.ActivityResource;
import com.fredtm.resources.CollectResource;
import com.fredtm.resources.OperationResource;
import com.fredtm.resources.TimeActivityResource;

public class FredObjectMapper {

	public static Operation mapResourceToEntity(OperationResource operationResource) {

		Operation operation = new Operation();
		operation.setId(operationResource.getUuid() == null || operationResource.getUuid().isEmpty() ? ""
				: operationResource.getUuid());
		operation.setTechnicalCharacteristics(operationResource.getTechnicalCharacteristics());
		operation.setName(operationResource.getName());
		operation.setCompany(operationResource.getCompany());
		operation.setModified(operationResource.getModification());
		
		Set<ActivityResource> activityResources = operationResource.getActivities();
		for (ActivityResource activityResource : activityResources) {
			Activity activity = new Activity();
			activity.setDescription(activityResource.getDescription());
			activity.setTitle(activityResource.getTitle());
			activity.setActivityType(
					ActivityType.getById(activityResource.getActivityType()).orElse(ActivityType.PRODUCTIVE)
			);
			activity.setItemName(activityResource.getItemName());
			activity.setIsQuantitative(activityResource.getQuantitative());
			activity.setOperation(operation);
			operation.addActivity(activity);
		}

		Set<CollectResource> collectResources = operationResource.getCollects();
		for (CollectResource collectResource : collectResources) {
			Collect collect = new Collect();
			collect.setOperation(operation);
			collect.setActivities(operation.getActivities());
			Set<TimeActivityResource> timesResources = collectResource.getTimes();
			List<TimeActivity> times = new LinkedList<>();
			for (TimeActivityResource timeResource : timesResources) {
				TimeActivity time = new TimeActivity();
				time.setCollectedAmount(timeResource.getCollectedAmount());
				time.setFinalDate(timeResource.getFinalDate());
				time.setStartDate(timeResource.getStartDate());
				time.setTimed(timeResource.getTimed());

				time.setCollect(collect);
				Activity activity = operation.getActivities().stream()
						.filter(a -> a.getTitle().equals(timeResource.getActivityTitle())).findFirst().get();
				time.setActivity(activity);
				times.add(time);
			}
			collect.setTimes(times);
			operation.addCollect(collect);
		}


		return operation;
	}
	
	
    public static List<Operation> mapResourcesToEntities(List<OperationResource> resources) {
        return resources.stream()
        	.map(FredObjectMapper::mapResourceToEntity)
        	.collect(Collectors.toList());
    }
	
	

	
	

}
