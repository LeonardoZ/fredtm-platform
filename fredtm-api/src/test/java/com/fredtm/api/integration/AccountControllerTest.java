package com.fredtm.api.integration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import com.fredtm.api.test.TestBase;
import com.fredtm.resources.AccountDTO;
import com.fredtm.resources.SendAccountDTO;
import com.fredtm.resources.base.GsonFactory;
import com.fredtm.resources.security.LoginDTO;
import com.google.gson.Gson;

@ActiveProfiles(value = "test")
public class AccountControllerTest extends TestBase {
	
	@Test
	public void shouldPostNewAccount() {
		SendAccountDTO dto = new SendAccountDTO()
				.email("leo-zapp@gmail.com")
				.name("Zapparoli")
				.password("123asd");

		Gson gson = new Gson();
		String json = gson.toJson(dto);

		String jsons = makeContentWrongRequest().and().given().body(json).post("/account").thenReturn().body()
				.asString();

		AccountDTO response = gson.fromJson(jsons, AccountDTO.class);

		assertEquals(dto.getEmail(), response.getEmail());
		assertEquals(dto.getName(), response.getName());
}

	@Test
	public void shouldLogIn() {

		LoginDTO dto = new LoginDTO();
		dto.setEmail("leo.zapparoli@gmail.com");
		dto.setPassword("123456");
		
		Gson gson = GsonFactory.getGson();
				
		String json = gson.toJson(dto);
		int statusCode = makeHeaderlessContentRequest().and().given()
				.body(json).post("/account/login").andReturn().statusCode();
		assertEquals(200, statusCode);
	}

	@Test
	public void shouldNotLogIn() {

		LoginDTO dto = new LoginDTO().email(
				"leo.zapparsoli@gmail.com").password("123d");
		Gson gson = new Gson();
		String json = gson.toJson(dto);
		int statusCode = makeHeaderlessContentRequest().and().given()
				.body(json).post("/account/login").andReturn().statusCode();
		assertEquals(401, statusCode);
	}

//	@Test
//	public void shouldReturn3Elements() {
//		String json = makeRequest().and().given().queryParam("page", 0)
//				.queryParam("elements", 3).get("/account/all").andReturn()
//				.asString();
//		Gson gson = new Gson();
//
//		Type listType = new TypeToken<AccountsResource>() {
//		}.getType();
//		AccountsResource accs = gson.fromJson(json, listType);
//
//		assertEquals(3, accs.getPage().getSize());
//
//	}

}
