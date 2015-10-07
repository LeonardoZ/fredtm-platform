package com.fredtm.service;

import java.util.Date;
import java.util.Set;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Operation;
import com.fredtm.data.repository.CollectRepository;
import com.fredtm.data.repository.OperationRepository;
import com.fredtm.data.repository.SyncRepository;

@Service
@Transactional
public class OperationServiceImpl implements OperationService {

	@Autowired
	private SyncRepository syncRepository;

	@Autowired
	private CollectRepository collectRepository;

	@Autowired
	private OperationRepository opRepository;

	@Override
	@Transactional(value = TxType.REQUIRED)
	public Operation getOperation(String uuid) {
		Operation operation = opRepository.findByUuid(uuid);
		operation.getCollectsList().size();
		operation.getActivities().size();
		operation.getSyncs().size();
		return operation;
	}

	@Override
	@Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
	public void deleteOperation(String uuid) {
		Operation found = opRepository.findByUuid(uuid);
		Set<Collect> collects = found.getCollects();
		collectRepository.delete(collects);
		syncRepository.delete(found.getSyncs());
		opRepository.delete(found);
	}

	@Override
	@Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
	public Operation saveOperation(Operation operation) {
		operation.setModified(new Date());
		return opRepository.save(operation);

	}

	@Override
	public boolean exists(String uuid) {
		Operation found = opRepository.findByUuid(uuid);
		return found != null;
	}

}
