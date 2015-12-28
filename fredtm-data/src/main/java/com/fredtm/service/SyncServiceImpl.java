package com.fredtm.service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fredtm.core.model.Account;
import com.fredtm.core.model.Activity;
import com.fredtm.core.model.FredEntity;
import com.fredtm.core.model.Operation;
import com.fredtm.core.model.Sync;
import com.fredtm.core.model.TimeActivity;
import com.fredtm.core.model.TimeActivityPicture;
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

	public SyncState isValidSync(String operationUuid, Date newModification) {
		if (operationUuid == null || operationUuid.equals("")) {
			return SyncState.NEW_SYNC;
		}

		Operation operation = opRepository.findByUuid(operationUuid);
		clearDates(operation.getModified(), newModification);

		if (operation.getModified().before(newModification)) {
			return SyncState.SYNC_EXISTING;
		} else if (operation.getModified().after(newModification)
				|| operation.getModified().equals(newModification.getTime())) {
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

		// retrieve old ids
//		retrieveOldIds(oldOperation, newOperation);

		// New Sync
		eraseDataFromOperation(oldOperation);

		List<Sync> syncs = oldOperation.getSyncs();
		newOperation.setSyncs(syncs);

		for (Sync s : syncs) {
			s.setOperation(newOperation);
		}
		newOperation = opRepository.saveAndFlush(newOperation);

		Date when = new Date();
		Sync sync = new Sync();
		sync.setCreated(when);
		sync.setOperation(newOperation);
		newOperation.addSync(sync);
		return syncRepository.save(sync);
	}

	private void retrieveOldIds(Operation oldOperation, Operation newOperation) {
		Map<String, Integer> collectIds = mapEntitiesToIds(oldOperation.getCollects());
		newOperation.getCollects().forEach(nc -> {
			// collect ids
			retrieveId(nc, collectIds);

			List<TimeActivity> times = nc.getTimes();
			Map<String, Integer> timesIds = mapEntitiesToIds(times);
			System.out.println("Times id "+timesIds);
			times.stream().forEach(ts -> {
				// time ids
				retrieveId(ts, timesIds);
				
				// pic ids
				List<TimeActivityPicture> pictures = ts.getPictures();
				Map<String, Integer> idsRepository = mapEntitiesToIds(pictures);System.out.println("PIC "+idsRepository);
				pictures.stream().forEach(p -> retrieveId(p, idsRepository));
			});
		});
		

		Set<Activity> activities = oldOperation.getActivities();
		Map<String, Integer> idsRepository = mapEntitiesToIds(activities);
		System.out.println(idsRepository);
		activities.stream().forEach(a -> retrieveId(a, idsRepository));

	}
	
	<T extends Collection<X>, X extends FredEntity> Map<String, Integer> mapEntitiesToIds(T collection){
		Map<String, Integer> map = collection.stream()
				.filter(f -> f.getUuid() != null && !f.getUuid().isEmpty())
				.filter(f2 -> f2.getId() != null && !f2.getId().equals(0))
				.collect(Collectors.toMap(FredEntity::getUuid, FredEntity::getId));
		 return map == null ? Collections.emptyMap() : map;
	}
	
	void retrieveId(FredEntity entity, Map<String,Integer> idsRepository){
		String uuid = entity.getUuid();
		if (idsRepository.containsKey(uuid)) {
			Integer id = idsRepository.get(uuid);
			entity.setId(id);
		}
	}
	
	@Transactional(value = TxType.MANDATORY, rollbackOn = Exception.class)
	public void eraseDataFromOperation(Operation op) {
		collectRepo.delete(op.getCollects());
		activityRepo.delete(op.getActivities());
	}

	@Override
	@Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
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
