// $Id: UploadRequest.java,v 1.8 2008-09-23 13:53:58 jean-gui Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.Map;

import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.input.InputModule;
import org.w3c.unicorn.input.UploadInputModule;
import org.w3c.unicorn.response.Response;
import org.w3c.unicorn.response.parser.ResponseParserFactory;
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
			final String sInputParameterName, final InputModule aInputModule,
			final String responseType) throws MalformedURLException,
			IOException {
		super();
		UploadRequest.logger.trace("Constructor");
		if (UploadRequest.logger.isDebugEnabled()) {
			UploadRequest.logger.debug("URL : " + sURL + ".");
			UploadRequest.logger.debug("Input parameter name : "
					+ sInputParameterName + ".");
			UploadRequest.logger.debug("Input module : " + aInputModule + ".");
		}
		if (!(aInputModule instanceof UploadInputModule)) {
			throw new IllegalArgumentException("InputModule : "
					+ aInputModule.toString() + ".");
		}
		this.sURL = sURL;
		this.sInputParameterName = sInputParameterName;
		this.aUploadInputModule = (UploadInputModule) aInputModule;
		this.mapOfParameter = new Hashtable<String, String>();
		this.setResponseType(responseType);
	}

	@Override
	public void addParameter(final String sName, final String sValue)
			throws IOException {
		UploadRequest.logger.trace("addParameter");
		if (UploadRequest.logger.isDebugEnabled()) {
			UploadRequest.logger.debug("Name :" + sName + ".");
			UploadRequest.logger.debug("Value :" + sValue + ".");
		}
		this.mapOfParameter.put(sName, sValue);
	}

	@Override
	public Response doRequest() throws IOException {
		UploadRequest.logger.trace("doRequest");
		this.aClientHttpRequest = new ClientHttpRequest(sURL);
		UploadRequest.logger.debug("Lang : " + this.sLang + ".");
		this.aClientHttpRequest.setLang(sLang);
		this.aClientHttpRequest.setParameter(this.sInputParameterName,
				this.aUploadInputModule.getFileName(), this.aUploadInputModule
						.getInputStream());
		for (final String sName : this.mapOfParameter.keySet()) {
			final String sValue = this.mapOfParameter.get(sName);
			DirectRequestPOST.logger.trace("addParameter");
			if (DirectRequestPOST.logger.isDebugEnabled()) {
				DirectRequestPOST.logger.debug("Name :" + sName + ".");
				DirectRequestPOST.logger.debug("Value :" + sValue + ".");
			}
			this.aClientHttpRequest.setParameter(sName, sValue);
		}
		final Response aObservationResponse;
		InputStream is = this.aClientHttpRequest.post();
		
    return streamToResponse(is);
	}

	@Override
	public EnumInputMethod getInputMethod() {
		UploadRequest.logger.trace("getInputMethod");
		return EnumInputMethod.UPLOAD;
	}

	public String toString() {
		final int iStringBufferSize = 1000;
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append("ClientHttpRequest:").append(
				this.aClientHttpRequest);
		return aStringBuffer.toString();
	}

}
