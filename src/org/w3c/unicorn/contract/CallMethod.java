// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.contract;

import java.net.URL;
import java.util.ArrayList;

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
			.getLog(CallMethod.class);

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
	ArrayList<CallParameter> callParameters;
	
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
			final ArrayList<CallParameter> callParameters) {
		logger.trace("Constructor\n" +
				     "URL : " + aURL + ".\n" +
				     "Post : " + bPost + ".\n" +
				     "Name : " + sName + ".\n" +
				     "ID : " + sID + ".\n" +
				     "Map of call parameter : " + callParameters + ".");

		this.aURL = aURL;
		this.callParameters = callParameters;
		this.bPost = bPost;
		this.sName = sName;
		this.sID = sID;
	}

	/**
	 * Returns all the parameters of the call
	 * 
	 * @return Returns the parameters.
	 */
	public ArrayList<CallParameter> getListOfCallParameter() {
		return this.callParameters;
	}

	/**
	 * Add a parameter to the call
	 * 
	 * @param aCallParameter
	 *            a parameter to add
	 */
	public void addParameter(final CallParameter aCallParameter) {
		this.callParameters.add(aCallParameter);
	}

	/**
	 * Gets the parameter by its name
	 * 
	 * @param sName
	 *            name of the parameter to get
	 * @return the Call parameter researched
	 */
	public CallParameter getCallParameterByName(final String sName) {
		for (CallParameter param : callParameters)
			if (param.getName().equals(sName))
				return param;
		return null;
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
	@Override
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
		aStringBuffer.append(this.callParameters)
				.append(sVariableSeparator);

		return aStringBuffer.toString();
	}

}
