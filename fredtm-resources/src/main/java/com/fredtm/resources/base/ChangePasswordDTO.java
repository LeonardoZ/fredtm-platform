package com.fredtm.resources.base;

public class ChangePasswordDTO {

	private String  newPassword, token;

	public ChangePasswordDTO() {

	}

	public ChangePasswordDTO(String newPassword, String token) {
		super();
		this.newPassword = newPassword;
		this.token = token;
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
