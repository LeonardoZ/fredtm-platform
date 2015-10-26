package com.fredtm.api.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fredtm.api.resource.TimeActivityResourceAssembler;
import com.fredtm.core.model.TimeActivity;
import com.fredtm.data.repository.TimeActivityRepository;
import com.fredtm.resources.TimeActivityDTO;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@RestController
@Api(consumes = "application/json", produces = "applicaiton/json", value = "time", description = "Time methods")
@RequestMapping(value = "fredapi/time/times")
public class TimeActivityController implements ResourcesUtil<TimeActivity, TimeActivityDTO> {

	@Autowired
	private TimeActivityRepository timeRepository;

	@Autowired
	private TimeActivityResourceAssembler timeAssembler;

	@ApiOperation(value = "Add Time to TimeActivity")
	@ApiResponses({ @ApiResponse(code = 200, message = "TimeActivityDTO", response = TimeActivityDTO.class),
			@ApiResponse(code = 406, message = "Already existing Time send to be created.") })
	@RequestMapping(value = "/time", method = RequestMethod.PUT)
	public ResponseEntity<Resource<TimeActivityDTO>> addTimeActivity(TimeActivityDTO dto) {
		Optional<TimeActivity> found = timeRepository.findByUuid(dto.getUuid());
		if (found.isPresent()) {
			return createResponseHttp(HttpStatus.CONFLICT);
		}
		TimeActivity timeActivity = timeAssembler.fromResource(dto);
		TimeActivity saved = timeRepository.save(timeActivity);
		return createResponseEntity(saved, HttpStatus.CREATED);

	}

	@ApiOperation(value = "Remove time")
	@ApiResponses({ @ApiResponse(code = 200, message = "Time deleted", response = TimeActivityDTO.class),
			@ApiResponse(code = 304, message = "Time not deleted") })
	@RequestMapping(value = "/{timeUuid}", method = RequestMethod.DELETE)
	public HttpStatus removeTime(
			@ApiParam(name = "Time UUID", value = "The Time  UUID to be removed", required = true) @PathVariable("timeUuid") String uuid) {
		try {
			Optional<TimeActivity> time = timeRepository.findByUuid(uuid);
			timeRepository.delete(time.get());
			return HttpStatus.OK;
		} catch (Exception ex) {
			return HttpStatus.NOT_MODIFIED;
		}
	}

	@Override
	public Resource<TimeActivityDTO> configureResource(TimeActivity e) {
		return null;
	}

}
