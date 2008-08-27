// $Id: URIRequest.java,v 1.6 2008-08-27 12:09:59 jbarouh Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.input.InputModule;
import org.w3c.unicorn.input.URIInputModule;
import org.w3c.unicorn.response.Response;
import org.w3c.unicorn.response.parser.ResponseParserFactory;

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
		URIRequest.logger.trace("Constructor");
		if (URIRequest.logger.isDebugEnabled()) {
			URIRequest.logger.debug("URL : " + sURL + ".");
			URIRequest.logger.debug("Input parameter name : "
					+ sInputParameterName + ".");
			URIRequest.logger.debug("Input module : " + aInputModule + ".");
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
	public void addParameter(final String sName, final String sValue)
			throws UnsupportedEncodingException {
		URIRequest.logger.trace("addParameter");
		if (URIRequest.logger.isDebugEnabled()) {
			URIRequest.logger.debug("sName : " + sName + ".");
			URIRequest.logger.debug("sValue : " + sValue + ".");
		}
		if (null == this.sParameter) {
			this.sParameter = "";
		} else {
			this.sParameter += "&";
		}
		this.sParameter += sName + "=" + URLEncoder.encode(sValue, "UTF-8");
		URIRequest.logger.debug("Parameters : " + this.sParameter + ".");
	}

	/**
	 * Do the request to the observer
	 * 
	 * @throws IOException
	 *             odd error occured
	 */
	public Response doRequest() throws IOException {
		URIRequest.logger.trace("doRequest");
		if (URIRequest.logger.isDebugEnabled()) {
			URIRequest.logger.debug("URL : " + this.sURL + " .");
			URIRequest.logger.debug("Parameters : " + this.sParameter + " .");
		}
		final URL aURL;
		if (null == this.sParameter) {
			aURL = new URL(this.sURL);
		} else {
			URIRequest.logger.debug(this.sParameter);
			aURL = new URL(this.sURL + "?" + this.sParameter);
		}
		URIRequest.logger.debug("URL : " + aURL + " .");
		final URLConnection aURLConnection = aURL.openConnection();

		aURLConnection.setRequestProperty("Accept-Language", this.sLang);

		InputStream is = aURLConnection.getInputStream();
		//System.out.println("mark supported : " + is.markSupported());
		//System.out.println("is class : " + is.getClass());
		setResponseStream(is);
		
		StringBuffer sb = new StringBuffer();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String s;
		while ((s = br.readLine()) != null) {
			sb.append(s + "\n");
			//System.out.println(s);
		}
		br.close();
		this.setResponseBuffer(sb);
		

		StringBufferInputStream sbis = new StringBufferInputStream(sb.toString());
		//System.out.println(sb.toString());
		//System.out.println("response stream set");
		//Response res = this.aResponseParser.parse(is);
		Response res = ResponseParserFactory.parse(sbis, this.getResponseType());
		res.setXml(sb);
		//Response res = null;
		//System.out.println("response created");
		//is.reset();
		return res;
	}

	@Override
	public EnumInputMethod getInputMethod() {
		URIRequest.logger.trace("getInputMethod");
		return EnumInputMethod.URI;
	}

	/**
	 * Prints the object
	 */
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
