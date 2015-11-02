package com.fredtm.api.exception;

public class UnauthorizedExcpetion extends IllegalArgumentException {

	private static final long serialVersionUID = 4703226350544417311L;

	public UnauthorizedExcpetion(String msg) {
		super(msg);
	}
}
