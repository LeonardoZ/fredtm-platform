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

import com.fredtm.api.resource.CollectResourceAssembler;
import com.fredtm.core.model.Collect;
import com.fredtm.data.repository.CollectRepository;
import com.fredtm.resources.CollectDTO;
import com.fredtm.resources.CollectsDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(consumes = "application/json", 
produces = "application/json", 
value = "Collects", description = "Collects Services")
@RequestMapping(value = "fredapi/collect")
public class CollectController implements ResourcesUtil<Collect, CollectDTO> {

	@Autowired
	private CollectRepository collectRepository;

	@Autowired
	private CollectResourceAssembler assembler;

	@ApiOperation(response = CollectDTO.class, value = "Find Collect by UUID")
	@ApiResponses({ @ApiResponse(code = 200, message = "CollectDTO", response = CollectDTO.class),
			@ApiResponse(code = 404, message = "Collect Not Found") })
	@RequestMapping(method = RequestMethod.GET, value = "/{uuid}")
	public HttpEntity<Resource<CollectDTO>> getCollect(
			@ApiParam(name = "Collect UUID", value = "The UUID from Collect to be viewed", required = true) @PathVariable("uuid") String uuid) {
		Optional<Collect> collect = collectRepository.findByUuid(uuid);
		if (collect.isPresent()) {
			return createResponseEntity(collect.get(), HttpStatus.OK);
		} else {
			return createResponseHttp(HttpStatus.NOT_FOUND);
		}

	}

	@ApiOperation(value = "Create Collect")
	@ApiResponses({ @ApiResponse(code = 200, message = "CollectDTO", response = CollectDTO.class),
		@ApiResponse(code = 406, message = "Collect sent to be created.") })
	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<Resource<CollectDTO>> createCollect(
			@ApiParam(name = "Collect DTO", value = "The DTO with informations to be saved", required = true) @RequestBody CollectDTO dto) {
		Optional<Collect> found = collectRepository.findByUuid(dto.getUuid());
		if (found.isPresent()) {
			return createResponseHttp(HttpStatus.CONFLICT);
		}
		Collect toBeSaved = assembler.fromResource(dto);
		return createResponseEntity(collectRepository.save(toBeSaved), HttpStatus.CREATED);
	}

	@ApiOperation(value = "Update Collect")
	@ApiResponses({ @ApiResponse(code = 200, message = "CollectDTO", response = CollectDTO.class),
		@ApiResponse(code = 400, message = "Collect with wrong data send to be updated.") })
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public HttpEntity<Resource<CollectDTO>> updateCollect(
			@ApiParam(name = "Collect DTO", value = "The DTO with informations to be updated", required = true) @RequestBody CollectDTO dto) {
		Collect toUpdate = assembler.fromResource(dto);
		Collect saved = collectRepository.save(toUpdate);
		return createResponseEntity(saved, HttpStatus.OK);
	}

	@ApiOperation(value = "Remove Collect")
	@ApiResponses({ @ApiResponse(code = 200, message = "Collect removed", response = CollectDTO.class),
			@ApiResponse(code = 304, message = "Collect not removed") })
	@RequestMapping(value = "/{collectUuid}", method = RequestMethod.DELETE)
	public HttpStatus removeCollect(
			@ApiParam(name = "Collect UUID", value = "The Collect UUID", required = true) @PathVariable("collectUuid") String uuid) {
		try {
			Optional<Collect> collect = collectRepository.findByUuid(uuid);
			collectRepository.delete(collect.get());
			return HttpStatus.OK;
		} catch (Exception ex) {
			return HttpStatus.NOT_MODIFIED;
		}
	}

	@ApiOperation(value = "Finds Collects by Operation UUID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Collects from Operation", response = CollectsDTO.class),
			@ApiResponse(code = 404, message = "Collects Not Found") })
	@RequestMapping(method = RequestMethod.GET, value = "/by/operation/{uuid}")
	public HttpEntity<Resources<Resource<CollectDTO>>> getCollectsBy(
			@ApiParam(name = "Operation UUID", value = "The Operation UUID", required = true) @PathVariable("uuid") String operationUuid) {
		List<Collect> found = collectRepository.findAllByOperationUuid(operationUuid);
		if (found.isEmpty()) {
			return new ResponseEntity<Resources<Resource<CollectDTO>>>(HttpStatus.NO_CONTENT);
		} else {
			List<Resource<CollectDTO>> resources = configureResources(found);
			Resources<Resource<CollectDTO>> r = new Resources<>(resources);
			return new ResponseEntity<>(r, HttpStatus.OK);
		}
	}

	@Override
	public Resource<CollectDTO> configureResource(Collect e) {
		CollectDTO dto = assembler.toResource(e);
		Link self = linkTo(CollectController.class).slash(e.getUuid()).withSelfRel();
		Resource<CollectDTO> result = new Resource<>(dto, self);
		return result;
	}

}
