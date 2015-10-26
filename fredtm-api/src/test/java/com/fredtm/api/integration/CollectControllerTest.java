package com.fredtm.api.integration;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import com.fredtm.api.test.TestBase;
import com.fredtm.resources.ActivityDTO;
import com.fredtm.resources.CollectDTO;
import com.fredtm.resources.CollectsDTO;
import com.fredtm.resources.OperationDTO;
import com.fredtm.resources.base.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@ActiveProfiles(value = "test")
public class CollectControllerTest extends TestBase {

	@Test
	public void doAllGetTest() {
		String body = makeRequest().and().log().all().given()
				.get("/collect/by/operation/{uuid}", "29c31a3d-97a7-439e-b201-ce80b716dfc6").andReturn().body()
				.asString();
		CollectsDTO fromJson = GsonFactory.getGson().fromJson(body, CollectsDTO.class);

		Assert.assertEquals(1, fromJson.getEmbedded().getCollectDTOList().size());

	}

	@Test
	public void doGetTest() {
		int statusCode = makeRequest().and().log().all().given()
				.get("/collect/{uuid}", "4d28f6f6-eeb3-4f79-848a-5926e40fba8c").andReturn().statusCode();

		Assert.assertEquals(200, statusCode);

	}

	@Test
	public void doDeleteTest() {
		int statusCode = makeRequest().and().given().pathParam("id", "29c31a3d-97a7-439e-b201-ce80b716dfc6")
				.delete("/collect/{id}").andReturn().statusCode();
		Assert.assertEquals(200, statusCode);
	}

	@Test
	public void doInsertTest() {
		CollectDTO dto = new CollectDTO();
		dto.setGeneralSpeed(120);
		dto.setOperationId("29c31a3d-97a7-439e-b201-ce80b716dfc6");

		String json = GsonFactory.getGson().toJson(dto);

		String body = makeContentRequest().and().log().all().given().content(json).post("/collect").andReturn().body()
				.asString();

		System.out.println(body);
		ActivityDTO returned = GsonFactory.getGson().fromJson(body, ActivityDTO.class);

		int statusCode = makeRequest().and().log().all().given().get("/collect/{id}", returned.getUuid()).andReturn()
				.statusCode();

		Assert.assertEquals(200, statusCode);
	}
	
	@Test
	public void doFullInsertTest() {
		CollectDTO dto = new CollectDTO();
		dto.setGeneralSpeed(120);
		dto.setOperationId("29c31a3d-97a7-439e-b201-ce80b716dfc6");
		StringBuilder sb = readFromFile("classpath:testInsert.json");

		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm:ss")
				.create();

		OperationDTO fromJson = gson.fromJson(sb.toString(),
				OperationDTO.class);
		List<CollectDTO> collects = fromJson.getCollects();
		CollectDTO collectDTO = collects.get(0);
		
		collectDTO.setOperationId("29c31a3d-97a7-439e-b201-ce80b716dfc6");
		
		String json = GsonFactory.getGson().toJson(collectDTO);
		System.out.println(json);

		String body = makeContentRequest().and().log().all().given().content(json).post("/collect").andReturn().body()
				.asString();

		System.out.println(body);
		ActivityDTO returned = GsonFactory.getGson().fromJson(body, ActivityDTO.class);

		int statusCode = makeRequest().and().log().all().given().get("/collect/{id}", returned.getUuid()).andReturn()
				.statusCode();

		Assert.assertEquals(200, statusCode);
	}

	@Test
	public void doUpdateTest() {
		String body = makeRequest().and().given().get("/collect/{id}", "4d28f6f6-eeb3-4f79-848a-5926e40fba8c")
				.andReturn().body().asString();

		CollectDTO collectDTO = GsonFactory.getGson().fromJson(body, CollectDTO.class);
		collectDTO.setGeneralSpeed(95);

		String json = GsonFactory.getGson().toJson(collectDTO);
		String body2 = makeContentRequest().and().given().log().all().content(json).put("/collect").andReturn().body()
				.asString();

		System.out.println("ActivityControllerTest.doUpdateTest()");
		System.out.println(body2);
		CollectDTO returned = GsonFactory.getGson().fromJson(body2, CollectDTO.class);

		Assert.assertEquals(95, returned.getGeneralSpeed());
	}

}
