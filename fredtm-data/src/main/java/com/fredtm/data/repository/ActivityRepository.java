package com.fredtm.data.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fredtm.core.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, String> {

	@Query(name = "findByTitleAndOperationId", value = "select a from Activity a where a.title = ? and a.operation.id = ? ")
	Activity findByTitleAndOperationId(String title, String id);

	@Query(name = "findByOperationId", value = "select a from Activity a where a.operation.id = ? ")
	Set<Activity> findByOperationId(String string);

}
