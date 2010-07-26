// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.exceptions;

public class InitializationFailedException extends Exception {

	private static final long serialVersionUID = -5495122008977532810L;
	
	public InitializationFailedException() {
		super();
	}

	public InitializationFailedException(final String message) {
		super(message);
	}

	public InitializationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
