// $Id: ParameterType.java,v 1.1.1.1 2006-08-31 09:09:27 dleroy Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist.parameters;

/**
 * ParameterType<br />
 * Created: Jun 22, 2006 10:39:41 AM<br />
 * @author Jean-Guilhem Rouel
 */
public enum ParameterType {
	
	CHECKBOX("checkbox"),
	CHECKBOXLIST("checkboxlist"),
	DROPDOWN("dropdownlist"),
	RADIO("radio"),
	TEXTAREA("textarea"),
	TEXTFIELD("textfield"),
	UNKNOWN("unknown");
	
	private final String sValue;
	
	private ParameterType (final String sValue) {
		this.sValue = sValue;
	}
	
	public final String value () {
		return this.sValue;
	}
	
	public static ParameterType fromValue (final String sValue) {
		for (final ParameterType aParameterType : ParameterType.values()) {
			if (aParameterType.sValue.equals(sValue)) {
				return aParameterType;
			}
		}
		throw new IllegalArgumentException("Parameter type "+sValue.toString()+" unknow.");
	}
	
}
