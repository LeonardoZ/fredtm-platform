package com.fredtm.api.unit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fredtm.api.resource.OperationResource;
import com.fredtm.api.resource.OperationResourceAssembler;
import com.fredtm.api.resource.ResourceJsonUtil;
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
		operation.setId("aaa");

		Activity activity = new Activity(operation, "Toast", "For tests",
				ActivityType.PRODUCTIVE, true);
		activity.setId("bbb");
		Activity activity2 = new Activity("Toast 2", "For tests 2",
				ActivityType.IMPRODUCTIVE);
		activity2.setOperation(operation);
		activity2.setId("ccc");
		Activity activity3 = new Activity(operation, "Toast 3", "For tests 3",
				ActivityType.PRODUCTIVE, false);
		activity3.setId("ddd");
		
		operation.addActivity(activity);
		operation.addActivity(activity2);
		operation.addActivity(activity3);

		Collect collect = new Collect();
		collect.setId("abc");
		collect.setOperation(operation);
		operation.addCollect(collect);

		collect.addNewActivity(activity3);
		collect.addNewActivity(activity2);
		collect.addNewActivity(activity);
		long baseTime = 5_828_000;
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
		OperationResource resource = ora.toResource(operation);
		String json = new ResourceJsonUtil().toJson(resource);
		OperationResource fromJson = new ResourceJsonUtil().fromJson(json,
				OperationResource.class);
		System.out.println(json);
		System.err.println(fromJson);
		Assert.assertEquals(resource, fromJson);
	}

}
