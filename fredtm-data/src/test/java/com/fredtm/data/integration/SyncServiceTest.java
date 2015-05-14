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
	public void aa() {
		try {

			Operation next = repo.findAll().iterator().next();
			next.setTechnicalCharacteristics("THATS MY SPOT");
			next.getActivities().get(0).setDescription("XDD");
			next.getCollects().get(0).setActivities(next.getActivities());
			next.getCollects()
					.get(0)
					.addNewTime(next.getActivities().get(0), 130_000l,
							124_444l, 1200l);
			Operation old = repo.findAll().iterator().next();
			service.receiveSync("",old,next);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
