// $Id: URIRequest.java,v 1.4 2008-02-20 15:09:57 hduong Exp $
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
import org.w3c.unicorn.response.parser.ResponseParserFactory;

/**
 * Use to handle a request to a observer.
 * @author Damien LEROY
 */
public class URIRequest extends Request {

	private String sURL = null;
	private String sParameter = null;

	protected URIRequest (
			final String sURL,
			final String sInputParameterName,
			final InputModule aInputModule,
			final String responseType) throws IOException {
		super();
		URIRequest.logger.trace("Constructor");
		if (URIRequest.logger.isDebugEnabled()) {
			URIRequest.logger.debug("URL : " + sURL + ".");
			URIRequest.logger.debug("Input parameter name : " + sInputParameterName + ".");
			URIRequest.logger.debug("Input module : " + aInputModule + ".");
		}
		if (!(aInputModule instanceof URIInputModule)) {
			throw new IllegalArgumentException("InputModule : " + aInputModule.toString() + ".");
		}
		this.sURL = sURL;
		final URIInputModule aURIInputModule = (URIInputModule) aInputModule;
		this.addParameter(sInputParameterName, aURIInputModule.getURI());
		this.setResponseType(responseType);
	}

	public void addParameter (
			final String sName,
			final String sValue) throws UnsupportedEncodingException {
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
		URIRequest.logger.debug("Parameters : "+this.sParameter+".");
	}

	public Response doRequest () throws IOException {
		URIRequest.logger.trace("doRequest");
		if (URIRequest.logger.isDebugEnabled()) {
			URIRequest.logger.debug("URL : "+this.sURL+" .");
			URIRequest.logger.debug("Parameters : "+this.sParameter+" .");
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
		//Response res = this.aResponseParser.parse(is);
		Response res = ResponseParserFactory.parse(is, this.getResponseType());
		return res;
	}

	@Override
	public EnumInputMethod getInputMethod () {
		URIRequest.logger.trace("getInputMethod");
		return EnumInputMethod.URI;
	}

	public String toString () {
		final int iStringBufferSize = 1000;
		final String sVariableSeparator = " ";
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append("url:").append(this.sURL);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("param:").append(this.sParameter);
		return aStringBuffer.toString();
	}

}
