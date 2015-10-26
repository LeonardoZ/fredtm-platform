package com.fredtm.api.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import com.fredtm.api.test.TestBase;
import com.fredtm.resources.AccountDTO;
import com.fredtm.resources.ChangeToken;
import com.fredtm.resources.SendAccountDTO;
import com.fredtm.resources.base.ChangePasswordDTO;
import com.fredtm.resources.base.GsonFactory;
import com.fredtm.resources.security.LoginDTO;
import com.google.gson.Gson;

@ActiveProfiles(value = "test")
public class AccountControllerTest extends TestBase {

	@Test
	public void shouldPostNewAccount() {
		SendAccountDTO dto = new SendAccountDTO().email("leo-zapp@gmail.com").name("Zapparoli").password("123asd");

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
		int statusCode = makeHeaderlessContentRequest().and().given().body(json).post("/account/login").andReturn()
				.statusCode();
		assertEquals(200, statusCode);
	}

	@Test
	public void shouldNotLogIn() {

		LoginDTO dto = new LoginDTO().email("leo.zapparsoli@gmail.com").password("123d");
		Gson gson = new Gson();
		String json = gson.toJson(dto);
		int statusCode = makeHeaderlessContentRequest().and().given().body(json).post("/account/login").andReturn()
				.statusCode();
		assertEquals(401, statusCode);
	}

	@Test
	public void testChangePasswordToken() {
		String url = "/account/token/espc";
		AccountDTO dto = new AccountDTO().email("leo.zapparoli@gmail.com").uuid("23ca7484-9126-4eeb-91c7-262197aaef46");

		ChangeToken change = makeContentRequest().and().given().body(GsonFactory.getGson().toJson(dto)).post(url)
				.andReturn().body().as(ChangeToken.class);

		assertTrue(change.getEmail().equals("leo.zapparoli@gmail.com"));
		assertTrue(!change.getJwt().isEmpty());
	}

	@Test
	public void testDoPasswordChange() {
		String url = "/account/token/espc";
		AccountDTO dto = new AccountDTO().email("leo.zapparoli@gmail.com").uuid("23ca7484-9126-4eeb-91c7-262197aaef46");

		ChangeToken change = makeContentRequest().and().given()
				.body(GsonFactory.getGson().toJson(dto)).post(url)
				.andReturn().body().as(ChangeToken.class);

		ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO(dto.getEmail(), "33thisIsMyNewPassword22",
				change.getJwt());
		String changeUrl = "/account/password";

		AccountDTO returnedAcc = GsonFactory.getGson().fromJson(makeContentRequest().and().given()
				.body(GsonFactory.getGson().toJson(changePasswordDTO)).put(changeUrl).andReturn().asString(),
				AccountDTO.class);

		assertEquals(returnedAcc.getEmail(), "leo.zapparoli@gmail.com");

		LoginDTO login = new LoginDTO();
		login.setEmail("leo.zapparoli@gmail.com");
		login.setPassword("33thisIsMyNewPassword22");

		Gson gson = GsonFactory.getGson();

		String json = gson.toJson(login);
		int statusCode = makeHeaderlessContentRequest().and().given().body(json).post("/account/login").andReturn()
				.statusCode();
		assertEquals(200, statusCode);

	}

}
