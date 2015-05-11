package com.fredtm.api.test;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.port;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fredtm.api.FredTmApiConfig;
import com.fredtm.api.dto.AccountDto;
import com.fredtm.core.util.HashGenerator;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FredTmApiConfig.class)
@WebAppConfiguration
@IntegrationTest
@ActiveProfiles("test")
@SqlGroup({
		@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql"), })
public class AccountResourcesTest extends TestBase {

	@Before
	public void init() {
		baseURI = "https://localhost";
		port = 9000;
		basePath = "/fredapi";
	}

	@Test
	public void shouldPostNewAccount() {
		AccountDto dto = new AccountDto().email("leo-zapp@gmail.com")
				.name("Zapparoli").password("123456");

		Gson gson = new Gson();
		String json = gson.toJson(dto);
		AccountDto response = makeContentRequest().and().given().body(json)
				.post("/account").thenReturn().body().as(AccountDto.class);

		String hash = new HashGenerator().toHash("123456");

		dto.password(hash);

		assertEquals(dto, response);
	}

	@Test
	public void shouldLogIn() {

		AccountDto dto = new AccountDto()
				.email("leo.zapparoli@gmail.com")
				.password("123");
		Gson gson = new Gson();
		String json = gson.toJson(dto);
		int statusCode = makeHeaderlessContentRequest().and().given().body(json)
				.post("/account/login").andReturn().statusCode();
		assertEquals(200,statusCode);
	}
	
	@Test
	public void shouldNotLogIn() {

		AccountDto dto = new AccountDto()
				.email("leo.zapparsoli@gmail.com")
				.password(
						"123d");
		Gson gson = new Gson();
		String json = gson.toJson(dto);
		int statusCode = makeHeaderlessContentRequest()
				.and().given().body(json)
				.post("/account/login").andReturn().statusCode();
		assertEquals(401,statusCode);
	}
}
