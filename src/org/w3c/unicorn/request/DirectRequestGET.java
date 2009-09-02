// $Id: DirectRequestGET.java,v 1.3 2009-09-02 12:41:28 tgambet Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.input.DirectInputModule;
import org.w3c.unicorn.input.InputModule;
import org.w3c.unicorn.response.Response;
import org.w3c.unicorn.util.Property;

/**
 * Class to make a request directly using GET method
 * 
 * @author Damien LEROY
 */
public class DirectRequestGET extends Request {

	/**
	 * URL of for the request
	 */
	private String sURL = null;

	/**
	 * Parameter for the request
	 */
	private String sParameter = null;

	/**
	 * Constructor for the direct request with GET method
	 * 
	 * @param sURL
	 *            URL for the request
	 * @param sInputParameterName
	 *            name of the parameter
	 * @param aInputModule
	 *            input module to do the request
	 * @param responseType
	 *            type of the response of the observer
	 * @throws IOException
	 *             odd error occured
	 */
	protected DirectRequestGET(final String sURL,
			final String sInputParameterName, final InputModule aInputModule,
			final String responseType) throws IOException {
		super();
		Request.logger.trace("Constructor");
		if (Request.logger.isDebugEnabled()) {
			Request.logger.debug("URL : " + sURL + ".");
			Request.logger.debug("Input parameter name : "
					+ sInputParameterName + ".");
			Request.logger.debug("Input module : " + aInputModule + ".");
		}
		if (!(aInputModule instanceof DirectInputModule)) {
			throw new IllegalArgumentException("InputModule : "
					+ aInputModule.toString() + ".");
		}
		this.sURL = sURL;
		this.addParameter(sInputParameterName, aInputModule.getStringContent());
		this.setResponseType(responseType);
	}

	@Override
	public void addParameter(final String sName, final String sValue)
			throws IOException {
		Request.logger.trace("addParameter");
		if (Request.logger.isDebugEnabled()) {
			Request.logger.debug("Name :" + sName + ".");
			Request.logger.debug("Value :" + sValue + ".");
		}
		if (null == this.sParameter) {
			this.sParameter = "";
		} else {
			this.sParameter += "&";
		}
		this.sParameter += sName + "="
				+ URLEncoder.encode(sValue, "UTF-8");
		Request.logger.debug("Parameters : " + this.sParameter + ".");
	}

	@Override
	public Response doRequest() throws IOException {
		Request.logger.trace("doRequest");
		final URL aURL;
		if (null == this.sParameter) {
			aURL = new URL(this.sURL);
		} else {
			Request.logger.debug(this.sParameter);
			aURL = new URL(this.sURL + "?" + this.sParameter);
		}
		final URLConnection aURLConnection = aURL.openConnection();
		aURLConnection.setRequestProperty("Accept-Language", this.sLang);

		InputStream is = aURLConnection.getInputStream();

		return streamToResponse(is);
	}

	@Override
	public EnumInputMethod getInputMethod() {
		Request.logger.trace("getInputMethod");
		return EnumInputMethod.DIRECT;
	}

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
