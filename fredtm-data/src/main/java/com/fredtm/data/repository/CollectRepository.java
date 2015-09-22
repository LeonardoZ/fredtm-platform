package com.fredtm.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.fredtm.core.model.Collect;


@Transactional
public interface CollectRepository extends JpaRepository<Collect, Integer>{

}
