package com.fredtm.service;

import com.fredtm.core.model.Operation;
import com.fredtm.core.model.Sync;

public interface SyncService {

	public Sync receiveSync(String oldJson,
			 Operation newOperation);

}
