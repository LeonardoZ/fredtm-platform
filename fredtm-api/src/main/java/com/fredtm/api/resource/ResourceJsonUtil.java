package com.fredtm.api.resource;

import java.lang.reflect.Type;
import java.util.HashSet;

import com.fredtm.resources.ActivityDTO;
import com.fredtm.resources.OperationDTO;
import com.fredtm.resources.base.GsonFactory;
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
		String json = GsonFactory.getGson().toJson(t);
		return json;
	}

	class des implements JsonDeserializer<OperationDTO> {
		@Override
		public OperationDTO deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			OperationDTO res = gson.fromJson(json, typeOfT);
			JsonElement jsonElement = json.getAsJsonObject().get("activities");
			if (jsonElement.isJsonArray()) {
				Type collectionType = new TypeToken<HashSet<ActivityDTO>>() {
				}.getType();
				HashSet<ActivityDTO> acts = gson.fromJson(jsonElement, collectionType);
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
