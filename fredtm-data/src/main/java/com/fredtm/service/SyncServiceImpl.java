package com.fredtm.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
