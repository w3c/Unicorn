package org.w3c.unicorn.tasklisttree;

public enum EnumCondType {

	XPATH("xpath"), MIMETYPE("mimetype"), PARAMETER("parameter");

	private final String sValue;

	private EnumCondType(final String sValue) {
		this.sValue = sValue;
	}

	public final String value() {
		return this.sValue;
	}

}