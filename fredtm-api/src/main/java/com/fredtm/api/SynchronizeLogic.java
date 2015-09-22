package com.fredtm.api;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fredtm.core.model.Operation;
import com.fredtm.core.util.FredObjectMapper;
import com.fredtm.data.repository.OperationRepository;
import com.fredtm.resources.OperationDTO;

@Service
public class SynchronizeLogic {

	@Autowired
	private OperationRepository opRepository;


	@Transactional(rollbackOn = Exception.class)
	public Operation doSyncOnNew(OperationDTO resource) {
		// Create new
		Operation operation = FredObjectMapper.mapResourceToEntity(resource);
		opRepository.save(operation);
		return operation;
	}

	public Operation doSyncOnExisting(OperationDTO resource) {
		// Create new
		Operation operation = FredObjectMapper.mapResourceToEntity(resource);
		return operation;
	}
	

}
