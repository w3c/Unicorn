// $Id: WADLUnmarshaller.java,v 1.1.1.1 2006-08-31 09:09:21 dleroy Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.contract;

import java.util.List;

import org.w3c.unicorn.util.Unmarshaller;

/**
 * WADLUnmarshallerXPath<br />
 * Created: May 24, 2006 12:34:38 PM<br />
 */
public interface WADLUnmarshaller extends Unmarshaller {

	/**
	 * @return Returns a list of methods.
	 */
	public abstract List<CallMethod> getListOfCallMethod();

}