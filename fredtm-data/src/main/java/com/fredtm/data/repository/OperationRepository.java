package com.fredtm.data.repository;

import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.fredtm.core.model.Account;
import com.fredtm.core.model.Operation;

@Transactional
public interface OperationRepository extends JpaRepository<Operation, String> {

	Operation findByIdAndModifiedAfter(String id, Date param);

	@Query(name = "operation.findOperationsBy", value = "select o from Operation o where o.account = :acc order by o.modified desc")
	Set<Operation> findOperationsBy(@Param("acc") Account acc);

}
