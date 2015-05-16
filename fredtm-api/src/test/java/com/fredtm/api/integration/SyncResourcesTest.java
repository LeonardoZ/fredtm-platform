package com.fredtm.api.integration;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.port;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fredtm.api.FredTmApiConfig;
import com.fredtm.api.resource.OperationResource;
import com.fredtm.api.test.FredApiTest;
import com.fredtm.data.repository.OperationRepository;
import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.specification.ResponseSpecification;

@FredApiTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FredTmApiConfig.class)
@WebAppConfiguration
@IntegrationTest
@ActiveProfiles("test")
public class SyncResourcesTest  {
	@Before
	public void init() {

		baseURI = "https://localhost";
		port = 9000;
		basePath = "/fredapi";
		RestAssured.defaultParser = Parser.JSON;
	}
	@Autowired
	private OperationRepository repo;
	

	@Autowired
	private ApplicationContext context;
	protected ResponseSpecification makeContentRequest(){
		return given()
				.relaxedHTTPSValidation()
				.auth()
				.basic("leo.zapparoli@gmail.com", "123")
				.header("Accept", "application/json")
				.header("Content-Type", "application/json;charset=UTF8")
				.log().all().then();
		
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
		System.out.println(fromJson);
		String prettyPrint = makeContentRequest().given().body(fromJson).post("/sync").andReturn()
				.body().prettyPrint();

		System.err.println("aaaa" + prettyPrint);
	}

}
