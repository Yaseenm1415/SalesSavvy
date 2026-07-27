package com.app.exceptions;

public class InvalidQuantityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidQuantityException(String message) {
		super(message);
	}

}
