package com.fredtm.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.fredtm.core.model.Operation;

@Transactional
public interface OperationRepository extends CrudRepository<Operation, String>{
	
	
}
