package com.fredtm.client.api;



import org.apache.commons.codec.binary.Base64;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

public class FredTmApi {

	private final String email, password;
	private String encodedCredentials = "";
	private String endpoint;

	public FredTmApi(String endpoint,String email, String password) {
		super();

		this.endpoint = endpoint;
		this.email = email;
		this.password = password;
	}

	public FredTmApi(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	
	public RestAdapter configAdapter() {
		return new RestAdapter.Builder()
				.setLogLevel(LogLevel.FULL)
				.setRequestInterceptor(new RequestInterceptor() {
					
					@Override
					public void intercept(RequestFacade request) {
						String authorization = encodeCredentialsForBasicAuthorization();
						request.addHeader("Authorization", authorization);
						
					}
				})
				.setEndpoint(endpoint == null ? "http://fredtm-api.herokuapp.com/fredapi" : endpoint).build();
	}

	private String encodeCredentialsForBasicAuthorization() {
		if (encodedCredentials.isEmpty()) {
			final String userAndPassword = email + ":" + password;
			encodedCredentials = "Basic "
					+ new String(Base64.encodeBase64(
							userAndPassword.getBytes())).intern();
		}
		return encodedCredentials;
	}

	public <T> T get(Class<T> apiDef) {
		return configAdapter().create(apiDef);
	}

}
