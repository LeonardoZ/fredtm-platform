package com.fredtm.api.integration;

import org.junit.Assert;
import org.junit.Test;

import com.fredtm.api.test.BaseTest;
import com.fredtm.resources.ActivitiesDTO;
import com.fredtm.resources.ActivityDTO;
import com.fredtm.resources.base.GsonFactory;

public class ActivityControllerTest extends BaseTest {
	
	@Test
	public void doAllGetTest(){
		String body = makeRequest().and().log().all().given().get("/activity/by/operation/{uuid}", "29c31a3d-97a7-439e-b201-ce80b716dfc6").andReturn()
		.body().asString();
		ActivitiesDTO fromJson = GsonFactory.getGson().fromJson(body, ActivitiesDTO.class);

		Assert.assertEquals(2, fromJson.getEmbedded().getActivityDTOList().size());

	}

	@Test
	public void doGetTest(){
		int statusCode = makeRequest().and().log().all().given().get("/activity/{uuid}", "202d39c2-17c7-4949-a494-248dca56b833").andReturn()
				.statusCode();

		Assert.assertEquals(200, statusCode);

	}
	
	@Test
	public void doDeleteTest() {
		int statusCode = makeRequest().and().given().pathParam("id", "29c31a3d-97a7-439e-b201-ce80b716dfc6")
				.delete("/activity/{id}").andReturn().statusCode();
		Assert.assertEquals(200, statusCode);
	}

	@Test
	public void doInsertTest() {
		ActivityDTO activityDTO = new ActivityDTO()
				.operationId("29c31a3d-97a7-439e-b201-ce80b716dfc6")
				.title("Seeking")
				.description("Test Activity")
				.activityType(1);
		
		String json = GsonFactory.getGson().toJson(activityDTO);

		String body = makeContentRequest().and().log().all().given().content(json).post("/activity").andReturn().body()
				.asString();

		System.out.println(body);
		ActivityDTO returned = GsonFactory.getGson().fromJson(body, ActivityDTO.class);

		int statusCode = makeRequest().and().log().all().given().get("/activity/{id}", returned.getUuid()).andReturn()
				.statusCode();

		Assert.assertEquals(200, statusCode);
	}

	@Test
	public void doUpdateTest() {
		String body = makeRequest().and().given().get("/activity/{id}", "202d39c2-17c7-4949-a494-248dca56b833")
				.andReturn().body().asString();

		ActivityDTO activityDTO = GsonFactory.getGson().fromJson(body, ActivityDTO.class);
		activityDTO.setDescription("This is my new description");

		String json = GsonFactory.getGson().toJson(activityDTO);
		String body2 = makeContentRequest().and().given().log().all().content(json).put("/activity").andReturn().body()
				.asString();

		System.out.println("ActivityControllerTest.doUpdateTest()");
		System.out.println(body2);
		ActivityDTO returned = GsonFactory.getGson().fromJson(body2, ActivityDTO.class);

		Assert.assertEquals("This is my new description", returned.getDescription());
	}

}
