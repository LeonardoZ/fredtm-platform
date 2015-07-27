package com.fredtm.api.resource;

import java.lang.reflect.Type;
import java.util.HashSet;

import com.fredtm.resources.ActivityResource;
import com.fredtm.resources.OperationResource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class ResourceJsonUtil {

	private Gson gson = new Gson();

	public <T> String toJson(T t) {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		String json = builder.create().toJson(t);
		return json;
	}

	class des implements JsonDeserializer<OperationResource> {
		@Override
		public OperationResource deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			OperationResource res = gson.fromJson(json, typeOfT);
			JsonElement jsonElement = json.getAsJsonObject().get("activities");
			if (jsonElement.isJsonArray()) {
				Type collectionType = new TypeToken<HashSet<ActivityResource>>() {
				}.getType();
				HashSet<ActivityResource> acts = gson.fromJson(jsonElement, collectionType);
				res.activities(acts);
			}

			return res;
		}
	}

	public <D> D fromJson(String json, Class<D> d) {

		GsonBuilder builder = null;
		try {
			builder = new GsonBuilder().setPrettyPrinting();
		} catch (Exception e) {
			e.printStackTrace();
		}

		D entity = builder.create().fromJson(json, d);
		return entity;
	}

}
