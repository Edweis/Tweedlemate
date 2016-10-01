package com.tm.dao;

public class DAOConfigurationException extends RuntimeException {
	/*
	 * s Constructeurs
	 */
	public DAOConfigurationException(String message) {
		super(message);
	}

	public DAOConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOConfigurationException(Throwable cause) {
		super(cause);
	}
}
