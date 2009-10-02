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
	private boolean evaluateContent = false;
	
	public Message() {
	}
	
	public Message(Exception e) {
		content = e.getClass() + "\n";
		content += e.getMessage() + "\n";
		for (StackTraceElement stackTraceElement : e.getStackTrace()) {
			content += stackTraceElement.toString() + "\n";
		}
		level = Level.ERROR;
		message = "$stack_trace_text";
		evaluateContent = false;
	}
	
	public Message(Level level, String message, String content) {
		this.level = level;
		this.message = message;
		this.content = content;
		evaluateContent = true;
	}
	
	public Message(Level level, String message) {
		this.level = level;
		this.message = message;
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

	public boolean doEvaluateContent() {
		return evaluateContent;
	}

	public void setEvaluateContent(boolean evaluateContent) {
		this.evaluateContent = evaluateContent;
	}
	
}
