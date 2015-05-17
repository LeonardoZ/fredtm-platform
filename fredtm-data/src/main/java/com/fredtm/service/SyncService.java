package com.fredtm.service;

import java.util.Date;
import java.util.Set;

import com.fredtm.core.model.Account;
import com.fredtm.core.model.Operation;
import com.fredtm.core.model.Sync;

public interface SyncService {

	Sync receiveSync(String oldJson, Operation newOperation);

	Set<Operation> sendLastOperations(Account acc);

	boolean isValidSync(String uuid, Date modification);

}
