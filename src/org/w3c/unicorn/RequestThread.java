package org.w3c.unicorn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.event.EventCartridge;
import org.w3c.unicorn.exceptions.UnicornException;
import org.w3c.unicorn.request.Request;
import org.w3c.unicorn.response.Response;
import org.w3c.unicorn.response.parser.ResponseParserFactory;
import org.w3c.unicorn.util.EscapeXMLEntities;
import org.w3c.unicorn.util.Message;
import org.w3c.unicorn.util.Templates;

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
	private static final Log logger = LogFactory.getLog(RequestThread.class);;
	
	private Response aResponse;

	/**
	 * The request to make
	 */
	private Request aRequest;

	/**
	 * ID of the Observer
	 */
	private String obsID;

	private String lang;
	
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
			String obsID,
			String lang) {
		this.aRequest = aRequest;
		this.obsID = obsID;
		this.lang = lang;
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
		} catch (final Exception e) {
			logger.error("Exception : " + e.getMessage(), e);
			try {
				StringBuilder builder = new StringBuilder();
				VelocityContext context = new VelocityContext(Framework.getLanguageContexts().get(lang));
				EventCartridge aEventCartridge = new EventCartridge();
				aEventCartridge.addEventHandler(new EscapeXMLEntities());
				aEventCartridge.attachToContext(context);
				
				if (e.getMessage() != null)	
					context.put("exception", e.getMessage());
				else
					context.put("exception", "");
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				Templates.write("io_error.vm", context, osw);
				osw.close();
				InputStreamReader isr = new InputStreamReader(
						new ByteArrayInputStream(os.toByteArray()));
				char[] chararray = new char[8192];
				int readLength = 0;
				while ((readLength = isr.read(chararray, 0, 8192)) > -1) {
					builder.append(chararray, 0, readLength);
				}
				aResponse = ResponseParserFactory.parse(builder.toString(), "default");				
				aResponse.setXml(builder);				
			} catch (IOException e1) {
				logger.error("Exception : " + e1.getMessage(), e1);
				e1.printStackTrace();
			} catch (Exception e1) {
				logger.error("Exception : " + e1.getMessage(), e1);
				e1.printStackTrace();
			}
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

