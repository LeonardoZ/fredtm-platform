package com.fredtm.api.integration;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

import com.fredtm.api.test.TestBase;
import com.fredtm.resources.ActivityResource;
import com.fredtm.resources.OperationResource;
import com.fredtm.resources.OperationsResource;
import com.fredtm.resources.SyncResource;
import com.fredtm.resources.base.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class SyncControllerTest extends TestBase {

	@Test
	public void freshOperationSync() {
		StringBuilder sb = readFromFile("classpath:testInsert.json");

		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm:ss")
				.create();

		OperationResource fromJson = gson.fromJson(sb.toString(),
				OperationResource.class);

		String asString = makeContentRequest().given().body(fromJson)
				.post("/sync").andReturn().body().asString();


		SyncResource syncResource = gson.fromJson(asString, SyncResource.class);


		Assert.assertTrue(!syncResource.getUuid().isEmpty());
		
	}

	@Test
	public void alreadyExistingOperationSync() {
		StringBuilder sb = readFromFile("classpath:test.json");
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm:ss")
				.create();
		OperationResource fromJson = gson.fromJson(sb.toString(),
				OperationResource.class);

		String asString = makeContentRequest().given().body(fromJson)
				.post("/sync").andReturn().body().asString();

		SyncResource syncResource = gson.fromJson(asString, SyncResource.class);

		String ops = makeRequest().given()
				.pathParam("id", syncResource.getOperationId())
				.get("/operation/{id}").andReturn().body().asString();

		OperationResource syncdOperation = gson.fromJson(ops,
				OperationResource.class);
		
		Assert.assertEquals("Op de teste", syncdOperation.getName());
		for (ActivityResource act : syncdOperation.getActivities()) {
			if (act.getTitle().equals("B")) {
				Assert.assertEquals("Descripion of B Modified",
						act.getDescription());
				break;
			}
		}

		Assert.assertEquals("Op de teste", syncdOperation.getName());
		Assert.assertEquals(124444, syncdOperation.getCollects().iterator()
				.next().getTimes().iterator().next().getFinalDate());
		Assert.assertEquals(2, syncdOperation.getActivities().size());

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

		Assert.assertEquals(409, statusCode);
	}

	@Test
	public void shouldReturn2operations() {
		StringBuilder sb = readFromFile("classpath:testInsert.json");
		Gson gson = GsonFactory.getGson();
		OperationResource fromJson = gson.fromJson(sb.toString(),
				OperationResource.class);

		makeContentRequest().given().body(fromJson).post("/sync").andReturn()
				.body().asString();

		String operationsJson = makeRequest().given()
				.get("/sync/{accountId}", "A").andReturn().body().asString();

		Type listType = new TypeToken<OperationsResource>() {
		}.getType();
		
		OperationsResource operationsResource = gson.fromJson(operationsJson,
				listType);
		Assert.assertEquals(2, operationsResource.getEmbedded().getOperationResourceList()
				.size());

	}
	
}
