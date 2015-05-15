package com.fredtm.data.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.fredtm.core.model.Operation;
import com.fredtm.core.model.Sync;
import com.fredtm.data.FredDataConfig;
import com.fredtm.data.repository.OperationRepository;
import com.fredtm.service.SyncService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { FredDataConfig.class })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ActiveProfiles("test")
@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql") })
public class SyncServiceTest {

	@Autowired
	SyncService service;

	@Autowired
	OperationRepository repo;

	@Test
	public void testCreatedSync() {
		try {

			Operation next = repo.findAll().iterator().next();
			next.setTechnicalCharacteristics("THATS MY SPOT");
			next.getActivities().iterator().next().setDescription("XDD");
			next.getCollects().iterator().next().setActivities(next.getActivities());
			next.getCollects()
					.iterator().next()
					.addNewTime(next.getActivities().iterator().next(), 130_000l,
							124_444l, 1200l);
			Sync sync = service.receiveSync("",next);
			System.out.println(sync.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
