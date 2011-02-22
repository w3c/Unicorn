// Author: Damien LEROY
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.contract;

/**
 * Enumeration for the input methods
 * 
 * @author Damien LEROY
 */
public enum EnumInputMethod {

	URI("uri"), DIRECT("direct"), UPLOAD("upload");

	private final String sValue;

	private EnumInputMethod(final String sValue) {
		this.sValue = sValue;
	}

	public final String value() {
		return this.sValue;
	}

	public static EnumInputMethod fromValue(final String sValue) {
		for (final EnumInputMethod aEnumInputMethod : EnumInputMethod.values()) {
			if (aEnumInputMethod.sValue.equals(sValue)) {
				return aEnumInputMethod;
			}
		}
		throw new IllegalArgumentException("Unknow input method : "
				+ sValue.toString() + ".");
	}

}
