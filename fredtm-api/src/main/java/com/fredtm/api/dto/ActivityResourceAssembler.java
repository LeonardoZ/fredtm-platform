package com.fredtm.api.dto;

import com.fredtm.api.rest.AccountResources;
import com.fredtm.core.model.Account;

public class ActivityResourceAssembler extends
		JaxRsResourceAssemblerSupport<Account, AccountResource> {

	public ActivityResourceAssembler() {
		super(AccountResources.class, AccountResource.class);
	}

	@Override
	public AccountResource toResource(Account entity) {
		return new AccountResource().id(entity.getId()).email(entity.getEmail()).name(entity.getName())
				.password(entity.getPasswordHash());
	}

}
