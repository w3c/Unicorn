// $Id: UploadRequest.java,v 1.6 2009-09-21 16:28:33 tgambet Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.Map;

import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.input.UploadInputModule;
import org.w3c.unicorn.response.Response;
import org.w3c.unicorn.util.ClientHttpRequest;

/**
 * Class to deal with the upload request
 * 
 * @author Damien LEROY
 */
public class UploadRequest extends Request {

	/**
	 * URL for the request
	 */
	private String sURL = null;

	/**
	 * Name of the parameter
	 */
	private String sInputParameterName = null;

	/**
	 * A http client for the request in upload
	 */
	private ClientHttpRequest aClientHttpRequest = null;

	/**
	 * An input module with upload
	 */
	private UploadInputModule aUploadInputModule = null;

	/**
	 * Data structure for the parameters
	 */
	private Map<String, String> mapOfParameter = null;

	/**
	 * Create a upload request
	 * 
	 * @param sURL
	 *            URL for the request
	 * @param sInputParameterName
	 *            name of the parameter
	 * @param aInputModule
	 *            module for the input of the request
	 * @param responseType
	 *            type of the response
	 * @throws MalformedURLException
	 *             error if the URL is not well formed
	 * @throws IOException
	 *             odd error occured
	 */
	protected UploadRequest(final String sURL,
			final String sInputParameterName, final UploadInputModule aInputModule,
			final String responseType, String observerId) {
		super();
		logger.trace("Constructor");
		logger.debug("URL : " + sURL + ".");
		logger.debug("Input parameter name : " + sInputParameterName + ".");
		logger.debug("Input module : " + aInputModule + ".");
		this.sURL = sURL;
		this.observerId = observerId;
		this.sInputParameterName = sInputParameterName;
		this.aUploadInputModule = (UploadInputModule) aInputModule;
		this.mapOfParameter = new Hashtable<String, String>();
		this.setResponseType(responseType);
	}

	@Override
	public void addParameter(final String sName, final String sValue) {
		logger.trace("addParameter");
		logger.debug("Name :" + sName + ".");
		logger.debug("Value :" + sValue + ".");
		this.mapOfParameter.put(sName, sValue);
	}

	@Override
	public Response doRequest() throws Exception {
		logger.trace("doRequest");
		this.aClientHttpRequest = new ClientHttpRequest(sURL);
		logger.debug("Lang : " + this.sLang + ".");
		this.aClientHttpRequest.setLang(sLang);
		this.aClientHttpRequest.setParameter(this.sInputParameterName,
				this.aUploadInputModule.getFileName(), 
				this.aUploadInputModule.getInputStream(),
				this.aUploadInputModule.getMimeType());
		for (final String sName : this.mapOfParameter.keySet()) {
			final String sValue = this.mapOfParameter.get(sName);
			logger.trace("addParameter");
			logger.debug("Name :" + sName + ".");
			logger.debug("Value :" + sValue + ".");
			this.aClientHttpRequest.setParameter(sName, sValue);
		}
		InputStream is = this.aClientHttpRequest.post();

		return streamToResponse(is);
	}

	@Override
	public EnumInputMethod getInputMethod() {
		logger.trace("getInputMethod");
		return EnumInputMethod.UPLOAD;
	}

	@Override
	public String toString() {
		final int iStringBufferSize = 1000;
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append("ClientHttpRequest:").append(
				this.aClientHttpRequest);
		return aStringBuffer.toString();
	}

}
