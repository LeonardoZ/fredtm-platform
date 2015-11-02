package com.fredtm.sdk.api;

import com.fredtm.resources.OperationDTO;

import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Path;

public interface OperationApi {

	@GET("/operation/{uuid}")
	public void getOperation(@Path("uuid") String uuid,
			Callback<OperationDTO> callback);
	

	@DELETE("/operation/{uuid}")
	public void deleteOperation(@Path("uuid") String uuid,
			Callback<String> callback);

}
