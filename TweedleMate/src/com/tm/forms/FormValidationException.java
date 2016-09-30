package com.tm.forms;

public class FormValidationException extends Exception {

	public FormValidationException(String message) {
		super(message);
	}

	public FormValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public FormValidationException(Throwable cause) {
		super(cause);
	}
}
