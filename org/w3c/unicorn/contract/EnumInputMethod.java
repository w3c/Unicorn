// $Id: EnumInputMethod.java,v 1.1.1.1 2006-08-31 09:09:20 dleroy Exp $
// Author: Damien LEROY
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.contract;

/**
 * @author Damien LEROY
 */
public enum EnumInputMethod {

	URI("uri"),
	UPLOAD("upload"),
	DIRECT("direct");

	private final String sValue;
	
	private EnumInputMethod (final String sValue) {
		this.sValue = sValue;
	}
	
	public final String value () {
		return this.sValue;
	}
	
	public static EnumInputMethod fromValue (final String sValue) {
		for (final EnumInputMethod aEnumInputMethod : EnumInputMethod.values()) {
			if (aEnumInputMethod.sValue.equals(sValue)) {
				return aEnumInputMethod;
			}
		}
		throw new IllegalArgumentException("Unknow input method : "+sValue.toString()+".");
	}

}
