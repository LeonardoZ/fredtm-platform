package com.fredtm.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fredtm.core.model.Operation;
import com.fredtm.core.model.Sync;

@Service
@Transactional
public class OperationServiceImpl implements OperationService {

	@Override
	public Sync synchronizeOperation(Operation operation) {
		return null;
	}

}
