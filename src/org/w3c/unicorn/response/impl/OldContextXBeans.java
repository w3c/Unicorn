package org.w3c.unicorn.response.impl;

import org.w3c.unicorn.response.Context;

public class OldContextXBeans implements Context {

	private String context;
	private Integer line;
	private Integer column;
	
	public OldContextXBeans(String context, Integer line, Integer column) {
		this.context = context;
		this.line = line;
		this.column = column;
	}
	
	public Integer getColumn() {
		return column;
	}

	public Integer getColumnMax() {
		return null;
	}

	public Integer getColumnMin() {
		return null;
	}

	public String getContext() {
		return context;
	}

	public Integer getLine() {
		return line;
	}

	public Integer getLineMax() {
		return null;
	}

	public Integer getLineMin() {
		return null;
	}

	public Integer getOffset() {
		return null;
	}

	public String getPosition() {
		return null;
	}

	public String getURI() {
		return null;
	}

}
