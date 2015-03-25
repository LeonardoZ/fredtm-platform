package com.fredtm.core.model;

/**
 * @author leonardo
 *
 */
public class Validation {

	/**
	 * @param value
	 * @throws IllegalArgumentException
	 */
	public void isValidString(String value) {
		isNullValue(value);
		if(value.isEmpty()){
			throw new IllegalArgumentException("String should not be empty");
		}
	}

	/**
	 * @param value
	 * @throws IllegalArgumentException
	 */
	public void isNullValue(Object value) {
		if(value == null)
			throw new IllegalArgumentException("Value is null");
	}
	

}
