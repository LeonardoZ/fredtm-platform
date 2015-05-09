package com.fredtm.api.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.core.model.Account;
import com.fredtm.data.repository.AccountRepository;

@Component
@Path(value = "/account")
@Produces(value = MediaType.APPLICATION_JSON)
public class AccountResources {

	@Autowired
	private AccountRepository repository;
	
	@POST
	public Response createAccount(Account account){
		Account saved = repository.save(account);
        return Response.ok(saved).build();
	}

}
