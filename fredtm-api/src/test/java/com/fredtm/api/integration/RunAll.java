package com.fredtm.api.integration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AccountControllerTest.class, OperationControllerTest.class,
		SyncControllerTest.class, ActivityControllerTest.class })
public class RunAll {

}
