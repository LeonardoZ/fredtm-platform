package com.fredtm.api.unit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fredtm.api.resource.CollectResource;
import com.fredtm.api.resource.CollectResourceAssembler;
import com.fredtm.api.resource.OperationResource;
import com.fredtm.api.resource.OperationResourceAssembler;
import com.fredtm.api.resource.ResourceToJson;
import com.fredtm.core.model.Activity;
import com.fredtm.core.model.ActivityType;
import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Operation;

public class ResourceToJsonTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Operation operation = new Operation("Title of op", "Desc of op",
				"Details");

		Activity activity = new Activity(operation, "Toast", "For tests",
				ActivityType.PRODUCTIVE, true);

		Activity activity2 = new Activity("Toast 2", "For tests 2",
				ActivityType.IMPRODUCTIVE);
		activity2.setOperation(operation);

		Activity activity3 = new Activity(operation, "Toast 3", "For tests 3",
				ActivityType.PRODUCTIVE, false);

		operation.addActivity(activity);
		operation.addActivity(activity2);
		operation.addActivity(activity3);

		Collect collect = new Collect();
		collect.setOperation(operation);
		operation.addCollect(collect);
		
		collect.addNewActivity(activity3);
		collect.addNewActivity(activity2);
		collect.addNewActivity(activity);
		long baseTime = 12_828_000;
		// 3 hours 33 minutes 58 seconds
		long currentTime = baseTime + 10_000;
		for (int i = 0; i < 10; i++) {

			if (i == 0 || i == 9)
				collect.addNewTime(activity3, baseTime, currentTime,
						currentTime - baseTime);
			else if (i % 2 == 0)
				collect.addNewTime(activity3, baseTime, currentTime,
						currentTime - baseTime);
			else if (i % 2 == 1)
				collect.addNewTime(activity3, baseTime, currentTime,
						currentTime - baseTime);
			baseTime += 14_000;
			currentTime += 15_00;
		}
		
		OperationResourceAssembler ora = new OperationResourceAssembler();
		CollectResourceAssembler cra = new CollectResourceAssembler();
		OperationResource resource = ora.toResource(operation);
		String json = new ResourceToJson().toJson(resource);
		System.out.println(json);
	}

}
