package com.fredtm.sdk.api;

import com.fredtm.resources.AccountDTO;
import com.fredtm.resources.SendAccountDTO;
import com.fredtm.resources.security.LoginDTO;
import com.fredtm.resources.security.LoginResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;


public interface AccountApi {

	@POST("/account")
	public void createAccount(@Body SendAccountDTO account,Callback<AccountDTO> callback);

	@POST("/account/login")
	public void loginAccount(@Body LoginDTO resource, Callback<LoginResponse> callback);
	
	@POST("/account/login")
	public LoginResponse loginAccount(@Body LoginDTO response);

	@GET("/account/{id}")
	public void getAccount(@Path(value = "id") String id,Callback<AccountDTO> callback);

	@GET("/account/{id}")
	public AccountDTO getAccount(@Path(value = "id") String id);

	
//	@GET("/account/all")
//	public void getAllAccounts(@Query("page") int page,
//			@Query("elements") int elements,Callback<AccountsResource> callback);
}
