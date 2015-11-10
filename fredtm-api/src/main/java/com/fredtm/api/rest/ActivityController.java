package com.fredtm.api.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fredtm.api.resource.ActivityResourceAssembler;
import com.fredtm.core.model.Activity;
import com.fredtm.data.repository.ActivityRepository;
import com.fredtm.resources.ActivitiesDTO;
import com.fredtm.resources.ActivityDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(consumes = "application/json", produces = "application/json", 
value = "Activities", description = "Activities Services")
@RequestMapping(value = "fredapi/activity")
public class ActivityController implements ResourcesUtil<Activity, ActivityDTO> {

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private ActivityResourceAssembler assembler;

	@ApiOperation(response = ActivityDTO.class, value = "Find Activity by ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "ActivityDTO", response = ActivityDTO.class),
			@ApiResponse(code = 404, message = "Activity Not Found") })
	@RequestMapping(method = RequestMethod.GET, value = "/{uuid}")
	public HttpEntity<Resource<ActivityDTO>> getActivity(
			@ApiParam(name = "Activity UUID", value = "Activity UUID", required = true) @PathVariable("uuid") String uuid) {

		Optional<Activity> activity = activityRepository.findByUuid(uuid);
		if (activity.isPresent()) {
			return createResponseEntity(activity.get(), HttpStatus.OK);
		} else {
			return createResponseHttp(HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Create Activity")
	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<Resource<ActivityDTO>> createActivity(
			@ApiParam(name = "Activity DTO", value = "The DTO with informations to be saved", required = true) @RequestBody ActivityDTO dto) {
		Optional<Activity> found = activityRepository.findByUuid(dto.getUuid());
		if (found.isPresent()) {
			return createResponseHttp(HttpStatus.CONFLICT);
		}
		Activity toBeSaved = assembler.fromResource(dto);
		return createResponseEntity(activityRepository.save(toBeSaved), HttpStatus.CREATED);
	}

	@ApiOperation(value = "Update Activity")
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public HttpEntity<Resource<ActivityDTO>> updateActivity(
			@ApiParam(name = "Activity DTO", value = "The DTO with informations to be updated", required = true) @RequestBody ActivityDTO dto) {
		Activity toUpdate = assembler.fromResource(dto);
		Activity saved = activityRepository.save(toUpdate);
		return createResponseEntity(saved, HttpStatus.OK);
	}

	@ApiOperation(value = "Remove Activity")
	@ApiResponses({ @ApiResponse(code = 200, message = "Activity removed", response = ActivityDTO.class),
			@ApiResponse(code = 304, message = "Activity not removed") })
	@RequestMapping(value = "/{activityUuid}", method = RequestMethod.DELETE)
	public HttpStatus removeActivity(

	@ApiParam(name = "Activity UUID", value = "UUID from Activity", required = true) @PathVariable("activityUuid") String uuid) {
		try {
			Optional<Activity> activity = activityRepository.findByUuid(uuid);
			activityRepository.delete(activity.get());
			return HttpStatus.OK;
		} catch (Exception ex) {
			return HttpStatus.NOT_MODIFIED;
		}
	}

	@ApiOperation(value = "Finds Activities by Operation UUID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Activities from operation", response = ActivitiesDTO.class),
			@ApiResponse(code = 404, message = "Activities Not Found") })
	@RequestMapping(method = RequestMethod.GET, value = "/by/operation/{uuid}")

	public HttpEntity<Resources<Resource<ActivityDTO>>> getActivitiesBy(
			@ApiParam(name = "Operations UUID", value = "UUID", required = true) @PathVariable("uuid") String operationUuid) {
		List<Activity> found = activityRepository.findAllByOperationUuid(operationUuid);
		if (found.isEmpty()) {
			return new ResponseEntity<Resources<Resource<ActivityDTO>>>(HttpStatus.NO_CONTENT);
		} else {
			List<Resource<ActivityDTO>> resources = configureResources(found);
			Resources<Resource<ActivityDTO>> r = new Resources<>(resources);
			return new ResponseEntity<>(r, HttpStatus.OK);
		}
	}

	@Override
	public Resource<ActivityDTO> configureResource(Activity e) {
		ActivityDTO dto = assembler.toResource(e);
		Link self = linkTo(ActivityController.class).slash(e.getUuid()).withSelfRel();
		Resource<ActivityDTO> result = new Resource<>(dto, self);
		return result;
	}

}
