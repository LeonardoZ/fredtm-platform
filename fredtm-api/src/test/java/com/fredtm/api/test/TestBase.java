package com.fredtm.api.test;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.port;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fredtm.api.FredTmApiConfig;
import com.fredtm.resources.base.GsonFactory;
import com.fredtm.resources.security.LoginDTO;
import com.fredtm.resources.security.LoginResponse;
import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.specification.ResponseSpecification;

@FredApiTest
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "test")
@SpringApplicationConfiguration(classes = FredTmApiConfig.class)
@EnableAutoConfiguration
@WebAppConfiguration
@IntegrationTest
@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql") })
public class TestBase {

	@Autowired
	private ApplicationContext context;

	@Before
	public void init() {
		baseURI = "https://localhost";
		basePath = "/fredapi";
		port = 9000;
		RestAssured.defaultParser = Parser.JSON;
	}

	@Test
	public void test() {}

	protected ResponseSpecification makeRequest() {
		String authToken = getAuthToken();

		return given().relaxedHTTPSValidation().log().all(true).header("Accept", "application/json")
				.header("Authorization", "Bearer " + authToken).log().all().then();

	}

	private String getAuthToken() {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("leo.zapparoli@gmail.com");
		dto.setPassword("123456");
		Gson gson = GsonFactory.getGson();
		String json = gson.toJson(dto);
		LoginResponse response = makeHeaderlessContentRequest().and().given().body(json).post("/account/login")
				.andReturn().as(LoginResponse.class);
		String authToken = response.getToken();
		return authToken;
	}

	protected ResponseSpecification makeContentRequest() {
		String authToken = getAuthToken();

		return given().relaxedHTTPSValidation().header("Accept", "application/json")
				.header("Authorization", "Bearer " + authToken).header("Content-Type", "application/json;charset=UTF8")
				.log().all().then();

	}

	protected ResponseSpecification makeContentWrongRequest() {
		return given().relaxedHTTPSValidation().header("Accept", "application/json")
				.header("Content-Type", "application/json;charset=UTF8").log().all().then();

	}

	protected ResponseSpecification makeHeaderlessContentRequest() {
		return given().relaxedHTTPSValidation().header("Accept", "application/json")
				.header("Content-Type", "application/json").log().all().then();

	}

	protected StringBuilder readFromFile(String filePath) {
		Resource resource = context.getResource(filePath);

		try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
			String collect = br.lines().collect(Collectors.joining());
			return new StringBuilder(collect);
		} catch (IOException e1) {
			e1.printStackTrace();
			return new StringBuilder();
		}
	}

}
