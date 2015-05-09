package com.fredtm.data.test;



import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.fredtm.core.model.Account;
import com.fredtm.data.FredDataConfig;
import com.fredtm.data.repository.AccountRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { FredDataConfig.class })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ActiveProfiles("test")
public class AccountResourcesTest {
	
	@Autowired
	private AccountRepository repository;
	
	@Test
	public void createAccount(){
		Account account = new Account();
		account.setName("Leo");
		account.setPassword("1234");
		account.setEmail("leo.zapparoli@gmail.com");
		repository.save(account);
		Account found = repository.findAll().iterator().next();
		Assert.assertEquals(account, found);
	}

}
