package com.fredtm.client.api;

import java.util.Set;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

import com.fredtm.resources.dto.AccountDTO;


public interface AccountApi {

	@POST("/account")
	public void createAccount(@Body AccountDTO account,Callback<AccountDTO> callback);

	@POST("/account/login")
	public void loginAccount(@Body AccountDTO resource,Callback<AccountDTO> callback);

	@GET("/account/{id}")
	public void getAccount(@Path(value = "id") String id,Callback<AccountDTO> callback);

	@GET("/account/all")
	public void getAllAccounts(@Query("page") int page,
			@Query("elements") int elements,Callback<Set<AccountDTO>> callback);
}
