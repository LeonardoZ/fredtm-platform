package com.fredtm.api.integration;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

import com.fredtm.api.resource.OperationResource;
import com.fredtm.api.resource.SyncResource;
import com.fredtm.api.test.TestBase;
import com.google.gson.Gson;

public class SyncResourcesTest extends TestBase {

	@Test
	public void freshOperationSync() {
		StringBuilder sb = readFromFile("classpath:testInsert.json");
		OperationResource fromJson = new Gson().fromJson(sb.toString(),
				OperationResource.class);
		SyncResource syncResource = makeContentRequest().given().body(fromJson)
				.post("/sync").andReturn().as(SyncResource.class);

		Assert.assertTrue(!syncResource.getUuid().isEmpty());
	}

	@Test
	public void alreadyExistingOperationSync() {
		StringBuilder sb = readFromFile("classpath:test.json");
		OperationResource fromJson = new Gson().fromJson(sb.toString(),
				OperationResource.class);
		SyncResource syncResource = makeContentRequest().given().body(fromJson)
				.post("/sync").andReturn().as(SyncResource.class);
		OperationResource syncdOperation = makeRequest().given()
				.pathParam("id", syncResource.getOperationId())
				.get("/operation/{id}").as(OperationResource.class);
		Assert.assertTrue(fromJson.equals(syncdOperation));
	}

	@Test
	public void shouldNotSyncOldOperation() {
		StringBuilder sb = readFromFile("classpath:test.json");
		OperationResource fromJson = new Gson().fromJson(sb.toString(),
				OperationResource.class);
		Calendar c = GregorianCalendar.getInstance();
		c.set(2014, 12, 2, 7, 45);
		fromJson.setModification(c.getTime());
		int statusCode = makeContentRequest().given().body(fromJson)
				.post("/sync").andReturn().statusCode();

		Assert.assertEquals(304, statusCode);
	}
	
	@Test
	public void shouldReturn2operations(){
		StringBuilder sb = readFromFile("classpath:testInsert.json");
		OperationResource fromJson = new Gson().fromJson(sb.toString(),
				OperationResource.class);
		makeContentRequest().given().body(fromJson)
				.post("/sync").andReturn().as(SyncResource.class);

		int size = makeRequest().given().pathParam("accountId", "A").get("/sync/{accountId}").andReturn().jsonPath().getList("").size();
		Assert.assertEquals(2, size);
		
		
	}

	

}
