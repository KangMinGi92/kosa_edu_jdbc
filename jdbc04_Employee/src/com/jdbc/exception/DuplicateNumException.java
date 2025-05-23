package com.jdbc.exception;

public class DuplicateNumException extends Exception {
	public DuplicateNumException() {
		this("This is DuplicateNumException...");
	}
	public DuplicateNumException(String message) {
		super(message);
	}
}
