package com.fredtm.api.rest;

import java.util.Optional;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.api.dto.AccountDto;
import com.fredtm.core.model.Account;
import com.fredtm.service.AccountService;

@Component
@Path(value = "/account")
@Produces(value = MediaType.APPLICATION_JSON)
public class AccountResources {

	@Autowired
	private AccountService service;

	@POST
	public Response createAccount(AccountDto accountDto) {
		Account createdAccount = service.createAccount(accountDto.getEmail(),
				accountDto.getPassword(), accountDto.getName());
		return Response.ok(createdAccount).build();
	}

	@POST
	@Path("/login")
	public Response loginAccount(AccountDto dto) {
		Optional<Account> logedAccount = service.loginAccount(dto.getEmail(), dto.getPassword());
		if (logedAccount.isPresent()) {
			Account account = logedAccount.get();
			AccountDto accountDto = new AccountDto().email(account.getEmail())
					.name(account.getName());
			return Response.ok(accountDto).build();
		} else {
			return Response.status(401).build();
		}
	}

}
