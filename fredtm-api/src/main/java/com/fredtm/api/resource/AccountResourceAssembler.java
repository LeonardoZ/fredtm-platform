package com.fredtm.api.resource;

import org.springframework.stereotype.Component;

import com.fredtm.core.model.Account;
import com.fredtm.resources.AccountDTO;
import com.fredtm.resources.base.ElementParser;

@Component
public class AccountResourceAssembler extends ElementParser<Account, AccountDTO> {

	public AccountDTO toResource(Account entity) {
		return new AccountDTO().uuid(entity.getUuid()).email(entity.getEmail()).name(entity.getName());
	}

	@Override
	public Account fromResource(AccountDTO resource) {
		return null;
	}

}
