package com.fredtm.resources.base;

public class ChangePasswordDTO {

	private String email, newPassword, token;

	public ChangePasswordDTO() {

	}

	public ChangePasswordDTO(String email, String newPassword, String token) {
		super();
		this.email = email;
		this.newPassword = newPassword;
		this.token = token;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNewPassword() {
		return this.newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
