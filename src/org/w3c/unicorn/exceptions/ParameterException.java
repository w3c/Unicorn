// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.exceptions;

/**
 * ParameterException<br />
 * Created: Jun 9, 2006 5:35:55 PM<br />
 * Exception used when the parameters aren't well formed
 */
public class ParameterException extends Exception {

	private static final long serialVersionUID = 6882194955581911106L;

	public ParameterException(String string) {
		super(string);
	}

}
