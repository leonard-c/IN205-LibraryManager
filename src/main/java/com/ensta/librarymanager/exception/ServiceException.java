package com.ensta.librarymanager.exception;

@SuppressWarnings("serial")
public class ServiceException extends Exception {
	
	public ServiceException() {
		super("Une erreur s'est produite dans le Service.");
	}

}
