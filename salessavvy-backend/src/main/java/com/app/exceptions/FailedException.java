package com.app.exceptions;

public class FailedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FailedException(String message) {
		super(message);
	}

}
