package com.ensta.librarymanager.exception;

public class DaoException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DaoException() {
		super("Une erreur s'est produite dans le DAO.");
	}
	
}
