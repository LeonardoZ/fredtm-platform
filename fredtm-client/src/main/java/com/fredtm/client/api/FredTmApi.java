package com.fredtm.client.api;

import retrofit.RestAdapter;

public class FredTmApi {

	public RestAdapter configAdapter() {
		return new RestAdapter.Builder().setEndpoint(
				"https://localhost:9000/fredapi").build();
	}

	public <T> T build(Class<T> apiDef) {
		return configAdapter().create(apiDef);
	}

}
