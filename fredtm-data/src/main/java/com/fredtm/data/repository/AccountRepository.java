package com.fredtm.data.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.fredtm.core.model.Account;

@Transactional
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {

	 Optional<Account> getByEmailAndPasswordHash(String email,String passwordHash);
	
}
