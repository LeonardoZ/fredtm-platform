package com.fredtm.api.test;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.port;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;


public class OperationEndpointsTest {

	@Before
	public void init() {

		baseURI = "https://localhost";
		port = 9000;
		basePath = "/fredapi";
	}

	@Test
	public void doTest() {
		given()
			.relaxedHTTPSValidation()
				.auth()
				.basic("leo.zapparoli@gmail.com", "123")
				.header("Accept", "application/json").log().all().then()
				.get("/operation").then().statusCode(is(200));
	}

}
