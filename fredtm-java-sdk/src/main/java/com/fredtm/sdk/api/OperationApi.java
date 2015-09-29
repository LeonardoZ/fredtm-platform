package com.fredtm.sdk.api;

import com.fredtm.resources.OperationDTO;

import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Path;

public interface OperationApi {

	@GET("/operation/{id}")
	public void getOperation(@Path("id") String id,
			Callback<OperationDTO> callback);
	

	@DELETE("/operation/{id}")
	public void deleteOperation(@Path("uuid") String uuid,
			Callback<Integer> callback);

}
