package com.fredtm.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fredtm.core.model.Account;
import com.fredtm.core.util.HashGenerator;
import com.fredtm.data.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository repository;

	@Override
	@Transactional
	public Account createAccountWithHash(String email, String hash, String name) {
		Account account = new Account();
		account.setEmail(email);
		account.setName(name);
		account.setPasswordHash(hash);
		return createAccount(account);
	}

	@Override
	@Transactional
	public Account createAccount(String email, String password, String name) {
		Account account = new Account();
		account.setEmail(email);
		account.setName(name);
		account.setPassword(password);
		account.configureUUID();
		return createAccount(account);
	}

	@Override
	@Transactional
	public Account createAccount(Account account) {
		return repository.save(account);
	}

	@Override
	public Optional<Account> loginAccount(String email, String pass) {
		String hash = new HashGenerator().toHash(pass);
		return repository.getByEmailAndPasswordHash(email, hash);
	}

	@Override
	public Account getAccount(String id) {
		return repository.findOne(id);
	}

	@Override
	public Page<Account> getAllAccounts(int page, int elementsInRequest) {
		PageRequest request = new PageRequest(page, elementsInRequest, Sort.Direction.ASC, "name");
		return repository.findAll(request);
	}
}
