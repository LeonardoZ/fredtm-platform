package com.fredtm.sdk.api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

import com.fredtm.resources.OperationDTO;

public interface OperationApi {

	@GET("/operation/{id}")
	public void getOperation(@Path("id") String id,
			Callback<OperationDTO> callback);

}
