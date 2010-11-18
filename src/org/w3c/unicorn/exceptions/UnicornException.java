// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
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

	public UnicornException(int level, String message, String content, String... parameters) {
		this.message = new Message(level, message, content, parameters);
	}
	
	public UnicornException(int level, String message) {
		this.message = new Message(level, message);
	}

	public Message getUnicornMessage() {
		return message;
	}

	public void setUnicornMessage(Message message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		if (super.getMessage() == null || super.getMessage().equals(""))
			return message.getContent();
		else
			return super.getMessage();
	}
	
	
	
}
