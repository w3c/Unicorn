// $Id: RDFUnmarshaller.java,v 1.2 2009-08-28 12:39:54 jean-gui Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist;

import org.w3c.unicorn.util.Unmarshaller;

/**
 * @author Damien LEROY
 * 
 */
public interface RDFUnmarshaller extends Unmarshaller {

	public abstract Tasklist getMapOfTask();

	public abstract void setMapOfTask(final Tasklist mapOfTask);

}
