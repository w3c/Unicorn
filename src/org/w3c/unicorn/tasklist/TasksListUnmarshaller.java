// $Id: TasksListUnmarshaller.java,v 1.2 2009-08-28 12:39:54 jean-gui Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist;

import org.w3c.unicorn.util.Unmarshaller;

/**
 * TasksListUnmarshaller<br />
 * Created: May 30, 2006 5:54:45 PM<br />
 * Interface used to retreive a list of tasks
 * 
 * @author Jean-Guilhem ROUEL
 */
public interface TasksListUnmarshaller extends Unmarshaller {

	/**
	 * Returns the map of tasks.
	 * 
	 * @return Returns the tasks.
	 */
	public abstract Tasklist getMapOfTask();

}