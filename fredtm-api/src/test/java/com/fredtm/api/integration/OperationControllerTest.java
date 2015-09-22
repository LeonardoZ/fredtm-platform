package com.fredtm.api.integration;

import org.junit.Assert;
import org.junit.Test;

import com.fredtm.api.test.TestBase;
import com.fredtm.resources.OperationDTO;
import com.fredtm.resources.base.GsonFactory;

public class OperationControllerTest extends TestBase {

	@Test
	public void doTest() {
		String json = makeRequest().and().given()
				.pathParam("id", "29c31a3d-97a7-439e-b201-ce80b716dfc6").get("/operation/{id}/full")
				.andReturn().body()
				.prettyPrint();

		OperationDTO fromJson = GsonFactory.getGson().fromJson(json, OperationDTO.class);
		Assert.assertEquals("29c31a3d-97a7-439e-b201-ce80b716dfc6", fromJson.getUuid());
	}

}
