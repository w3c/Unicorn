// $Id: NoDocumentException.java,v 1.2 2008-06-17 13:41:13 fbatard Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.exceptions;

/**
 * NoDocumentException<br />
 * Created: Jul 7, 2006 5:26:38 PM<br />
 * 
 * @author Jean-Guilhem Rouel Exception used when there is no document to
 *         analyze
 */
public class NoDocumentException extends Exception {

	private static final long serialVersionUID = -4395530018963595605L;

	public NoDocumentException() {
		super();
	}

	public NoDocumentException(String message) {
		super(message);
	}
}
