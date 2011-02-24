// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.util;

public class Message {
	
	public static final int INFO = 0;
	public static final int WARNING = 1;
	public static final int ERROR = 2;
	
	private String message;
	private String content;
	private int level;
	private boolean evaluateContent = false;
	private boolean isCritical = false;

	private String[] parameters;
	
	public Message() {
	}
	
	public Message(Exception e) {
		content = e.getClass() + "\n";
		content += e.getMessage() + "\n";
		for (StackTraceElement stackTraceElement : e.getStackTrace()) {
			content += stackTraceElement.toString() + "\n";
		}
		level = ERROR;
		message = "$stack_trace_text";
		evaluateContent = false;
		isCritical = true;
	}
	
	public Message(int level, String message, String content, String... parameters) {
		this.level = level;
		this.message = message;
		this.content = content;
		evaluateContent = true;
		this.parameters = parameters;
	}
	
	public Message(int level, String message) {
		this.level = level;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String getMessage(String lang) {
		return Language.evaluate(lang, message, parameters);
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean doEvaluateContent() {
		return evaluateContent;
	}

	public void setEvaluateContent(boolean evaluateContent) {
		this.evaluateContent = evaluateContent;
	}
	
	public boolean isCritical() {
		return isCritical;
	}
	
}
