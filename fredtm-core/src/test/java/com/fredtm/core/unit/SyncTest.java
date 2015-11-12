package com.fredtm.core.unit;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.fredtm.core.model.Sync;

public class SyncTest {
	
	@Test
	public void shouldBeClassified(){
		Sync s1 = new Sync();
		Sync s2 = new Sync();
		Sync s3 = new Sync();
		Sync s4 = new Sync();
		Sync s5 = new Sync();
		Sync s6 = new Sync();
		
		Calendar c = GregorianCalendar.getInstance();
		
		c.set(2015, 7, 15, 10, 38);
		Date t1 = c.getTime();
		s1.setCreated(t1);
		
		c.set(2015, 7, 15, 10, 42);
		Date t2 = c.getTime();
		s2.setCreated(t2);
		
		c.set(2015, 7, 15, 11, 38);
		Date t3 = c.getTime();
		s3.setCreated(t3);
		
		c.set(2015, 7, 15, 10, 22);
		Date t4 = c.getTime();
		s4.setCreated(t4);
		
		c.set(2015, 7, 15, 10, 1);
		Date t5 = c.getTime();
		s5.setCreated(t5);
		
		c.set(2015, 7, 15, 10, 2);
		Date t6 = c.getTime();
		s6.setCreated(t6);
		
		Collection<Sync> asList = Arrays.asList(s1,s2,s3,s4,s5,s6);
		List<Sync> syncs = new LinkedList<Sync>(asList);
		Collections.sort(syncs);
		
		
		Assert.assertEquals(s3.getCreated(),syncs.get(0).getCreated());
		Assert.assertEquals(s5.getCreated(),syncs.get(5).getCreated());
	}

}
