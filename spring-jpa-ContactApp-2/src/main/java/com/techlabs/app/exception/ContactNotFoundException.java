package com.techlabs.app.exception;

public class ContactNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ContactNotFoundException(String message) {
		super(message);
	}
}
