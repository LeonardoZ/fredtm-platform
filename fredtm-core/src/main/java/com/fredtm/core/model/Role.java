package com.fredtm.core.model;

import java.util.Arrays;
import java.util.List;

public enum Role {

	USER, ADMIN;

	public static List<Role> list() {
		return Arrays.asList(values());
	}
	
}
