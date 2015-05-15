package com.fredtm.api.test;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.port;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fredtm.api.FredTmApiConfig;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.specification.ResponseSpecification;
@FredApiTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FredTmApiConfig.class)
@WebAppConfiguration
@IntegrationTest
@ActiveProfiles("test")
@SqlGroup({
		@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql") })
public class TestBase {

	@Before
	public void init() {

		baseURI = "https://localhost";
		port = 9000;
		basePath = "/fredapi";
		RestAssured.defaultParser = Parser.JSON;
	}
	
	protected ResponseSpecification makeRequest(){
		return given()
				.relaxedHTTPSValidation()
				.auth()
				.basic("leo.zapparoli@gmail.com", "123")
				.header("Accept", "application/json").log().all().then();
		
	}
	
	protected ResponseSpecification makeContentRequest(){
		return given()
				.relaxedHTTPSValidation()
				.auth()
				.basic("leo.zapparoli@gmail.com", "123")
				.header("Accept", "application/json")
				.header("Content-Type", "application/json;charset=UTF8")
				.log().all().then();
		
	}
	
	protected ResponseSpecification makeHeaderlessContentRequest(){
		return given()
				.relaxedHTTPSValidation()
				.header("Accept", "application/json")
				.header("Content-Type", "application/json")
				.log().all().then();
		
	}
	
}
