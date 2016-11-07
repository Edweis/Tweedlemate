package com.tm.exceptions;

public class ObjectNotFoundDAOException extends DAOException {

	public ObjectNotFoundDAOException() {
		super("Ressource not found");
	}

	private static final long serialVersionUID = 1L;

}
