package com.fredtm.api.integration;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import com.fredtm.api.resource.AccountResource;
import com.fredtm.api.test.TestBase;
import com.fredtm.core.util.HashGenerator;
import com.google.gson.Gson;

@ActiveProfiles(value="test")
public class AccountResourcesTest extends TestBase {


	@Test
	public void shouldPostNewAccount() {
		AccountResource dto = new AccountResource().email("leo-zapp@gmail.com")
				.name("Zapparoli").password("123456");

		Gson gson = new Gson();
		String json = gson.toJson(dto);
		AccountResource response = makeContentRequest().and().given()
				.body(json).post("/account").thenReturn().body()
				.as(AccountResource.class);

		String hash = new HashGenerator().toHash("123456");

		dto.password(hash);
		assertEquals(dto, response);
	}

	@Test
	public void shouldLogIn() {

		AccountResource dto = new AccountResource().email(
				"leo.zapparoli@gmail.com").password("123");
		Gson gson = new Gson();
		String json = gson.toJson(dto);
		int statusCode = makeHeaderlessContentRequest().and().given()
				.body(json).post("/account/login").andReturn().statusCode();
		assertEquals(200, statusCode);
	}

	@Test
	public void shouldNotLogIn() {

		AccountResource dto = new AccountResource().email(
				"leo.zapparsoli@gmail.com").password("123d");
		Gson gson = new Gson();
		String json = gson.toJson(dto);
		int statusCode = makeHeaderlessContentRequest().and().given()
				.body(json).post("/account/login").andReturn().statusCode();
		assertEquals(401, statusCode);
	}

	@Test
	public void shouldReturn3Elements() {
		List<Map<Object,Object>> list = makeRequest().and().given().queryParam("page", 0)
				.queryParam("elements", 3).get("/account/all").andReturn().prettyPeek()
				.jsonPath().get("links");

		String url = list.get(2).get("href").toString()
				.substring("https://localhost:9000/".length());
		int size = makeRequest().and().given().get(url).andReturn().prettyPeek().jsonPath()
				.getList("content").size();
		assertEquals(3, size);

	}

}
