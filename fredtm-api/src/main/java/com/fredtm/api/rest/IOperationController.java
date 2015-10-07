package com.fredtm.api.rest;

import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fredtm.resources.OperationDTO;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;


public interface IOperationController {

	@ApiOperation(value = "Get Paginated operations")
	HttpEntity<PagedResources<Resource<OperationDTO>>> getOperationsBy(
			@ApiParam(name = "Account UUID", value = "The Account UUID of the user", required = true) String accUuid,
			@ApiParam(name = "Page", value = "The page to be retrivied") int page,
			@ApiParam(name = "Size", value = "The size of operations to be retrieved") int size);

	@ApiOperation(value = "Update operation")
	HttpEntity<Resource<OperationDTO>> updateOperation(
			@ApiParam(name = "Operation DTO", value = "The DTO containing information to be updated", required = true) OperationDTO dto);

	@ApiOperation(value = "Create operation")
	HttpEntity<Resource<OperationDTO>> createOperation(
			@ApiParam(name = "Operation DTO", value = "The DTO containing information to be saved", required = true) OperationDTO dto);

	@ApiOperation(value = "Remove operation")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Operation deleted", response = OperationDTO.class),
			@ApiResponse(code = 304, message = "Operation not deleted") })
	HttpStatus removeOperation(
			@ApiParam(name = "Operation UUID", value = "The UUID of the operation to be removed", required = true) String uuid);

	
	@ApiOperation(value = "View all info of the operation")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "OperationDTO", response = OperationDTO.class),
			@ApiResponse(code = 404, message = "Operation Not Found") })
	HttpEntity<Resource<OperationDTO>> getOperationFull(
			@ApiParam(name = "Operation UUID", value = "The UUID of the operation to be viewed", required = true) String uuid);

	
	@ApiOperation(value = "View the specific info of the operation")
	@ApiResponses({ @ApiResponse(code = 200, message = "OperationDTO", response = OperationDTO.class),
			@ApiResponse(code = 404, message = "Operation Not Found") })
	HttpEntity<Resource<OperationDTO>> getOperation(
			@ApiParam(name = "Operation UUID", value = "The UUID of the operation to be viewed", required = true) String uuid);
		
}
