// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Damien LEROY
 * 
 */
public class RequestList {

	/**
	 * Object used for complex logging purpose
	 */
	private static final Log logger = LogFactory.getLog(RequestList.class);

	/**
	 * Language of the list
	 */
	private String sLang = null;

	/**
	 * Constructor of the list
	 * 
	 * @param sLang
	 *            language of the list
	 */
	public RequestList(final String sLang) {
		logger.debug("Lang : " + sLang + ".");
		this.sLang = sLang;
	}

	/**
	 * Map of request about the observer who handle the current mime type with a
	 * LOW priority.
	 */
	private final Map<String, Request> mapOfRequest = new LinkedHashMap<String, Request>();

	/**
	 * 
	 * @return
	 */
	public Map<String, Request> getRequestMap() {
		logger.trace("getRequestMap");
		return this.mapOfRequest;
	}

	/**
	 * Gives an observer placed in the map
	 * 
	 * @param String
	 *            sNodeID the ID of the node into which we'll search the Request
	 * @return
	 */
	public Request getRequest(final String sNodeID) {
		logger.trace("getRequest");
		logger.debug("Observer ID : " + sNodeID + ".");
		return this.mapOfRequest.get(sNodeID);
	}

	/**
	 * Adds a request to the map
	 * 
	 * @param aRequest
	 * @param sNodeID
	 *            The ID of the corresponding node
	 * @throws IOException
	 */

	public void addRequest(final Request aRequest, final String sNodeID) {
		logger.trace("addRequest");
		logger.debug("Request : " + aRequest + ".");
		logger.debug("String node ID : " + sNodeID + ".");
		aRequest.setLang(this.sLang);
		this.mapOfRequest.put(sNodeID, aRequest);
	}

	/**
	 * Prints the object
	 */
	@Override
	public String toString() {
		final int iStringBufferSize = 1000;
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append(mapOfRequest);
		return aStringBuffer.toString();
	}

}
