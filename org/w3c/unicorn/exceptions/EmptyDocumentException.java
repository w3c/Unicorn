// $Id: EmptyDocumentException.java,v 1.4 2008-06-17 14:09:50 fbatard Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.exceptions;

/**
 * EmptyDocumentException<br />
 * Created: Jul 7, 2006 5:26:56 PM<br />
 * Exception used when the document is empty
 * @author Jean-Guilhem Rouel 
 * 
 */
public class EmptyDocumentException extends Exception {

	private static final long serialVersionUID = 1785553648502514203L;

	public EmptyDocumentException() {
		super();
	}

	public EmptyDocumentException(final String message) {
		super(message);
	}
}
