// $Id: CallMethod.java,v 1.1.1.1 2006-08-31 09:09:20 dleroy Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.contract;

import java.net.URL;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * CallMethod<br />
 * Created: May 23, 2006 11:46:50 AM<br />
 */
public class CallMethod {

	private static final Log logger = LogFactory.getLog("org.w3c.unicorn.contract");

	private URL aURL;

	// GET or POST
	private String sName;
	private String sID;
	private boolean bPost;
	private Map<String, CallParameter> mapOfCallParameter;

	public CallMethod (
			final URL aURL,
			final boolean bPost,
			final String sName,
			final String sID,
			final Map<String, CallParameter> mapOfCallParameter) {
		CallMethod.logger.trace("Constructor");
		if (CallMethod.logger.isDebugEnabled()) {
			CallMethod.logger.debug("URL : " + aURL + ".");
			CallMethod.logger.debug("Post : " + bPost + ".");
			CallMethod.logger.debug("Name : " + sName + ".");
			CallMethod.logger.debug("ID : " + sID + ".");
			CallMethod.logger.debug("Map of call parameter : " + mapOfCallParameter + ".");
		}

		this.aURL = aURL;
		this.mapOfCallParameter = mapOfCallParameter;
		this.bPost = bPost;
		this.sName = sName;
		this.sID = sID;
	}

	/**
	 * @return Returns the parameters.
	 */
	public Map<String, CallParameter> getMapOfCallParameter () {
		return this.mapOfCallParameter;
	}

	
	public void addParameter (final CallParameter aCallParameter) {
		this.mapOfCallParameter.put(aCallParameter.getName(), aCallParameter);
	}

	/**
	 * @param sName
	 * @return
	 */
	public CallParameter getCallParameterByName (final String sName) {
		return this.mapOfCallParameter.get(sName);
	}

	/**
	 * @return Returns the post.
	 */
	public boolean isPost () {return this.bPost;}

	/**
	 * @return Returns the uri.
	 */
	public URL getURL () {return this.aURL;}

	/**
	 * @return Returns the id.
	 */
	public String getID () {return this.sID;}
	public String getName () {return this.sName;}

	/**
	 * {@inheritDoc}
	 */
	public String toString () {
		final int iSize = 1000;
		final String sVariableSeparator = "\n";
		final StringBuffer aStringBuffer = new StringBuffer(iSize);

		aStringBuffer.append("uri=").append(this.aURL);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("id=").append(this.sID);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("post=").append(this.bPost);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("parameters=\n");
		aStringBuffer.append(this.mapOfCallParameter).append(sVariableSeparator);

		return aStringBuffer.toString();
	}

}
