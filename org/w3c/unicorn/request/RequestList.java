// $Id: RequestList.java,v 1.3 2008-06-17 13:41:11 fbatard Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.generated.tasklist.TPriority;

/**
 * @author Damien LEROY
 * 
 */
public class RequestList {

	/**
	 * Object used for complex logging purpose
	 */
	private static final Log logger = LogFactory
			.getLog("org.w3c.unicorn.request");

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
		RequestList.logger.debug("Lang : " + sLang + ".");
		this.sLang = sLang;
	}

	/**
	 * Map of request about the observer who handle the current mime type with a
	 * LOW priority.
	 */
	private final Map<String, Request> mapOfRequestLOW = new LinkedHashMap<String, Request>();

	/**
	 * Map of request about the observer who handle the current mime type with a
	 * MEDIUM priority.
	 */
	private final Map<String, Request> mapOfRequestMEDIUM = new LinkedHashMap<String, Request>();

	/**
	 * Map of request about the observer who handle the current mime type with a
	 * HIGH priority.
	 */
	private final Map<String, Request> mapOfRequestHIGH = new LinkedHashMap<String, Request>();

	/**
	 * Get a request from the data structures with its ideas sorted by
	 * priorities
	 * 
	 * @param sObserverID
	 *            ID of the observer to get
	 * @return a request extracted from the maps of priorities
	 */
	public Request getRequest(final String sObserverID) {
		RequestList.logger.trace("getRequest");
		if (RequestList.logger.isDebugEnabled()) {
			RequestList.logger.debug("Observer ID : " + sObserverID + ".");
		}
		Request aRequest = null;
		aRequest = this.mapOfRequestHIGH.get(sObserverID);
		if (null != aRequest)
			return aRequest;
		aRequest = this.mapOfRequestMEDIUM.get(sObserverID);
		if (null != aRequest)
			return aRequest;
		return this.mapOfRequestLOW.get(sObserverID);
	}

	/**
	 * Gives the map of the requests depending on the priorities
	 * 
	 * @param aTPriority
	 *            priority to get
	 * @return map of the request depending on the priorities
	 */
	public Map<String, Request> getRequest(final TPriority aTPriority) {
		RequestList.logger.trace("getRequest");
		if (RequestList.logger.isDebugEnabled()) {
			RequestList.logger.debug("Priority : " + aTPriority + ".");
		}
		switch (aTPriority) {
		case HIGH:
			return this.mapOfRequestHIGH;
		case MEDIUM:
			return this.mapOfRequestMEDIUM;
		case LOW:
			return this.mapOfRequestLOW;
		}
		return null;
	}

	/**
	 * Gives an observer placed in priority list
	 * 
	 * @param aTPriority
	 *            priority into which it will search
	 * @param sObserverID
	 *            ID of the observer to get
	 * @return
	 */
	public Request getRequest(final TPriority aTPriority,
			final String sObserverID) {
		RequestList.logger.trace("getRequest");
		if (RequestList.logger.isDebugEnabled()) {
			RequestList.logger.debug("Priority : " + aTPriority + ".");
			RequestList.logger.debug("Observer ID : " + sObserverID + ".");
		}
		return this.getRequest(aTPriority).get(sObserverID);
	}

	public void addRequest(final Request aRequest, final TPriority aTPriority,
			final String sObserverID) throws IOException {
		RequestList.logger.trace("addRequest");
		if (RequestList.logger.isDebugEnabled()) {
			RequestList.logger.debug("Request : " + aRequest + ".");
			RequestList.logger.debug("TPriority : " + aTPriority + ".");
			RequestList.logger.debug("String observer ID : " + sObserverID
					+ ".");
		}
		aRequest.setLang(this.sLang);
		switch (aTPriority) {
		case HIGH:
			this.mapOfRequestHIGH.put(sObserverID, aRequest);
			return;
		case MEDIUM:
			this.mapOfRequestMEDIUM.put(sObserverID, aRequest);
			return;
		case LOW:
			this.mapOfRequestLOW.put(sObserverID, aRequest);
			return;
		}
	}

	/**
	 * Prints the object
	 */
	public String toString() {
		final int iStringBufferSize = 1000;
		final String sVariableSeparator = " ";
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append("HIGH:").append(this.mapOfRequestHIGH);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("MEDIUM:").append(this.mapOfRequestMEDIUM);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("LOW:").append(this.mapOfRequestLOW);
		return aStringBuffer.toString();
	}

}
