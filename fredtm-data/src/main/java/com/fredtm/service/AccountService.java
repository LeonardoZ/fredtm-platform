package com.fredtm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fredtm.core.model.Account;
import com.fredtm.data.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repository;

	@Transactional
	public Account createAccountWithHash(String email, String hash, String name) {
		Account account = new Account();
		account.setEmail(email);
		account.setName(name);
		account.setPasswordHash(hash);
		return createAccount(account);
	}
	
	@Transactional
	public Account createAccount(String email, String password, String name) {
		Account account = new Account();
		account.setEmail(email);
		account.setName(name);
		account.setPassword(password);
		return createAccount(account);
	}

	@Transactional
	public Account createAccount(Account account) {
		return repository.save(account);
	}
}
