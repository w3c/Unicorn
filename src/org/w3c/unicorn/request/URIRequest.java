// $Id: URIRequest.java,v 1.5 2009-09-17 16:37:18 tgambet Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.input.URIInputModule;
import org.w3c.unicorn.response.Response;

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
			URIInputModule aInputModule, final String responseType) {
		super();
		logger.trace("Constructor");
		logger.debug("URL : " + sURL + ".");
		logger.debug("Input parameter name : " + sInputParameterName + ".");
		logger.debug("Input module : " + aInputModule + ".");
		/*if (!(aInputModule instanceof URIInputModule)) {
			throw new IllegalArgumentException("InputModule : "
					+ aInputModule.toString() + ".");
		}*/
		this.sURL = sURL;
		final URIInputModule aURIInputModule = (URIInputModule) aInputModule;
		this.addParameter(sInputParameterName, aURIInputModule.getURI());
		this.setResponseType(responseType);
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
	public Response doRequest() throws Exception {
		logger.trace("doRequest");
		logger.debug("URL : " + this.sURL + " .");
		logger.debug("Parameters : " + this.sParameter + " .");
		final URL aURL;
		if (null == this.sParameter) {
			aURL = new URL(this.sURL);
		} else {
			logger.debug(this.sParameter);
			aURL = new URL(this.sURL + "?" + this.sParameter);
		}
		logger.debug("URL : " + aURL + " .");
		final URLConnection aURLConnection = aURL.openConnection();

		aURLConnection.setRequestProperty("Accept-Language", this.sLang);
		InputStream is = aURLConnection.getInputStream();
		Response response = streamToResponse(is);
		
		response.setRequestUri(aURL.toString());
		
		return response;
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
