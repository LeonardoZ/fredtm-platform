package com.fredtm.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.fredtm.core.model.Collect;


@Transactional
public interface CollectRepository extends JpaRepository<Collect, Integer>{

	Optional<Collect> findByUuid(String uuid);

	@Query(name = "Collect.findByOperationUuid", value = "select c from Collect c where c.operation.uuid = :opuuid ")
	List<Collect> findAllByOperationUuid(@Param("opuuid") String operationUuid);

}
