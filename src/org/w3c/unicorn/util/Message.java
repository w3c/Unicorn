package org.w3c.unicorn.util;

public class Message {
	
	public enum Level {
		ERROR,
		WARNING,
		INFO;
	}
	
	private String message;
	private String content;
	private Level level;
	
	public Message() {
	}
	
	public Message(Level level, String message, String content) {
		this.level = level;
		this.message = message;
		this.content = content;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}
	
	
}
