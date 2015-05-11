package com.fredtm.core.unit;

import org.junit.Test;

import com.fredtm.core.model.Validation;

public class ValidationTest {

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWithEmptyString() {
		Validation validation = new Validation();
		validation.isValidString("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWithNull() {
		Validation validation = new Validation();
		validation.isNullValue(null);
	}
	
}
