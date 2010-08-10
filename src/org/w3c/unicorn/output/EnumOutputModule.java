// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

/**
 * @author Damien LEROY
 * 
 */
public enum EnumOutputModule {

	SIMPLE("simple"), MAIL("mail");

	private final String sValue;

	private EnumOutputModule(final String sValue) {
		this.sValue = sValue;
	}

	public final String value() {
		return this.sValue;
	}

	/**
	 * Returns if the string is an output method possible value
	 * 
	 * @param sValue
	 *            string to compare
	 * @return the output method if matches and null otherwise
	 */
	public static EnumOutputModule fromValue(final String sValue) {
		for (final EnumOutputModule aEnumOutputMethod : EnumOutputModule
				.values()) {
			if (aEnumOutputMethod.sValue.equals(sValue)) {
				return aEnumOutputMethod;
			}
		}
		return null;
	}

}
