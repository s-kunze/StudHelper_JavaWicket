package de.kunze.studhelper.rest.exception;

public class NoUserFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoUserFoundException() {
		super();
	}
	
	public NoUserFoundException(String msg) {
		super(msg);
	}
	
}
