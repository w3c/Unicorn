// $Id: URIRequest.java,v 1.9 2009-08-11 13:43:00 jean-gui Exp $
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
import org.w3c.unicorn.input.InputModule;
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
			final InputModule aInputModule, final String responseType)
			throws IOException {
		super();
		Request.logger.trace("Constructor");
		if (Request.logger.isDebugEnabled()) {
			Request.logger.debug("URL : " + sURL + ".");
			Request.logger.debug("Input parameter name : "
					+ sInputParameterName + ".");
			Request.logger.debug("Input module : " + aInputModule + ".");
		}
		if (!(aInputModule instanceof URIInputModule)) {
			throw new IllegalArgumentException("InputModule : "
					+ aInputModule.toString() + ".");
		}
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
	public void addParameter(final String sName, final String sValue)
			throws UnsupportedEncodingException {
		Request.logger.trace("addParameter");
		if (Request.logger.isDebugEnabled()) {
			Request.logger.debug("sName : " + sName + ".");
			Request.logger.debug("sValue : " + sValue + ".");
		}
		if (null == this.sParameter) {
			this.sParameter = "";
		} else {
			this.sParameter += "&";
		}
		this.sParameter += sName + "=" + URLEncoder.encode(sValue, "UTF-8");
		Request.logger.debug("Parameters : " + this.sParameter + ".");
	}

	/**
	 * Do the request to the observer
	 * 
	 * @throws IOException
	 *             odd error occured
	 */
	@Override
	public Response doRequest() throws IOException {
		Request.logger.trace("doRequest");
		if (Request.logger.isDebugEnabled()) {
			Request.logger.debug("URL : " + this.sURL + " .");
			Request.logger.debug("Parameters : " + this.sParameter + " .");
		}
		final URL aURL;
		if (null == this.sParameter) {
			aURL = new URL(this.sURL);
		} else {
			Request.logger.debug(this.sParameter);
			aURL = new URL(this.sURL + "?" + this.sParameter);
		}
		Request.logger.debug("URL : " + aURL + " .");
		final URLConnection aURLConnection = aURL.openConnection();

		aURLConnection.setRequestProperty("Accept-Language", this.sLang);
		InputStream is = aURLConnection.getInputStream();
		return streamToResponse(is);
	}

	@Override
	public EnumInputMethod getInputMethod() {
		Request.logger.trace("getInputMethod");
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
