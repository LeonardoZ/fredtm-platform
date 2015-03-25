package com.fredtm.test.api;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.given;
import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.fredtm.core.model.Operation;
import com.google.gson.Gson;
import com.jayway.restassured.http.ContentType;

public class OperacaoResourceTest  {
	
	@Before
	public void setUp(){
		basePath = "/fredtm-api/f/operacoes";
	}
	
	@Test
	public void createTest(){
		Operation operacao = new Operation("Teste REST", "Fred Company", "Teste de criação rest");
		Gson gson = new Gson();
		String json = gson.toJson(operacao);
		System.out.println(json);
		String nome = "";
				given()
				.body(json)
				.contentType(ContentType.JSON)
				.post("/operacao")
				.andReturn()
				.body()
				.jsonPath()
				.prettyPeek()
				.getString("nome");
		assertEquals("Teste REST",nome);
	}
	
	@Test
	public void readTest(){
			given().log().all().
			get("/operacao")
			.andReturn()
			.body()
			.prettyPrint();
		
	}

}
