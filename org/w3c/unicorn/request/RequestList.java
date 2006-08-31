// $Id: RequestList.java,v 1.1.1.1 2006-08-31 09:09:25 dleroy Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.generated.tasklist.TPriority;

/**
 * @author Damien LEROY
 *
 */
public interface RequestList {

	public static final Log logger = LogFactory.getLog("org.w3c.unicorn.request");

	public abstract Request getRequest (final String sObserverID);

	public abstract Map<String, Request> getRequest (final TPriority aTPriority);

	public abstract Request getRequest (final TPriority aTPriority, final String sObserverName);

	public abstract void addRequest (
			final Request aRequest,
			final TPriority aTPriority,
			final String sObserverID);

}
