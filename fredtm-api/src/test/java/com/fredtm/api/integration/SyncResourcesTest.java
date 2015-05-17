package com.fredtm.api.integration;

import static com.jayway.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import com.fredtm.api.resource.OperationResource;
import com.fredtm.api.resource.SyncResource;
import com.fredtm.api.test.TestBase;
import com.fredtm.data.repository.OperationRepository;
import com.google.gson.Gson;
import com.jayway.restassured.specification.ResponseSpecification;

public class SyncResourcesTest extends TestBase {

	@Autowired
	private OperationRepository repo;

	@Autowired
	private ApplicationContext context;

	protected ResponseSpecification makeContentRequest() {
		return given().relaxedHTTPSValidation().auth()
				.basic("leo.zapparoli@gmail.com", "123")
				.header("Accept", "application/json")
				.header("Content-Type", "application/json;charset=UTF8").log()
				.all().then();

	}

	@Test
	public void doTest() {
		BufferedReader br = null;
		Resource resource = context.getResource("classpath:testInsert.json");
		try {
			br = new BufferedReader(new InputStreamReader(
					resource.getInputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String line;
		StringBuilder sb = new StringBuilder();
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		OperationResource fromJson = new Gson().fromJson(sb.toString(),
				OperationResource.class);
		SyncResource syncResource = makeContentRequest().given().body(fromJson).post("/sync")
				.andReturn().as(SyncResource.class);
		
		Assert.assertTrue(!syncResource.getUuid().isEmpty());
	}

}
