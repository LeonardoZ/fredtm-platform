package com.fredtm.api.unit;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import com.fredtm.api.resource.FredObjectMapper;
import com.fredtm.core.model.Operation;
import com.fredtm.resources.OperationResource;
import com.google.gson.Gson;

public class FredObjectMapperTest {

	@Test
	public void mapResourceToEntity() {
		String sb = readFromFile("testInsert.json");
		OperationResource fromJson = new Gson().fromJson(sb,
				OperationResource.class);

		FredObjectMapper fom = new FredObjectMapper();
		Operation operation = fom.mapResourceToEntity(fromJson);
		
		Assert.assertEquals(2, operation.getActivities().size());
		Assert.assertEquals(2, operation.getCollects().iterator().next().getActivities().size());
		Assert.assertEquals(2, operation.getCollects().iterator().next().getTimes().size());
	}

	protected String readFromFile(String filePath) {
		ClassLoader classLoader = getClass().getClassLoader();
		String result = "";
		try {
			result = IOUtils
					.toString(classLoader.getResourceAsStream(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}

}
