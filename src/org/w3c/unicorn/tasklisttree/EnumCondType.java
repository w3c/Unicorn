// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklisttree;

public enum EnumCondType {

    XPATH("xpath"), MIMETYPE("mimetype"), PARAMETER("parameter"), METHOD("method");

	private final String sValue;

	private EnumCondType(final String sValue) {
		this.sValue = sValue;
	}

	public final String value() {
		return this.sValue;
	}
	
	public static EnumCondType fromValue(final String sValue) {
		for (final EnumCondType aEnumCondType : EnumCondType.values()) {
			if (aEnumCondType.sValue.equals(sValue)) {
				return aEnumCondType;
			}
		}
		return null;
	}

}
