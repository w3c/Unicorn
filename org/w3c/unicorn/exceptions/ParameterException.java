// $Id: ParameterException.java,v 1.1.1.1 2006-08-31 09:09:21 dleroy Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.exceptions;

/**
 * ParameterException<br />
 * Created: Jun 9, 2006 5:35:55 PM<br />
 */
public class ParameterException extends Exception {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6882194955581911106L;

	/**
	 * Creates a new ParameterException.
	 * @param string
	 */
	public ParameterException(String string) {
		super(string);
	}

}
