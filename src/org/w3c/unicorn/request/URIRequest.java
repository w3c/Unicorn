// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;

import org.w3c.unicorn.Framework;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.exceptions.UnicornException;
import org.w3c.unicorn.input.URIInputModule;
import org.w3c.unicorn.response.Response;
import org.w3c.unicorn.util.Message;
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.response.ResponseFactory;

/**
 * Use to handle a request to a observer.
 * 
 * @author Damien LEROY
 */
public class URIRequest extends Request {

	/**
	 * URL for the request
	 */
	private String sURL = null;

	/**
	 * Parameter of the request
	 */
	private String sParameter = null;
	
	private int connectTimeOut;
	private int readTimeOut;

	/**
	 * Create an URI request
	 * 
	 * @param sURL
	 *            URL for the request
	 * @param sInputParameterName
	 *            name of the parameter for the request
	 * @param aInputModule
	 *            input module for the request
	 * @param responseType
	 *            type of the response by the observer
	 * @throws IOException
	 *             odd error occured
	 */
	public URIRequest(final String sURL, final String sInputParameterName,
			URIInputModule aInputModule, final String responseType, String observerId) {
		super();
		logger.trace("Constructor");
		logger.debug("URL : " + sURL + ".");
		logger.debug("Input parameter name : " + sInputParameterName + ".");
		logger.debug("Input module : " + aInputModule + ".");
		this.sURL = sURL;
		this.observerId = observerId;
		final URIInputModule aURIInputModule = (URIInputModule) aInputModule;
		this.addParameter(sInputParameterName, aURIInputModule.getURI());
		this.setResponseType(responseType);
		if (Property.get("OBSERVER_CONNECT_TIMEOUT") != null)
			connectTimeOut = Integer.parseInt(Property.get("OBSERVER_CONNECT_TIMEOUT"));
		else 
			connectTimeOut = 0;
		if (Property.get("OBSERVER_READ_TIMEOUT") != null)
			readTimeOut = Integer.parseInt(Property.get("OBSERVER_READ_TIMEOUT"));
		else 
			readTimeOut = 0;
	}

	/**
	 * Add a parameter to the request
	 * 
	 * @param sName
	 *            name of the parameter to add
	 * @param sValue
	 *            value of the parameter to add
	 */
	@Override
	public void addParameter(final String sName, final String sValue) {
		logger.trace("addParameter");
		logger.debug("sName : " + sName + ".");
		logger.debug("sValue : " + sValue + ".");
		if (null == this.sParameter) {
			this.sParameter = "";
		} else {
			this.sParameter += "&";
		}
		try {
			this.sParameter += sName + "=" + URLEncoder.encode(sValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		logger.debug("Parameters : " + this.sParameter + ".");
	}

	/**
	 * Do the request to the observer
	 * @throws Exception 
	 */
	@Override
	public Response doRequest() throws UnicornException {
		logger.trace("doRequest");
		logger.debug("URL : " + this.sURL + " .");
		logger.debug("Parameters : " + this.sParameter + " .");
		String observerName = Framework.mapOfObserver.get(observerId).getName(sLang.split(",")[0]);
		final URL aURL;
		try {
			if (null == this.sParameter) {
				aURL = new URL(this.sURL);
			} else {
				aURL = new URL(this.sURL + "?" + this.sParameter);
			}
			logger.debug("URL : " + aURL + " .");
			
			HttpURLConnection aURLConnection = (HttpURLConnection) aURL.openConnection();
			aURLConnection.setConnectTimeout(connectTimeOut);
			aURLConnection.setReadTimeout(readTimeOut);
			aURLConnection.setRequestProperty("Accept-Language", this.sLang);
			
			aURLConnection.connect();
			int responseCode = aURLConnection.getResponseCode();
			switch (responseCode) {
			case HttpURLConnection.HTTP_NOT_FOUND:
				throw new UnicornException(Message.ERROR, "$message_observer_not_found", null, observerName, 
						aURL.toString().replaceAll("&?" + Framework.mapOfObserver.get(observerId).getParamOutputName() + "=[^&]*", ""));
			case HttpURLConnection.HTTP_INTERNAL_ERROR:
				throw new UnicornException(Message.ERROR, "$message_observer_internal_error", null, observerName,
						aURL.toString().replaceAll("&?" + Framework.mapOfObserver.get(observerId).getParamOutputName() + "=[^&]*", ""));
			}
			
			Response res = ResponseFactory.getResponse(aURLConnection.getInputStream(), responseType, aURL.toString(), aURLConnection.getContentEncoding(), observerId);
			aURLConnection.disconnect();
			return res;
		
		} catch (MalformedURLException e) {
			throw new UnicornException(new Message(e));
		} catch (ConnectException e) {
			throw new UnicornException(Message.ERROR, "$message_observer_connect_exception", null, observerName);
		} catch (SocketTimeoutException e) {
			if (e.getMessage().contains("connect timed out")) {
				throw new UnicornException(Message.ERROR, "$message_observer_connect_exception", null, observerName);
			} else if (e.getMessage().contains("Read timed out")) {
				throw new UnicornException(Message.ERROR, "$message_observer_read_timeout", null, observerName);
			} else {
				throw new UnicornException(new Message(e));
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new UnicornException(new Message(e));
		}
	}

	@Override
	public EnumInputMethod getInputMethod() {
		logger.trace("getInputMethod");
		return EnumInputMethod.URI;
	}

	/**
	 * Prints the object
	 */
	@Override
	public String toString() {
		final int iStringBufferSize = 1000;
		final String sVariableSeparator = " ";
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append("url:").append(this.sURL);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("param:").append(this.sParameter);
		return aStringBuffer.toString();
	}

}
