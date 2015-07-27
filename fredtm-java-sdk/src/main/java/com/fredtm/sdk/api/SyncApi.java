package com.fredtm.sdk.api;

import java.util.Set;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

import com.fredtm.resources.OperationResource;
import com.fredtm.resources.OperationsResource;
import com.fredtm.resources.SyncResource;

public interface SyncApi {

	@POST("/sync")
	public void sendSyncs(@Body OperationResource operation,
			Callback<SyncResource> callback);

	@GET("/sync/{accountId}")
	public void receiveSyncs(@Path("accountId") String accountId,
			Callback<OperationsResource> callback);

	@GET("/sync/{accountId}")
	public void getSyncs(@Path("accountId") String accountId,
			Callback<Set<SyncResource>> callback);

}
