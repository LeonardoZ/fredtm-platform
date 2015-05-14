package com.fredtm.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.fredtm.core.model.Account;


public interface AccountService {

	Account createAccountWithHash(String email, String hash, String name);

	Account createAccount(String email, String password, String name);

	Account createAccount(Account account);

	Optional<Account> loginAccount(String email, String pass);

	Account getAccount(String id);

	Page<Account> getAllAccounts(int page, int elementsInRequest);

}