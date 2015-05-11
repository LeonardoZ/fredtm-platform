package com.fredtm.core.unit;

import org.junit.Assert;
import org.junit.Test;

import com.fredtm.core.util.HashGenerator;

public class HashGeneratorTest {

	@Test
	public void generateSha256Hash() {
		HashGenerator hg = new HashGenerator();
		String hash = hg.toHash("123");
		String expected = "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3";
		Assert.assertEquals("Mus be the same hash", expected, hash);
	}

}
