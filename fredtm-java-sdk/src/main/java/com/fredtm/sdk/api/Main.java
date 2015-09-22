package com.fredtm.sdk.api;

import com.fredtm.resources.AccountDTO;
import com.fredtm.resources.security.LoginDTO;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Main {

	public static void main(String[] args) {
//		AccountApi accountApi = new FredTmApi("http://localhost:9000/fredapi","", "").getAnnonymous(AccountApi.class);
//		AccountResource account = new AccountResource().email("leo-zapp@gmail.com")
//				.name("Zapparoli").password("123456");
//
//		accountApi.createAccount(account, new Callback<AccountResource>() {
//			
//			@Override
//			public void success(AccountResource t, Response response) {
//				System.out.println(t);
//			}
//			
//			@Override
//			public void failure(RetrofitError error) {
//				System.out.println(error.getMessage());
//				
//			}
//		});
		AccountApi caccountApi = 
				new FredTmApi("https://192.168.1.104:9000/fredapi",new LoginDTO("563asd","leo@gmail.com"))
				.getComplete(AccountApi.class);
		caccountApi.getAccount("42e55de6-ebde-4a8e-b19d-60b4444dd0c7", new Callback<AccountDTO>() {

	@Override
	public void success(AccountDTO t, Response response) {
		System.err.println("======== FINISH! " + t.getUuid());
	}

	@Override
	public void failure(RetrofitError error) {
		// TODO Auto-generated method stub

	}
});}

}
