package com.fredtm.client.api;

import java.util.Set;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

import com.fredtm.resources.dto.OperationDTO;
import com.fredtm.resources.dto.SyncDTO;

public interface SyncApi {

	@POST("/sync")
	public void receiveSync(@Body OperationDTO operation,
			Callback<SyncDTO> callback);

	@GET("/sync/{accountId}")
	public void getSyncs(@Path("accountId") String accountId,
			Callback<Set<SyncDTO>> callback);

}
