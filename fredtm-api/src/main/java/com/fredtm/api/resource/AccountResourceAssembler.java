package com.fredtm.api.resource;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.api.rest.AccountResources;
import com.fredtm.core.model.Account;
import com.fredtm.data.repository.AccountRepository;

@Component
public class AccountResourceAssembler extends
		JaxRsResourceAssemblerSupport<Account, AccountResource> {

	public AccountResourceAssembler() {
		super(AccountResources.class, AccountResource.class);
	}

	@Override
	public AccountResource toResource(Account entity) {
		return new AccountResource().uuid(entity.getId())
				.email(entity.getEmail()).name(entity.getName())
				.password(entity.getPasswordHash());
	}

	@Autowired
	private AccountRepository repository;

	public Optional<Account> fromResource(AccountResource ar) {
		Account ac  =  hasValidUuid(ar)  ? repository.findOne(ar.getUuid()) : new Account();
		ac.setEmail(ar.getEmail());
		ac.setName(ar.getName());
		ac.setPassword(ar.getPassword());
		return Optional.of(ac);
	}

}
