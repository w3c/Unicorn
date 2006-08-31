// $Id: EnumOutputModule.java,v 1.1.1.1 2006-08-31 09:09:25 dleroy Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

/**
 * @author Damien LEROY
 *
 */
public enum EnumOutputModule {

	SIMPLE("simple"),
	MAIL("mail");

	private final String sValue;

	private EnumOutputModule (final String sValue) {
		this.sValue = sValue;
	}

	public final String value () {
		return this.sValue;
	}

	public static EnumOutputModule fromValue (final String sValue) {
		for (final EnumOutputModule aEnumOutputMethod : EnumOutputModule.values()) {
			if (aEnumOutputMethod.sValue.equals(sValue)) {
				return aEnumOutputMethod;
			}
		}
		return null;
	}

}
