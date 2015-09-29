package com.fredtm.service;

import com.fredtm.core.model.Operation;

public interface OperationService {

	public Operation getOperation(String uuid);

	public void deleteOperation(String uuid);

	public Operation saveOperation(Operation operation);

	public boolean exists(String uuid);
	
}
