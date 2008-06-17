// $Id: NoMimeTypeException.java,v 1.2 2008-06-17 13:41:13 fbatard Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.exceptions;

/**
 * NoMimeTypeException<br />
 * Created: Jul 7, 2006 5:30:51 PM<br />
 * 
 * @author Jean-Guilhem Rouel Exception that occurs when mime-type isn't
 *         specified
 */
public class NoMimeTypeException extends Exception {

	private static final long serialVersionUID = -1030043318614100474L;

	public NoMimeTypeException() {
		super();
	}

	public NoMimeTypeException(String message) {
		super(message);
	}
}
