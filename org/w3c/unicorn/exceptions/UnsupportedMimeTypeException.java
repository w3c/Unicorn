// $Id: UnsupportedMimeTypeException.java,v 1.1.1.1 2006-08-31 09:09:21 dleroy Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.exceptions;

/**
 * UnsupportedMimeTypeException<br />
 * Created: May 30, 2006 10:21:13 AM<br />
 * Exception used to report errors when an observer does not support a mime type
 * @author Jean-Guilhem ROUEL
 */
public class UnsupportedMimeTypeException extends Exception {
	
	private static final long serialVersionUID = -6581746591098195257L;
	
	public UnsupportedMimeTypeException(String message) {
		super("Unsupported Content-Type: " + message);
    }
}
