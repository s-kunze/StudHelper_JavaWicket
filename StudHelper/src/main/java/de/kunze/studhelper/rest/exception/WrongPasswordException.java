package de.kunze.studhelper.rest.exception;

public class WrongPasswordException extends Exception {

	private static final long serialVersionUID = 1L;

	public WrongPasswordException() {
		super();
	}

	public WrongPasswordException(String msg) {
		super(msg);
	}

}
