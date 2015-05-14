package com.fredtm.service;

import com.fredtm.core.model.Operation;
import com.fredtm.core.model.Sync;

public interface OperationService {

	public Sync synchronizeOperation(Operation operation);
	
}
