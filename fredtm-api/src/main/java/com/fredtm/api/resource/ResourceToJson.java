package com.fredtm.api.resource;

import com.google.gson.Gson;

public class ResourceToJson {

	private Gson gson = new Gson();
	
	 public <T> String toJson(T t) {
		 String json = gson.toJson(t);
		 return json;
	}
	
}
