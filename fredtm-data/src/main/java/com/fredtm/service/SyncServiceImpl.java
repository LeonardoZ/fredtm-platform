package com.fredtm.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fredtm.core.model.Operation;
import com.fredtm.core.model.Sync;
import com.fredtm.data.repository.OperationRepository;

@Service
public class SyncServiceImpl implements SyncService {

	@Autowired
	private OperationRepository opRepository;


	@Transactional( rollbackOn = Exception.class)
	public Sync receiveSync(String oldJson, 
			Operation newOperation) {
		// New Sync
		Date when = new Date();
		
		Sync sync = new Sync();
		sync.setJsonOldData(oldJson.getBytes());
		sync.setCreated(when);
		newOperation.addSync(sync);
		sync.setOperation(newOperation);
		
		
		opRepository.save(newOperation);
		Operation findOne = opRepository.findOne(newOperation.getId());
		System.err.println("syncs: "+findOne.getSyncs().toString());
		return sync;

	}

}
