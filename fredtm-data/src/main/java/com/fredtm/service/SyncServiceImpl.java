package com.fredtm.service;

import java.util.Date;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fredtm.core.model.Account;
import com.fredtm.core.model.Operation;
import com.fredtm.core.model.Sync;
import com.fredtm.data.repository.OperationRepository;
import com.fredtm.data.repository.SyncRepository;

@Service
public class SyncServiceImpl implements SyncService {

	@Autowired
	private OperationRepository opRepository;

	@Autowired
	private SyncRepository syncRepository;

	@Transactional(rollbackOn = Exception.class)
	public Sync receiveSync(String oldJson, Operation newOperation) {
		// New Sync
		Date when = new Date();
		Sync sync = new Sync();
		sync.setJsonOldData(oldJson.getBytes());
		sync.setCreated(when);
		sync.setOperation(newOperation);
		newOperation.addSync(sync);
		return syncRepository.save(sync);

	}

	@Override
	public boolean isValidSync(String uuid, Date modification) {
		if (uuid == null || uuid.isEmpty()) {
			// New operation
			return true;
		}
		Operation operation = opRepository.findByIdAndModifiedAfter(uuid,
				modification);
		// if TRUE then the new mod date is the last one and sync is allowed
		return operation == null;
	}

	@Override
	public Set<Operation> sendLastOperations(Account acc) {
		return opRepository.findOperationsBy(acc);
	}

}
