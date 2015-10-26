package com.fredtm.resources;

public class ChangeToken {
	private String code;
	private String email;
	private String jwt;

	public ChangeToken() {

	}
	
	public ChangeToken(String code, String email, String jwt) {
		super();
		this.code = code;
		this.email = email;
		this.jwt = jwt;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJwt() {
		return this.jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

}