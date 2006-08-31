// $Id: ParameterFactory.java,v 1.1.1.1 2006-08-31 09:09:27 dleroy Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist.parameters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.generated.tasklist.TParamType;

/**
 * Factory to create any type of parameter.
 * @author Damien LEROY
 */
public class ParameterFactory {

	private static final Log logger = LogFactory.getLog("org.w3c.unicorn.tasklist");

	public static Parameter getParameter (final TParamType aTParamType) {

		switch (aTParamType) {
			case CHECKBOX :
				return new CheckboxParameter();
			case CHECKBOXLIST :
				return new CheckboxListParameter();			
			case DROPDOWN :
				return new DropDownParameter();
			case RADIO :
				return new RadioParameter();
			case TEXTAREA :
				return new TextAreaParameter();
			case TEXTFIELD :
				return new TextFieldParameter();
			default :
				ParameterFactory.logger.error("Unknown parameter type.");
				return null;
		}
	}

}
