
package com.fredtm.resources.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {

	public static Gson getGson() {
		return new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
	}

}
