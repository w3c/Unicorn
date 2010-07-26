// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist.parameters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3.unicorn.tasklist.TParamType;

/**
 * Factory to create any type of parameter.
 * 
 * @author Damien LEROY
 */
public class ParameterFactory {

	private static final Log logger = LogFactory
			.getLog("org.w3c.unicorn.tasklist");

	public static Parameter getParameter(final TParamType.Enum aTParamType) {
		switch (aTParamType.intValue()) {
		case TParamType.INT_CHECKBOX:
			return new CheckboxParameter();
		case TParamType.INT_CHECKBOXLIST:
			return new CheckboxListParameter();
		case TParamType.INT_DROPDOWN:
			return new DropDownParameter();
		case TParamType.INT_DROPDOWNLIST:
			return new DropdownListParameter();
		case TParamType.INT_RADIO:
			return new RadioParameter();
		case TParamType.INT_TEXTAREA:
			return new TextAreaParameter();
		case TParamType.INT_TEXTFIELD:
			return new TextFieldParameter();
		default:
			ParameterFactory.logger.error("Unknown parameter type.");
			return null;
		}
	}

}
