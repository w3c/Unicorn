package org.w3c.unicorn.exceptions;

import org.w3c.unicorn.util.Message;

public class UnicornException extends Exception {
	
	private static final long serialVersionUID = 3302368429707755988L;

	private Message message;
	
	public UnicornException() {
		super();
	}
	
	public UnicornException(String string) {
		super(string);
	}
	
	public UnicornException(Message message) {
		this.message = message;
	}

	public Message getUnicornMessage() {
		return message;
	}

	public void setUnicornMessage(Message message) {
		this.message = message;
	}
	
}
