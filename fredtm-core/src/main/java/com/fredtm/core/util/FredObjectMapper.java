package com.fredtm.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.fredtm.core.model.Activity;
import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Location;
import com.fredtm.core.model.Operation;
import com.fredtm.core.model.Sync;
import com.fredtm.core.model.TimeActivity;
import com.fredtm.core.model.TimeActivityPicture;
import com.fredtm.resources.ActivityDTO;
import com.fredtm.resources.CollectDTO;
import com.fredtm.resources.OperationDTO;
import com.fredtm.resources.SyncDTO;
import com.fredtm.resources.TimeActivityDTO;

import values.ActivityType;

public class FredObjectMapper {

	public static Operation mapResourceToEntity(OperationDTO operationResource) {

		Operation operation = new Operation();
		operation.setUuid(operationResource.getUuid() == null || operationResource.getUuid().equals("") ? ""
				: operationResource.getUuid());
		operation.setTechnicalCharacteristics(operationResource.getTechnicalCharacteristics());
		operation.setName(operationResource.getName());
		operation.setCompany(operationResource.getCompany());
		operation.setModified(operationResource.getModification());

		Set<ActivityDTO> activityResources = operationResource.getActivities();
		if (activityResources != null) {
			for (ActivityDTO activityResource : activityResources) {
				Activity activity = new Activity();
				activity.setDescription(activityResource.getDescription());
				activity.setTitle(activityResource.getTitle());
				activity.setActivityType(
						ActivityType.getById(activityResource.getActivityType()).orElse(ActivityType.PRODUCTIVE));
				activity.setItemName(activityResource.getItemName());
				activity.setIsQuantitative(activityResource.getQuantitative());
				activity.setIsIdleActivity(activityResource.getIdleActivity());
				activity.setOperation(operation);
				operation.addActivity(activity);
			}
		}

		List<CollectDTO> collectResources = operationResource.getCollects();
		if (collectResources != null) {
			for (CollectDTO collectResource : collectResources) {
				Collect collect = new Collect();
				collect.setOperation(operation);
				collect.setActivities(operation.getActivities());
				collect.setGeneralSpeed(collectResource.getGeneralSpeed());
				Set<TimeActivityDTO> timesResources = collectResource.getTimes();
				List<TimeActivity> times = new LinkedList<>();

				for (TimeActivityDTO timeResource : timesResources) {
					TimeActivity time = new TimeActivity();
					time.setCollectedAmount(timeResource.getCollectedAmount());
					time.setFinalDate(timeResource.getFinalDate());
					time.setStartDate(timeResource.getStartDate());
					time.setTimed(timeResource.getTimed());
					time.setCollect(collect);

					Activity activity = operation.getActivities().stream()
							.filter(a -> a.getTitle().equals(timeResource.getActivityTitle())).findFirst().get();
					time.setActivity(activity);
					time.setCollectedAmount(timeResource.getCollectedAmount());

					time.setLocation(new Location(timeResource.getLatitude(), timeResource.getLongitude()));
					timeResource.getPics().forEach(p -> {
						TimeActivityPicture tap = new TimeActivityPicture();
						tap.setContent(p.getCompressedPictureContent().getBytes());
						time.addPicture(tap);
					});

					times.add(time);
				}
				collect.setTimes(times);
				operation.addCollect(collect);
			}
		}

		return operation;
	}

	public static List<Operation> mapResourcesToEntities(List<OperationDTO> resources) {
		return resources.stream().map(FredObjectMapper::mapResourceToEntity).collect(Collectors.toList());
	}

	public static OperationDTO mapEntityToResource(Operation entity, String accUuid) {

		return new OperationDTO().uuid(entity.getUuid()).name(entity.getName())
				.technicalCharacteristics(entity.getTechnicalCharacteristics()).company(entity.getCompany())
				.modification(entity.getModified())
				.accountId(entity.getAccount() != null ? entity.getAccount().getUuid() : null)
				.activities(toResourcesActivities(entity.getActivities()))
				.collects(toResourcesCol(entity.getCollectsList())).lastSync(toResource(entity.getLastSync()));

	}

	private static SyncDTO toResource(Sync entity) {
		SyncDTO sr = new SyncDTO();
		if (entity != null) {
			sr.uuid(entity.getUuid()).created(entity.getCreated()).operationId(entity.getOperation().getUuid());
		}
		return sr;
	}

	public static Set<ActivityDTO> toResourcesActivities(Collection<Activity> set) {
		Set<ActivityDTO> crs = new HashSet<>();
		for (Activity entity : set) {
			ActivityDTO ar = new ActivityDTO();
			ar.title(entity.getTitle()).description(entity.getDescription())
					.activityType(entity.getActivityType().getActivityType()).itemName(entity.getItemName())
					.quantitative(entity.isQuantitative()).operationId(entity.getOperation().getUuid())
					.idle(entity.getIsIdleActivity());
			System.out.println(ar.getIdleActivity());;
			crs.add(ar);
		}
		return crs;
	}

	public static List<CollectDTO> toResourcesCol(List<Collect> cols) {
		List<CollectDTO> crs = new LinkedList<>();
		AtomicInteger ai = new AtomicInteger(1);
		for (Collect entity : cols) {
			CollectDTO cr = new CollectDTO();
			cr.setIndex(ai.getAndIncrement());
			List<Activity> activities = entity.getActivities();
			List<ActivityDTO> acrs = new ArrayList<>(toResourcesActivities(activities));
			List<TimeActivity> times = entity.getTimes();
			List<TimeActivityDTO> tars = toResourcesFromTimeActivity(times);

			cr.setOperationId(entity.getOperation().getUuid());
			cr.setGeneralSpeed(entity.getGeneralSpeed());
			cr.setActivities(new HashSet<ActivityDTO>(acrs));
			cr.setTimes(new HashSet<TimeActivityDTO>(tars));
			crs.add(cr);
		}
		return crs;
	}

	public static List<TimeActivityDTO> toResourcesFromTimeActivity(List<TimeActivity> tas) {
		List<TimeActivityDTO> trs = new ArrayList<>();
		for (TimeActivity entity : tas) {
			TimeActivityDTO tar = new TimeActivityDTO();
			Location location = new Location(0, 0);
			tar.uuid(entity.getUuid()).activityTitle(entity.getActivity().getTitle())
					.collectId(entity.getCollect().getUuid()).startDate(entity.getStartDate()).timed(entity.getTimed())
					.activityId(entity.getActivity().getUuid()).finalDate(entity.getFinalDate())
					.latitude(entity.getLocation().orElseGet(() -> location).getLatitude())
					.itemName(entity.getActivity().getItemName())
					.longitude(entity.getLocation().orElseGet(() -> location).getLongitude())
					.collectedAmount(entity.getCollectedAmount());
			tar.setActivityType(entity.getActivity().getActivityType().toString());

			// List<PictureResource> pics = new LinkedList<>();
			// for (TimeActivityPicture tap : entity.getPictures()) {
			// String uri = tap.getUri().replaceAll("file:", "");
			// PictureResource pr = new PictureResource();
			// pr.setUrl(uri);
			// pics.add(pr);
			// }
			// tar.setPics(pics);
			trs.add(tar);
		}

		return trs;
	}

	public static List<OperationDTO> mapEntitiesToResources(List<Operation> entities) {
		List<OperationDTO> resources = new LinkedList<>();
		for (Operation e : entities) {
			resources.add(mapEntityToResource(e, "0"));
		}
		return resources;
	}

}
