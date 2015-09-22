package com.fredtm.sdk.api;

import java.util.Set;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

import com.fredtm.resources.OperationDTO;
import com.fredtm.resources.OperationsDTO;
import com.fredtm.resources.SyncDTO;

public interface SyncApi {

	@POST("/sync")
	public void sendSyncs(@Body OperationDTO operation,
			Callback<SyncDTO> callback);

	@GET("/sync/{accountId}")
	public void receiveSyncs(@Path("accountId") String accountId,
			Callback<OperationsDTO> callback);

	@GET("/sync/{accountId}")
	public void getSyncs(@Path("accountId") String accountId,
			Callback<Set<SyncDTO>> callback);

}
