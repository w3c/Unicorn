// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.exceptions.UnicornException;
import org.w3c.unicorn.request.Request;
import org.w3c.unicorn.response.Response;
import org.w3c.unicorn.util.Message;

/**
 * Thread executing a request
 * 
 * @author Damien Leroy
 * 
 */
class RequestThread extends Thread {
	/**
	 * Used for complex logging purpose
	 */
	private static final Log logger = LogFactory.getLog(RequestThread.class);
	
	private Response aResponse;

	/**
	 * The request to make
	 */
	private Request aRequest;

	/**
	 * ID of the Observer
	 */
	private String obsID;
	
	private ArrayList<Message> messages;

	/**
	 * Initialize the thread by filling the properties
	 * 
	 * @param mapOfResponse
	 *            the map of the responses
	 * @param aRequest
	 *            the request to make
	 * @param obsID
	 *            the ID of the observer
	 * @param unicorn
	 *            the unicorn call to make
	 */
	public RequestThread( 
			Request aRequest,
			String obsID) {
		this.aRequest = aRequest;
		this.obsID = obsID;
		messages = new ArrayList<Message>();
	}

	/**
	 * Allow to launch the thread
	 */
	@Override
	public void run() {
		try {
			aResponse = this.aRequest.doRequest();
			aResponse.setObserverId(obsID);
		} catch (final UnicornException e) {
			messages.add(e.getUnicornMessage());
			logger.error("Observer request failed: \n\t Request: " +  aRequest.getInputMethod().toString() + " - " + this.aRequest.toString(), e);
		}
	}

	public String getObsID() {
		return obsID;
	}

	public Response getResponse() {
		return aResponse;
	}

	public void setResponse(Response aResponse) {
		this.aResponse = aResponse;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}

}

