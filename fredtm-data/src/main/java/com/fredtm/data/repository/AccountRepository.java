package com.fredtm.data.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.fredtm.core.model.Account;

@Transactional
public interface AccountRepository extends PagingAndSortingRepository<Account, Integer> {

	Optional<Account> getByEmail(String email);

	Account findByUuid(String uuid);

}
