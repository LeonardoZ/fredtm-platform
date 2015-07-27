package com.fredtm.api.integration;

import org.junit.Assert;
import org.junit.Test;

import com.fredtm.api.test.TestBase;
import com.fredtm.resources.OperationResource;
import com.google.gson.GsonBuilder;

public class OperationControllerTest extends TestBase {

	@Test
	public void doTest() {
		String json = makeRequest().and().given()
				.pathParam("id", "fd806586-35d4-4e8c-a119-935d4bea0773")
				.get("/operation/{id}/full").andReturn()
				.body().prettyPrint();
		
		GsonBuilder gbuilder = new GsonBuilder().setDateFormat("dd/MM/yyyy hh:MM:ss");
		OperationResource fromJson = gbuilder.create().fromJson(json, OperationResource.class);
		Assert.assertEquals(fromJson.getUuid(),
				"fd806586-35d4-4e8c-a119-935d4bea0773");
	}

}
