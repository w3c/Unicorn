// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.input.DirectInputModule;
import org.w3c.unicorn.response.Response;
import org.w3c.unicorn.response.ResponseFactory;
import org.w3c.unicorn.util.Message;
import org.w3c.unicorn.exceptions.UnicornException;

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
			final String sInputParameterName, DirectInputModule aInputModule,
			final String responseType, String observerId) {
		super();
		logger.trace("Constructor");
		logger.debug("URL : " + sURL + ".");
		logger.debug("Input parameter name : " + sInputParameterName + ".");
		logger.debug("Input module : " + aInputModule + ".");
		this.sURL = sURL;
		this.observerId = observerId;
		addParameter(sInputParameterName, aInputModule.getStringContent());
		setResponseType(responseType);
	}

	@Override
	public void addParameter(final String sName, final String sValue) {
		logger.trace("addParameter");
		logger.debug("Name :" + sName + ".");
		logger.debug("Value :" + sValue + ".");
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

	@Override
	public Response doRequest() throws UnicornException {
		try {
			logger.trace("doRequest");
			URL aURL;
			if (null == this.sParameter) {
				aURL = new URL(this.sURL);
			} else {
				logger.debug(this.sParameter);
				aURL = new URL(this.sURL + "?" + this.sParameter);
			}
			URLConnection aURLConnection = aURL.openConnection();
			aURLConnection.setRequestProperty("Accept-Language", this.sLang);
			
			return ResponseFactory.getResponse(aURLConnection.getInputStream(), responseType, sURL.toString(), aURLConnection.getContentEncoding(), observerId);
			
		} catch (IOException e) {
			throw new UnicornException(new Message(e));
		}
	}

	@Override
	public EnumInputMethod getInputMethod() {
		logger.trace("getInputMethod");
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
