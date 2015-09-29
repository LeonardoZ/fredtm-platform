package com.fredtm.api.integration;

import org.junit.Assert;
import org.junit.Test;

import com.fredtm.api.test.TestBase;
import com.fredtm.resources.OperationDTO;
import com.fredtm.resources.OperationsDTO;
import com.fredtm.resources.base.GsonFactory;

public class OperationControllerTest extends TestBase {

	@Test
	public void doTest() {
		String json = makeRequest().and().given().pathParam("id", "29c31a3d-97a7-439e-b201-ce80b716dfc6")
				.get("/operation/{id}/full").andReturn().body().prettyPrint();

		OperationDTO fromJson = GsonFactory.getGson().fromJson(json, OperationDTO.class);
		Assert.assertEquals("29c31a3d-97a7-439e-b201-ce80b716dfc6", fromJson.getUuid());
	}

	@Test
	public void doDeleteTest() {
		int statusCode = makeRequest().and().given().pathParam("id", "29c31a3d-97a7-439e-b201-ce80b716dfc6")
				.delete("/operation/{id}").andReturn().statusCode();
		Assert.assertEquals(200, statusCode);
	}

	@Test
	public void doInsertTest() {
		int statusCode = makeRequest().and().given().pathParam("id", "29c31a3d-97a7-439e-b201-ce80b716dfc6")
				.delete("/operation/{id}").andReturn().statusCode();
		Assert.assertEquals(200, statusCode);
	}

	@Test
	public void doUpdateTest() {
		int statusCode = makeRequest().and().given().pathParam("id", "29c31a3d-97a7-439e-b201-ce80b716dfc6")
				.delete("/operation/{id}").andReturn().statusCode();
		Assert.assertEquals(200, statusCode);
	}

	@Test
	public void doPaginationTest() {
		String json = makeRequest().and().given().queryParam("page", 0).queryParam("size", 5)
				.get("/operation/{accUuid}/all", "23ca7484-9126-4eeb-91c7-262197aaef46").andReturn().body()
				.prettyPrint();

		OperationsDTO fromJson = GsonFactory.getGson().fromJson(json, OperationsDTO.class);
		Assert.assertEquals(1, fromJson.getPage().getTotalElements());
		Assert.assertEquals(5, fromJson.getPage().getSize());
		Assert.assertEquals(1, fromJson.getEmbedded().getOperationDTOList().size());
	}

}
