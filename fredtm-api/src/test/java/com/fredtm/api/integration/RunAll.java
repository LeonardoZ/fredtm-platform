package com.fredtm.api.integration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AccountResourcesTest.class, OperationResourcesTest.class,
		SyncResourcesTest.class })
public class RunAll {

}
