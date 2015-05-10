package com.fredtm.core.unit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ OperationTest.class, ActivityTest.class,
		TimeActivityTest.class, FormatElapsedTimeTest.class, CollectTest.class,
		ValidationTest.class })
public class AllTests {

}
