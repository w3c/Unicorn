package org.w3c.unicorn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.event.EventCartridge;
import org.w3c.unicorn.request.Request;
import org.w3c.unicorn.response.Response;
import org.w3c.unicorn.response.parser.ResponseParserFactory;
import org.w3c.unicorn.util.EscapeXMLEntities;
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

	/**
	 * Data Structure for the responses
	 */
	//private Map<String, Response> mapOfResponse;
	
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

	/**
	 * The call to perform
	 */
	//private UnicornCall unicornCall;

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
	public RequestThread(//Map<String, Response> mapOfResponse, 
			Request aRequest,
			String obsID,
			//UnicornCall unicorn,
			String lang) {
		//this.mapOfResponse = mapOfResponse;
		this.aRequest = aRequest;
		this.obsID = obsID;
		this.lang = lang;
		//this.unicornCall = unicorn;
	}

	/**
	 * Allow to launch the thread
	 */
	@Override
	public void run() {
		//Response aResponse = null;
		try {
			// Uncomment/comment next lines to test io_error
			//throw new Exception("Message test de l'exception");
			aResponse = this.aRequest.doRequest();
		} catch (final Exception e) {
			RequestThread.logger.error("Exception : " + e.getMessage(), e);
			try {
				StringBuilder builder = new StringBuilder();
				//String lang[] = unicornCall.getMapOfStringParameter().get(
				//		Property.get("UNICORN_PARAMETER_PREFIX") + "lang");
				//String lang = unicornCall.getLang();

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
			} catch (MalformedURLException e1) {
				RequestThread.logger
						.error("Exception : " + e1.getMessage(), e1);
				e1.printStackTrace();
			} catch (IOException e1) {
				RequestThread.logger
						.error("Exception : " + e1.getMessage(), e1);
				e1.printStackTrace();
			} catch (Exception e1) {
				RequestThread.logger
						.error("Exception : " + e1.getMessage(), e1);
				e1.printStackTrace();
			}
		}
		
		this.aResponse.setObserverId(obsID);
		
		RequestThread.logger.debug(obsID + " before sync mapOfResponse");
		/*synchronized (mapOfResponse) {
			mapOfResponse.put(obsID, aResponse);
		}*/
		RequestThread.logger.debug(obsID + " after sync mapOfResponse");
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

	
	
}

