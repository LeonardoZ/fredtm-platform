package com.fredtm.api.dto;

import com.fredtm.core.model.Account;


public class AccountDtoAssembler extends JaxRsResourceAssemblerSupport<Account, AccountDto> {

	public AccountDtoAssembler(Class<?> controllerClass,
			Class<AccountDto> resourceType) {
		super(controllerClass, resourceType);
	}

	@Override
	public AccountDto toResource(Account entity) {
		return new AccountDto().email(entity.getEmail()).name(entity.getName()).password(entity.getPasswordHash());
	}

}
