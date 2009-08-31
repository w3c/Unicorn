// $Id: NoTaskException.java,v 1.2 2009-08-28 12:40:02 jean-gui Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.exceptions;

/**
 * NoTaskException<br />
 * Created: Jul 7, 2006 5:43:15 PM<br />
 * Exception used when the user didn't specify the task to perform
 * 
 * @author Jean-Guilhem Rouel
 * 
 */
public class NoTaskException extends Exception {

	private static final long serialVersionUID = -7057542417180953708L;

	public NoTaskException() {
		this("No task chosen");
	}

	public NoTaskException(String message) {
		super(message);
	}
}