package com.fredtm.service;

import java.util.Date;
import java.util.List;

import com.fredtm.core.model.Account;
import com.fredtm.core.model.Operation;
import com.fredtm.core.model.Sync;

public interface SyncService {

	Sync receiveSync(Operation oldOperation ,Operation newOperation);
	
	Sync receiveSync(Operation newOperation);

	List<Operation> sendLastOperations(Account acc);

	SyncState isValidSync(String uuid,String accUuid, Date modification);
	
	void eraseDataFromOperation(Operation op);


}
