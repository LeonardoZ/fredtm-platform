package com.fredtm.resources.security;

public class LoginResponse {

	private String accountUuid;
	private String token;

	public LoginResponse() {

	}

	public LoginResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getAccountUuid() {
		return this.accountUuid;
	}
	
	public void setAccountUuid(String accountUuid) {
		this.accountUuid = accountUuid;
	}

}
