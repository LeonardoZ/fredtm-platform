package com.fredtm.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.fredtm.core.model.Sync;

public interface SyncRepository extends
		PagingAndSortingRepository<Sync, String> {

}
