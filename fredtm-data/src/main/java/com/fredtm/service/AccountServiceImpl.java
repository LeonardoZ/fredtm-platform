package com.fredtm.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fredtm.core.model.Account;
import com.fredtm.core.model.Role;
import com.fredtm.core.util.PasswordEncryptionService;
import com.fredtm.data.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository repository;

	@Override
	@Transactional
	public Account createAccount(String email, String password, String name) {
		Account account = new Account();
		account.setEmail(email);
		account.setName(name);
		byte[] salt = PasswordEncryptionService.generateSalt();
		byte[] encryptedPassword = PasswordEncryptionService.getEncryptedPassword(password, salt);
		account.setPassword(encryptedPassword);
		account.setSalt(salt);
		account.addRole(Role.USER);
		return repository.save(account);
	}

	@Override
	public Optional<Account> loginAccount(String email, String pass) {
		Optional<Account> account = repository.getByEmail(email);
		if (account.isPresent()) {
			boolean authenticated = PasswordEncryptionService.authenticate(pass, account.get().getPasswordHash(),
					account.get().getSalt());
			return authenticated ? account : Optional.empty();
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Account getAccount(String uuid) {
		return repository.findByUuid(uuid);
	}

	@Override
	public Account getAccount(Integer id) {
		return repository.findOne(id);
	}

	@Override
	public Page<Account> getAllAccounts(int page, int elementsInRequest) {
		PageRequest request = new PageRequest(page, elementsInRequest, Sort.Direction.ASC, "name");
		return repository.findAll(request);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Account changePassword(Account account, String newPassword) {
		account = repository.findOne(account.getId());
		account.setPassword(PasswordEncryptionService.getEncryptedPassword(newPassword, account.getSalt()));
		return repository.save(account);
	}
}
