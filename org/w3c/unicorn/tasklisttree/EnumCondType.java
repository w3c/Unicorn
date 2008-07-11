package org.w3c.unicorn.tasklisttree;

public enum EnumCondType {

	XPATH("uri"), MIMETYPE("mimetype");

	private final String sValue;

	private EnumCondType(final String sValue) {
		this.sValue = sValue;
	}

	public final String value() {
		return this.sValue;
	}
	
}