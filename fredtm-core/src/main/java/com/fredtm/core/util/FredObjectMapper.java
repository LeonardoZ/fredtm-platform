package com.fredtm.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fredtm.core.model.Activity;
import com.fredtm.core.model.ActivityType;
import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Location;
import com.fredtm.core.model.Operation;
import com.fredtm.core.model.Sync;
import com.fredtm.core.model.TimeActivity;
import com.fredtm.core.model.TimeActivityPicture;
import com.fredtm.resources.ActivityResource;
import com.fredtm.resources.CollectResource;
import com.fredtm.resources.OperationResource;
import com.fredtm.resources.SyncResource;
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
					ActivityType.getById(activityResource.getActivityType()).orElse(ActivityType.PRODUCTIVE));
			activity.setItemName(activityResource.getItemName());
			activity.setIsQuantitative(activityResource.getQuantitative());
			activity.setOperation(operation);
			operation.addActivity(activity);
		}

		List<CollectResource> collectResources = operationResource.getCollects();
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

		return operation;
	}

	public static List<Operation> mapResourcesToEntities(List<OperationResource> resources) {
		return resources.stream().map(FredObjectMapper::mapResourceToEntity).collect(Collectors.toList());
	}

	public static OperationResource mapEntityToResource(Operation entity, String accUuid) {

		return new OperationResource().uuid(entity.getId()).name(entity.getName())
				.technicalCharacteristics(entity.getTechnicalCharacteristics()).company(entity.getCompany())
				.modification(entity.getModified())
				.accountId(entity.getAccount() != null ? entity.getAccount().getId() : null)
				.activities(toResourcesAct(entity.getActivities())).collects(toResourcesCol(entity.getCollects()))
				.lastSync(toResource(entity.getLastSync()));

	}

	private static SyncResource toResource(Sync entity) {
		SyncResource sr = new SyncResource();
		if (entity != null) {
			sr.uuid(entity.getId()).created(entity.getCreated()).operationId(entity.getOperation().getId());
		}
		return sr;
	}

	private static Set<ActivityResource> toResourcesAct(Collection<Activity> set) {
		Set<ActivityResource> crs = new HashSet<>();
		for (Activity entity : set) {
			ActivityResource ar = new ActivityResource();
			ar.title(entity.getTitle()).description(entity.getDescription())
					.activityType(entity.getActivityType().getActivityType()).itemName(entity.getItemName())
					.quantitative(entity.isQuantitative()).operationId(entity.getOperation().getId());
			crs.add(ar);
		}
		return crs;
	}

	private static List<CollectResource> toResourcesCol(List<Collect> cols) {
		List<CollectResource> crs = new LinkedList<>();
		for (Collect entity : cols) {
			CollectResource cr = new CollectResource();
			List<Activity> activities = entity.getActivities();
			List<ActivityResource> acrs = new ArrayList<>(toResourcesAct(activities));
			List<TimeActivity> times = entity.getTimes();
			List<TimeActivityResource> tars = toResourcesFromTimeActivity(times);

			cr.setOperationId(entity.getOperation().getId());
			cr.setActivities(new HashSet<ActivityResource>(acrs));
			cr.setTimes(new HashSet<TimeActivityResource>(tars));
			crs.add(cr);
		}
		return crs;
	}

	public static List<TimeActivityResource> toResourcesFromTimeActivity(List<TimeActivity> tas) {
		List<TimeActivityResource> trs = new ArrayList<>();
		for (TimeActivity entity : tas) {
			TimeActivityResource tar = new TimeActivityResource();
			Location location = new Location(0, 0);
			tar.uuid(entity.getId()).activityTitle(entity.getActivity().getTitle())
					.collectId(entity.getCollect().getId()).startDate(entity.getStartDate()).timed(entity.getTimed())
					.activityId(entity.getActivity().getId()).finalDate(entity.getFinalDate())
					.latitude(entity.getLocation().orElseGet(() -> location).getLatitude())
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

	public static List<OperationResource> mapEntitiesToResources(List<Operation> entities) {
		List<OperationResource> resources = new LinkedList<>();
		for (Operation e : entities) {
			resources.add(mapEntityToResource(e, "0"));
		}
		return resources;
	}

}
