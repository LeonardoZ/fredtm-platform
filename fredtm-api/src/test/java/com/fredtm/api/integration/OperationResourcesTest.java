package com.fredtm.api.integration;

import static org.hamcrest.Matchers.is;

import org.junit.Test;

import com.fredtm.api.test.TestBase;

public class OperationResourcesTest extends TestBase {

	@Test
	public void doTest() {
		makeRequest().and().given().pathParam("id", "fd806586-35d4-4e8c-a119-935d4bea0773")
		.get("/operation/{id}").then().statusCode(is(200));
	}

}
