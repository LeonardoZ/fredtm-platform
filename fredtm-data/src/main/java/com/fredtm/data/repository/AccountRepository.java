package com.fredtm.data.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.fredtm.core.model.Account;

@Transactional
public interface AccountRepository extends CrudRepository<Account, Long> {

	 Optional<Account> getByEmailAndPasswordHash(String password,String passwordHash);
	
}
