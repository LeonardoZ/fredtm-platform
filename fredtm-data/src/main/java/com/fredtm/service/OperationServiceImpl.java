package com.fredtm.service;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fredtm.core.model.Operation;
import com.fredtm.data.repository.OperationRepository;

@Service
@Transactional
public class OperationServiceImpl implements OperationService {

	@Autowired
	private OperationRepository opRepository;
	
	@Override
	@Transactional(value=TxType.REQUIRED)
	public Operation getOperation(String uuid) {
		Operation operation = opRepository.findByUuid(uuid);
		operation.getCollects().size();
		operation.getActivities().size();
		operation.getSyncs().size();
		return operation;
	}


}
