package org.w3c.unicorn.exceptions;

public class InitializationFailedException extends Exception {

	private static final long serialVersionUID = -5495122008977532810L;
	
	public InitializationFailedException() {
		super();
	}

	public InitializationFailedException(final String message) {
		super(message);
	}

}
