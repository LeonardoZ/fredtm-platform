package com.fredtm.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.fredtm.core.model.Account;

public interface SyncRepository extends
		PagingAndSortingRepository<Account, Long> {

}
