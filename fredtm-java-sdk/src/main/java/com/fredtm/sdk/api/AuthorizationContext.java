package com.fredtm.sdk.api;

import com.fredtm.resources.security.LoginDTO;

public class AuthorizationContext {
	
	private AccountApi annonymousAccocuntApi;

	public String getToken(LoginDTO dto){
		return new LoginRequestor(dto).requestLoginToken(annonymousAccocuntApi);
	}

	void setAccountApi(AccountApi annonymousAcocuntApi){
		this.annonymousAccocuntApi = annonymousAcocuntApi;
	}

	public AccountApi getAnnonymousAcocuntApi() {
		return this.annonymousAccocuntApi;
	}
	
}
