package com.fredtm.resources.security;

public class LoginDTO {

	private String password, email;

	public LoginDTO() {
	}

	public LoginDTO(String password, String email) {
		super();
		this.password = password;
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LoginDTO email(String email) {
		this.email = email;
		return this;
	}

	public LoginDTO password(String password) {
		this.password = password;
		return this;
	}
}