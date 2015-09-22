package com.fredtm.sdk.api;

import com.fredtm.resources.security.LoginDTO;
import com.fredtm.resources.security.LoginResponse;

public class LoginRequestor {

	private LoginDTO dto;

	public LoginRequestor(LoginDTO dto) {
		this.dto = dto;
	}

	public String requestLoginToken(AccountApi annonymousAccountApi) {
		LoginResponse loginAccount = annonymousAccountApi.loginAccount(dto);
		return "Bearer " + loginAccount.getToken();
	}

}
