package com.fredtm.api.test;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.port;

import org.junit.Before;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.specification.ResponseSpecification;

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
