package com.fredtm.data.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.fredtm.core.model.TimeActivity;

@Transactional
public interface TimeActivityRepository extends CrudRepository<TimeActivity, Integer> {

	Optional<TimeActivity> findByUuid(String timeUuid);

	
	
}
