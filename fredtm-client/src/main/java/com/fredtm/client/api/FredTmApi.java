package com.fredtm.client.api;

import java.util.Base64;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

public class FredTmApi {

	private final String email, password;
	private String encodedCredentials = "";

	public FredTmApi(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public RestAdapter configAdapter() {
		return new RestAdapter.Builder()
		.setLogLevel(LogLevel.FULL)
		.setRequestInterceptor(rf -> {

			String authorization = encodeCredentialsForBasicAuthorization();
			rf.addHeader("Authorization", authorization);
		}).setEndpoint("http://fredtm-api.herokuapp.com/fredapi").build();
	}

	private String encodeCredentialsForBasicAuthorization() {
		if (encodedCredentials.isEmpty()) {
			final String userAndPassword = email + ":" + password;
			encodedCredentials = "Basic "
					+ new String(Base64.getEncoder().encode(userAndPassword.getBytes())).intern();
		}
		return encodedCredentials;
	}

	public <T> T get(Class<T> apiDef) {
		return configAdapter().create(apiDef);
	}

}
