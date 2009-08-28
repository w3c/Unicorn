// $Id: NoDocumentException.java,v 1.2 2009-08-28 12:40:02 jean-gui Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.exceptions;

/**
 * NoDocumentException<br />
 * Created: Jul 7, 2006 5:26:38 PM<br />
 * Exception used when there is no document to analyze
 * 
 * @author Jean-Guilhem Rouel
 * 
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
