// $Id: CallMethod.java,v 1.3 2008-06-17 14:09:50 fbatard Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.contract;

import java.net.URL;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * CallMethod Created: May 23, 2006 11:46:50 AM
 */
public class CallMethod {

	/**
	 * Object for complex logging purpose
	 */
	private static final Log logger = LogFactory
			.getLog("org.w3c.unicorn.contract");

	/**
	 * URL for the call
	 */
	private URL aURL;

	// GET or POST
	/**
	 * The url to call
	 */
	private String sName;

	/**
	 * The name to call
	 */
	private String sID;

	/**
	 * the ID of the call
	 */
	private boolean bPost;

	/**
	 * whether the call is sent or not
	 */
	private Map<String, CallParameter> mapOfCallParameter;

	/**
	 * Set the parameter for the object call
	 * 
	 * @param aURL
	 *            the url to call
	 * @param bPost
	 *            whether or not the call is post
	 * @param sName
	 *            the name to call
	 * @param sID
	 *            the id of the call
	 * @param mapOfCallParameter
	 *            the parameter of the call
	 */
	public CallMethod(final URL aURL, final boolean bPost, final String sName,
			final String sID,
			final Map<String, CallParameter> mapOfCallParameter) {
		CallMethod.logger.trace("Constructor");
		if (CallMethod.logger.isDebugEnabled()) {
			CallMethod.logger.debug("URL : " + aURL + ".");
			CallMethod.logger.debug("Post : " + bPost + ".");
			CallMethod.logger.debug("Name : " + sName + ".");
			CallMethod.logger.debug("ID : " + sID + ".");
			CallMethod.logger.debug("Map of call parameter : "
					+ mapOfCallParameter + ".");
		}

		this.aURL = aURL;
		this.mapOfCallParameter = mapOfCallParameter;
		this.bPost = bPost;
		this.sName = sName;
		this.sID = sID;
	}

	/**
	 * Returns all the parameters of the call
	 * 
	 * @return Returns the parameters.
	 */
	public Map<String, CallParameter> getMapOfCallParameter() {
		return this.mapOfCallParameter;
	}

	/**
	 * Add a parameter to the call
	 * 
	 * @param aCallParameter
	 *            a parameter to add
	 */
	public void addParameter(final CallParameter aCallParameter) {
		this.mapOfCallParameter.put(aCallParameter.getName(), aCallParameter);
	}

	/**
	 * Gets the parameter by its name
	 * 
	 * @param sName
	 *            name of the parameter to get
	 * @return the Call parameter researched
	 */
	public CallParameter getCallParameterByName(final String sName) {
		return this.mapOfCallParameter.get(sName);
	}

	/**
	 * Returns if the call is post
	 * 
	 * @return Returns the post.
	 */
	public boolean isPost() {
		return this.bPost;
	}

	/**
	 * Returns the URI
	 * 
	 * @return Returns the uri.
	 */
	public URL getURL() {
		return this.aURL;
	}

	/**
	 * Returns the ID
	 * 
	 * @return Returns the id.
	 */
	public String getID() {
		return this.sID;
	}

	/**
	 * Get the name of the call
	 * 
	 * @return the name of the call
	 */
	public String getName() {
		return this.sName;
	}

	/**
	 * Print the object
	 */
	public String toString() {
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
		aStringBuffer.append(this.mapOfCallParameter)
				.append(sVariableSeparator);

		return aStringBuffer.toString();
	}

}
