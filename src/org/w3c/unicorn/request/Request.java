// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.input.DirectInputModule;
import org.w3c.unicorn.input.InputModule;
import org.w3c.unicorn.input.URIInputModule;
import org.w3c.unicorn.input.UploadInputModule;
import org.w3c.unicorn.exceptions.UnicornException;

/**
 * 
 * @author Damien LEROY
 */
public abstract class Request {
	/**
	 * Object used for complex logging purpose
	 */
	protected static final Log logger = LogFactory.getLog(Request.class);

	/**
	 * Language of the request
	 */
	protected String sLang = null;

	/**
	 * Type of the response for the request
	 */
	protected String responseType = null;
	
	protected String observerId;

	/**
	 * Sets the language of the request
	 * 
	 * @param sLang
	 *            new language to set
	 * @throws IOException
	 *             odd error occured
	 */
	public void setLang(final String sLang) {
		logger.debug("setLang(" + sLang + ")");
		this.sLang = sLang;
	}

	/**
	 * Add a parameter to the request
	 * 
	 * @param sName
	 *            name of the parameter
	 * @param sValue
	 *            value of the parameter
	 * @throws IOException
	 *             odd error occured
	 */
	public abstract void addParameter(final String sName, final String sValue);

	/**
	 * Do the request to the observer
	 * 
	 * @return the response of the observer
	 * @throws IOException
	 *             odd error occured
	 * @throws Exception 
	 */
	public abstract org.w3c.unicorn.response.Response doRequest() throws UnicornException;

	public abstract EnumInputMethod getInputMethod();

	/**
	 * Create a request for the observer
	 * 
	 * @param aInputModule
	 *            input module used for the request
	 * @param sURL
	 *            url for the request
	 * @param sInputParameterName
	 *            name of the parameter of the request
	 * @param bIsPost
	 *            to know whether the request is sent or not
	 * @param responseType
	 *            type of the response
	 * @return a request ready to be done
	 * @throws IOException
	 *             odd error occurred
	 */
	public static Request createRequest(final InputModule aInputModule,
			final String sURL, final String sInputParameterName,
			final boolean bIsPost, final String responseType, String observerId) {

		logger.trace("createRequest");
		logger.debug("InputModule : " + aInputModule + ".");
		logger.debug("URL : " + sURL + ".");
		logger.debug("Input parameter name : " + sInputParameterName + ".");
		logger.debug("POST method : " + bIsPost + ".");
		switch (aInputModule.getEnumInputMethod()) {
		case DIRECT:
			if (bIsPost) {
				return new DirectRequestPOST(sURL, sInputParameterName, 
						(DirectInputModule) aInputModule, responseType, observerId);
			} else {
				return new DirectRequestGET(sURL, sInputParameterName,
						(DirectInputModule) aInputModule, responseType, observerId);
			}
		case UPLOAD:
			return new UploadRequest(sURL, sInputParameterName,
					(UploadInputModule) aInputModule, responseType, observerId);
		case URI:
			return new URIRequest(sURL, sInputParameterName,
					(URIInputModule) aInputModule, responseType, observerId);
		}
		return null;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getObserverId() {
		return observerId;
	}

	public void setObserverId(String observerId) {
		this.observerId = observerId;
	}
}
