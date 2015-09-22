package com.fredtm.api.integration;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

import com.fredtm.api.test.TestBase;
import com.fredtm.resources.ActivityDTO;
import com.fredtm.resources.OperationDTO;
import com.fredtm.resources.OperationsDTO;
import com.fredtm.resources.SyncDTO;
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

		OperationDTO fromJson = gson.fromJson(sb.toString(),
				OperationDTO.class);

		String asString = makeContentRequest().given().body(fromJson)
				.post("/sync").andReturn().body().asString();


		SyncDTO syncResource = gson.fromJson(asString, SyncDTO.class);


		Assert.assertTrue(!syncResource.getUuid().equals(""));
		
	}

	@Test
	public void alreadyExistingOperationSync() {
		StringBuilder sb = readFromFile("classpath:test.json");
		
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm:ss")
				.create();
		OperationDTO fromJson = gson.fromJson(sb.toString(),
				OperationDTO.class);
		
		String asString = makeContentRequest().given().body(fromJson)
				.post("/sync").andReturn().body().asString();

		System.out.println("+=================================================================");
		SyncDTO syncResource = gson.fromJson(asString, SyncDTO.class);

		String ops = makeRequest().given()
				.pathParam("id", syncResource.getOperationId())
				.get("/operation/{id}").andReturn().body().asString();

		OperationDTO syncdOperation = gson.fromJson(ops,
				OperationDTO.class);
		
		Assert.assertEquals("Op de teste", syncdOperation.getName());
		for (ActivityDTO act : syncdOperation.getActivities()) {
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
		OperationDTO fromJson = new Gson().fromJson(sb.toString(),
				OperationDTO.class);
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
		OperationDTO fromJson = gson.fromJson(sb.toString(),
				OperationDTO.class);

		makeContentRequest().given().body(fromJson).post("/sync").andReturn()
				.body().asString();

		String operationsJson = makeRequest().given()
				.get("/sync/{accountId}", "23ca7484-9126-4eeb-91c7-262197aaef46").andReturn().body().asString();

		Type listType = new TypeToken<OperationsDTO>() {
		}.getType();
		
		OperationsDTO operationsResource = gson.fromJson(operationsJson,
				listType);
		Assert.assertEquals(2, operationsResource.getEmbedded().getOperationDTOList()
				.size());

	}
	
}
