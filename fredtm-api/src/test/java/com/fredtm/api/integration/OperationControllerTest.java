package com.fredtm.api.integration;

import org.junit.Assert;
import org.junit.Test;

import com.fredtm.api.test.TestBase;
import com.fredtm.resources.OperationResource;
import com.fredtm.resources.base.GsonFactory;

public class OperationControllerTest extends TestBase {

	@Test
	public void doTest() {
		String json = makeRequest().and().given()
				.pathParam("id", "fd806586-35d4-4e8c-a119-935d4bea0773")
				.get("/operation/{id}/full").andReturn()
				.body().prettyPrint();
		
		OperationResource fromJson = GsonFactory.getGson().fromJson(json, OperationResource.class);
		Assert.assertEquals(fromJson.getUuid(),
				"fd806586-35d4-4e8c-a119-935d4bea0773");
	}

}
