package com.fredtm.data.integration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.fredtm.core.model.Account;
import com.fredtm.data.FredTmDataConfig;
import com.fredtm.data.repository.AccountRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { FredTmDataConfig.class })
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ActiveProfiles("test")
@SqlGroup({ 
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql") 
})
public class AccountRepositoryTest {

	@Autowired
	private AccountRepository repository;

	public void createAccount() {
		Account account = new Account();
		account.setName("Leo");
		account.setPassword("1234");
		account.setEmail("leo.zapparoli@gmail.com");
		repository.save(account);
		Account found = repository.findOne(account.getId());
		Assert.assertEquals(account, found);
	}

	@Test
	public void shouldHave3Pages() {
		PageRequest request = new PageRequest(0, 3, Sort.Direction.DESC, "name");
		Page<Account> page = repository.findAll(request);
		Assert.assertEquals(3, page.getSize());
	}

	@Test
	public void shouldReturnTheFourthElement() {
		PageRequest request = new PageRequest(0, 3);
		Page<Account> page = repository.findAll(request);
		Pageable nextPageable = page.nextPageable();
		Page<Account> found = repository.findAll(nextPageable);
		Account account = found.getContent().get(0);
		Assert.assertEquals("D", account.getId());
	}
}
