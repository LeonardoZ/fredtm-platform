package com.fredtm.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fredtm.core.model.Account;
import com.fredtm.core.model.Operation;
import com.fredtm.core.model.Sync;
import com.fredtm.data.repository.ActivityRepository;
import com.fredtm.data.repository.CollectRepository;
import com.fredtm.data.repository.OperationRepository;
import com.fredtm.data.repository.SyncRepository;

@Service
public class SyncServiceImpl implements SyncService {

	@Autowired
	private OperationRepository opRepository;

	@Autowired
	private SyncRepository syncRepository;

	@Autowired
	private CollectRepository collectRepo;

	@Autowired
	private ActivityRepository activityRepo;

	
	
	public SyncState isValidSync(String uuid, String syncId, Date newModification) {
		if (uuid == null || uuid.isEmpty()) {
			return SyncState.NEW_SYNC;
		}

		Operation operation = opRepository.findOne(uuid);
		clearDates(operation.getModified(), newModification);

		if (operation.getModified().before(newModification)) {
			return SyncState.SYNC_EXISTING;
		} else if (operation.getModified().after(newModification) ||
				operation.getModified().equals(newModification.getTime())) {
			return SyncState.SYNC_DATE_CONFLICT;
		} else {
			return SyncState.NOTHING_TO_RECEIVE_FROM_SYNC;
		}
	}

	private void clearDates(Date d1, Date d2) {
		Calendar c1 = GregorianCalendar.getInstance();
		Calendar c2 = GregorianCalendar.getInstance();

		c1.setTime(d1);
		c2.setTime(d2);

		c1.clear(Calendar.MILLISECOND);
		c2.clear(Calendar.MILLISECOND);

	}

	@Override
	@Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
	public Sync receiveSync(Operation oldOperation, Operation newOperation) {
		// New Sync
		eraseDataFromOperation(oldOperation);

		newOperation.setSyncs(oldOperation.getSyncs());
		newOperation = opRepository.save(newOperation);

		Date when = new Date();
		Sync sync = new Sync();
		sync.setCreated(when);
		sync.setOperation(newOperation);
		newOperation.addSync(sync);
		return syncRepository.save(sync);
	}

	@Transactional(value = TxType.MANDATORY, rollbackOn = Exception.class)
	public void eraseDataFromOperation(Operation op) {
		System.err.println("Collects! = "+op.getCollects().size());
		collectRepo.delete(op.getCollects());
		activityRepo.delete(op.getActivities());
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public Sync receiveSync(Operation newOperation) {

		newOperation = opRepository.save(newOperation);
		Date when = new Date();
		Sync sync = new Sync();
		sync.setCreated(when);
		sync.setOperation(newOperation);
		newOperation.addSync(sync);
		return syncRepository.save(sync);
	}

	@Override
	public List<Operation> sendLastOperations(Account acc) {
		return opRepository.findOperationsBy(acc);
	}

}
