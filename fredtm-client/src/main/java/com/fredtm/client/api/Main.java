package com.fredtm.client.api;

import java.util.Base64;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.fredtm.resources.dto.AccountDTO;

public class Main {

	public static void main(String[] args) {
		AccountDTO dto = new AccountDTO();
		dto.setEmail("leo.zapparoli@gmail.com");
		dto.setPasswordHash("123");
		new FredTmApi("leo.zapparoli@gmail.com", "123").get(AccountApi.class)
				.loginAccount(dto, new Callback<AccountDTO>() {

					@Override
					public void success(AccountDTO t, Response response) {
						System.out.println(t);
					}

					@Override
					public void failure(RetrofitError error) {
						System.out.println(error);
					}
				});
	}

}
