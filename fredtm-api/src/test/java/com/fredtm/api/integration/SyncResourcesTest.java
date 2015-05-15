package com.fredtm.api.integration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import com.fredtm.api.resource.OperationResource;
import com.fredtm.api.test.TestBase;
import com.fredtm.data.repository.OperationRepository;
import com.google.gson.Gson;


public class SyncResourcesTest extends TestBase {

	@Autowired
	private OperationRepository repo;

	@Autowired
	private ApplicationContext context;

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
		System.out.println(fromJson);
		String prettyPrint = makeContentRequest().given().body(fromJson).post("/sync").andReturn()
				.body().prettyPrint();

		System.err.println("aaaa" + prettyPrint);
	}

}
