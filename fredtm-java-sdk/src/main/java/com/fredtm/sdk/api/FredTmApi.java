package com.fredtm.sdk.api;



import org.apache.commons.codec.binary.Base64;

import com.fredtm.resources.base.GsonFactory;
import com.google.gson.Gson;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.RestAdapter.LogLevel;
import retrofit.converter.GsonConverter;

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

	public RestAdapter configCompleteAdapter(){
		return configAdapter(true);
	}
	
	public RestAdapter configAnnonymousAdapter(){
		return configAdapter(false);
	}
	
	private RestAdapter configAdapter(boolean complete) {
		Gson g = GsonFactory.getGson();
		Builder builder = new RestAdapter.Builder()
				.setLogLevel(LogLevel.FULL)
				.setConverter(new GsonConverter(g))
				.setEndpoint(endpoint == null ? "http://fredtm-api.herokuapp.com/fredapi" : endpoint);
				if(complete){
					builder.setRequestInterceptor(new RequestInterceptor() {
						@Override
						public void intercept(RequestFacade request) {
							String authorization = encodeCredentialsForBasicAuthorization();
							request.addHeader("Authorization", authorization);
						}
					});
				}
				return builder.build();
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

	public <T> T getComplete(Class<T> apiDef) {
		return configCompleteAdapter().create(apiDef);
	}
	public <T> T getAnnonymous(Class<T> apiDef) {
		return configAnnonymousAdapter().create(apiDef);
	}

}
