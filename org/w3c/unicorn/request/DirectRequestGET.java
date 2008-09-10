// $Id: DirectRequestGET.java,v 1.6 2008-09-10 10:10:40 jean-gui Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.input.DirectInputModule;
import org.w3c.unicorn.input.InputModule;
import org.w3c.unicorn.response.Response;
import org.w3c.unicorn.response.parser.ResponseParserFactory;
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
		DirectRequestGET.logger.trace("Constructor");
		if (DirectRequestGET.logger.isDebugEnabled()) {
			DirectRequestGET.logger.debug("URL : " + sURL + ".");
			DirectRequestGET.logger.debug("Input parameter name : "
					+ sInputParameterName + ".");
			DirectRequestGET.logger.debug("Input module : " + aInputModule
					+ ".");
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
		DirectRequestGET.logger.trace("addParameter");
		if (DirectRequestGET.logger.isDebugEnabled()) {
			DirectRequestGET.logger.debug("Name :" + sName + ".");
			DirectRequestGET.logger.debug("Value :" + sValue + ".");
		}
		if (null == this.sParameter) {
			this.sParameter = "";
		} else {
			this.sParameter += "&";
		}
		this.sParameter += sName + "="
				+ URLEncoder.encode(sValue, Property.get("UNICORN_ENCODING"));
		DirectRequestGET.logger.debug("Parameters : " + this.sParameter + ".");
	}

	@Override
	public Response doRequest() throws IOException {
		DirectRequestGET.logger.trace("doRequest");
		final URL aURL;
		if (null == this.sParameter) {
			aURL = new URL(this.sURL);
		} else {
			DirectRequestGET.logger.debug(this.sParameter);
			aURL = new URL(this.sURL + "?" + this.sParameter);
		}
		final URLConnection aURLConnection = aURL.openConnection();
		aURLConnection.setRequestProperty("Accept-Language", this.sLang);
		InputStream is = aURLConnection.getInputStream();

    return streamToResponse(is);
	}

	@Override
	public EnumInputMethod getInputMethod() {
		DirectRequestGET.logger.trace("getInputMethod");
		return EnumInputMethod.DIRECT;
	}

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
