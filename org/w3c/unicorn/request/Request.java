// $Id: Request.java,v 1.4 2008-02-20 15:09:57 hduong Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.input.InputModule;
import org.w3c.unicorn.response.parser.ResponseParser;

/**
 * 
 * @author Damien LEROY
 */
public abstract class Request {
	protected static final Log logger = LogFactory.getLog("org.w3c.unicorn.request");
	
	//public ResponseParser aResponseParser;

	protected String sLang = null;

	protected String responseType=null;
	
	public void setLang (final String sLang) throws IOException {
		Request.logger.debug("setLang("+sLang+")");
		this.sLang = sLang;
	}

	public abstract void addParameter (final String sName, final String sValue) throws IOException;

    public abstract org.w3c.unicorn.response.Response doRequest () throws IOException;

	public abstract EnumInputMethod getInputMethod ();

	public static Request createRequest (
			final InputModule aInputModule,
			final String sURL,
			final String sInputParameterName,
			final boolean bIsPost,
			final String responseType) throws IOException {
		
		
		Request.logger.trace("createRequest");
		if (Request.logger.isDebugEnabled()) {
			Request.logger.debug("InputModule : " + aInputModule + ".");
			Request.logger.debug("URL : " + sURL + ".");
			Request.logger.debug("Input parameter name : " + sInputParameterName + ".");
			Request.logger.debug("POST method : " + bIsPost + ".");
		}
		switch (aInputModule.getEnumInputMethod()) {
			case DIRECT :
				if ( bIsPost) {
					return new DirectRequestPOST(sURL, sInputParameterName, aInputModule, responseType);
				} else {
					return new DirectRequestGET(sURL, sInputParameterName, aInputModule, responseType);
				}
			case UPLOAD :
				return new UploadRequest(sURL, sInputParameterName, aInputModule, responseType);
			case URI :
				return new URIRequest(sURL, sInputParameterName, aInputModule, responseType);
		}
		return null;
	}

	public String toString () {
		return "Abstract class org.w3c.unicorn.request.Request, toString function must be overrided.";
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

}
