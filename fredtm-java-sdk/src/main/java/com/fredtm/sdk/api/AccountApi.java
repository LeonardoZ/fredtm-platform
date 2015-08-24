package com.fredtm.sdk.api;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

import com.fredtm.resources.AccountResource;
import com.fredtm.resources.AccountsResource;


public interface AccountApi {

	@POST("/account")
	public void createAccount(@Body AccountResource account,Callback<AccountResource> callback);

	@POST("/account/login")
	public void loginAccount(@Body AccountResource resource,Callback<AccountResource> callback);

	@GET("/account/{id}")
	public void getAccount(@Path(value = "id") String id,Callback<AccountResource> callback);

	@GET("/account/all")
	public void getAllAccounts(@Query("page") int page,
			@Query("elements") int elements,Callback<AccountsResource> callback);
}