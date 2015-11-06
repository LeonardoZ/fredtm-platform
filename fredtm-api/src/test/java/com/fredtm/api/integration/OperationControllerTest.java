package com.fredtm.api.integration;

import org.junit.Assert;
import org.junit.Test;

import com.fredtm.api.test.BaseTest;
import com.fredtm.resources.OperationDTO;
import com.fredtm.resources.OperationsDTO;
import com.fredtm.resources.base.GsonFactory;

public class OperationControllerTest extends BaseTest {

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
		OperationDTO operationDTO = new OperationDTO()
				.accountId("48493871-920d-43b9-bdd0-5f804d1b9be4")
				.name("For test")
				.technicalCharacteristics("Tech description")
				.company("My company");

		String json = GsonFactory.getGson().toJson(operationDTO);

		String body = makeContentRequest().and().log().all()
				.given().content(json)
				.post("/operation")
				.andReturn()
				.body()
				.asString();

		OperationDTO returned = GsonFactory.getGson().fromJson(body, OperationDTO.class);

		int statusCode = makeRequest().and().log().all().given()
				.get("/operation/{id}", returned.getUuid())
				.andReturn()
				.statusCode();

		Assert.assertEquals(200, statusCode);
	}

	@Test
	public void doUpdateTest() {
		String body = makeRequest().and().given()
				.get("/operation/{id}", "29c31a3d-97a7-439e-b201-ce80b716dfc6").andReturn().body().asString();

		OperationDTO operationDTO = GsonFactory.getGson().fromJson(body, OperationDTO.class);
		operationDTO.setTechnicalCharacteristics("WOLOLO");

		String json = GsonFactory.getGson().toJson(operationDTO);
		String body2 = makeContentRequest().and().given().log().all().content(json).put("/operation").andReturn().body().asString();
		
		System.out.println("OperationControllerTest.doUpdateTest()");
		System.out.println(body2);
		OperationDTO returned = GsonFactory.getGson().fromJson(body2, OperationDTO.class);

		Assert.assertEquals("WOLOLO", returned.getTechnicalCharacteristics());
		Assert.assertTrue(returned.getModification().after(operationDTO.getModification()));
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
