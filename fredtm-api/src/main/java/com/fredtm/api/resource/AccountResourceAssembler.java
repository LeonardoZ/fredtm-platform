package com.fredtm.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.core.model.Account;
import com.fredtm.data.repository.AccountRepository;
import com.fredtm.resources.AccountResource;
import com.fredtm.resources.base.ElementParser;

@Component
public class AccountResourceAssembler extends ElementParser<Account, AccountResource>{

	@Autowired
	private AccountRepository repository;



	public AccountResource toResource(Account entity) {
		return new AccountResource().uuid(entity.getId())
				.email(entity.getEmail()).name(entity.getName())
				.password(entity.getPasswordHash());
	}

	@Override
	public Account toEntity(AccountResource ar) {
		Account ac  =  hasValidUuid(ar)  ? repository.findOne(ar.getUuid()) : new Account();
		ac.setEmail(ar.getEmail());
		ac.setName(ar.getName());
		ac.setPassword(ar.getPassword());
		return null;
	}

}
