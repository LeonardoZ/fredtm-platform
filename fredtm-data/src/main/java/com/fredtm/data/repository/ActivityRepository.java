package com.fredtm.data.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.fredtm.core.model.Activity;

@Transactional
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	@Query(name = "findByTitleAndOperationId", value = "select a from Activity a where a.title = :title and a.operation.id = :opId ")
	Activity findByTitleAndOperationId(@Param("title") String title, @Param("opId") Integer id);

	@Query(name = "findByOperationId", value = "select a from Activity a where a.operation.id = :opId ")
	Set<Activity> findByOperationId(@Param("opId") Integer opId);

}
