// $Id: RequestListImpl.java,v 1.1.1.1 2006-08-31 09:09:25 dleroy Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.unicorn.generated.tasklist.TPriority;

/**
 * @author Damien LEROY
 *
 */
public class RequestListImpl implements RequestList {

	private String sLang = null;

	public RequestListImpl (final String sLang) {
		this.sLang = sLang;
	}
	
	/**
	 * Map of request about the observer who handle the current mime type with a LOW priority.
	 */
	protected final Map<String, Request> mapOfRequestLOW = new LinkedHashMap<String, Request>();

	/**
	 * Map of request about the observer who handle the current mime type with a MEDIUM priority.
	 */
	protected final Map<String, Request> mapOfRequestMEDIUM = new LinkedHashMap<String, Request>();

	/**
	 * Map of request about the observer who handle the current mime type with a HIGH priority.
	 */
	protected final Map<String, Request> mapOfRequestHIGH = new LinkedHashMap<String, Request>();

	public Request getRequest (final String sObserverID) {
		RequestListImpl.logger.trace("getRequest");
		if (RequestListImpl.logger.isDebugEnabled()) {
			RequestListImpl.logger.debug("Observer ID : " + sObserverID + ".");
		}
		Request aRequest = null;
		aRequest = this.mapOfRequestHIGH.get(sObserverID);
		if (null != aRequest) return aRequest;
		aRequest = this.mapOfRequestMEDIUM.get(sObserverID);
		if (null != aRequest) return aRequest;
		return this.mapOfRequestLOW.get(sObserverID);
	}

	public Map<String, Request> getRequest (final TPriority aTPriority) {
		RequestListImpl.logger.trace("getRequest");
		if (RequestListImpl.logger.isDebugEnabled()) {
			RequestListImpl.logger.debug("Priority : " + aTPriority + ".");
		}
		switch (aTPriority) {
			case HIGH :
				return this.mapOfRequestHIGH;
			case MEDIUM :
				return this.mapOfRequestMEDIUM;
			case LOW :
				return this.mapOfRequestLOW;
		}
		return null;
	}

	public Request getRequest (final TPriority aTPriority, final String sObserverID) {
		RequestListImpl.logger.trace("getRequest");
		if (RequestListImpl.logger.isDebugEnabled()) {
			RequestListImpl.logger.debug("Priority : " + aTPriority + ".");
			RequestListImpl.logger.debug("Observer ID : " + sObserverID + ".");
		}
		return this.getRequest(aTPriority).get(sObserverID);
	}

	public void addRequest(
			final Request aRequest,
			final TPriority aTPriority,
			final String sObserverID) {
		RequestListImpl.logger.trace("addRequest");
		if (RequestListImpl.logger.isDebugEnabled()) {
			RequestListImpl.logger.debug("Request : " + aRequest + ".");
			RequestListImpl.logger.debug("TPriority : " + aTPriority + ".");
			RequestListImpl.logger.debug("String observer ID : " + sObserverID + ".");
		}
		aRequest.setLang(this.sLang);
		switch (aTPriority) {
			case HIGH :
				this.mapOfRequestHIGH.put(sObserverID, aRequest);
				return;
			case MEDIUM :
				this.mapOfRequestMEDIUM.put(sObserverID, aRequest);
				return;
			case LOW :
				this.mapOfRequestLOW.put(sObserverID, aRequest);
				return;
		}
	}

	public String toString () {
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
