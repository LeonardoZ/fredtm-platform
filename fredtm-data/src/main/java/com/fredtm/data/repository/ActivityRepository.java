package com.fredtm.data.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.fredtm.core.model.Activity;

public interface ActivityRepository extends CrudRepository<Activity, String> {

	@Query(name = "findByNameAndOperationId", value = "select a from activity a where a.title = ? and a.operation.id = ? ")
	Activity findByNameAndOperationId(String title, long id);

}
