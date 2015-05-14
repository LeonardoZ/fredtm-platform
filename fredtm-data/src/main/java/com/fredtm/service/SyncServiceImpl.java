package com.fredtm.service;

import java.util.Date;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fredtm.core.model.Operation;
import com.fredtm.core.model.Sync;
import com.fredtm.data.repository.OperationRepository;

@Service
@Transactional
public class SyncServiceImpl implements SyncService {

	@Autowired
	private OperationRepository opRepository;


	@Transactional( rollbackOn = Exception.class)
	public Sync receiveSync(String oldJson, Operation oldOperation,
			Operation newOperation) {
		// New Sync
		Date when = new Date();
		Sync sync = new Sync();
		sync.setJsonOldData(oldJson.getBytes());
		sync.setCreated(when);
		sync.setOperation(oldOperation);
		newOperation.addSync(sync);
		opRepository.save(newOperation);
		return sync;

	}

}
