package com.jdbc.exception;

public class DuplicateISBNException extends Exception {
	public DuplicateISBNException() {
		this("This is DuplicateISBNException...");
	}
	public DuplicateISBNException(String message) {
		super(message);
	}
}
