// $Id: NoMimeTypeException.java,v 1.5 2009-08-11 13:43:03 jean-gui Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.exceptions;

/**
 * NoMimeTypeException<br />
 * Created: Jul 7, 2006 5:30:51 PM<br />
 * Exception that occurs when mime-type isn't specified
 * 
 * @author Jean-Guilhem Rouel
 * 
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
