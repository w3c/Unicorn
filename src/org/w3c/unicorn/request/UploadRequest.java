// $Id: UploadRequest.java,v 1.2 2009-08-28 12:39:48 jean-gui Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.input.InputModule;
import org.w3c.unicorn.input.UploadInputModule;
import org.w3c.unicorn.response.Response;

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
		Request.logger.trace("Constructor");
		if (Request.logger.isDebugEnabled()) {
			Request.logger.debug("URL : " + sURL + ".");
			Request.logger.debug("Input parameter name : "
					+ sInputParameterName + ".");
			Request.logger.debug("Input module : " + aInputModule + ".");
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
		Request.logger.trace("addParameter");
		if (Request.logger.isDebugEnabled()) {
			Request.logger.debug("Name :" + sName + ".");
			Request.logger.debug("Value :" + sValue + ".");
		}
		this.mapOfParameter.put(sName, sValue);
	}

	@Override
	public Response doRequest() throws IOException {
		Request.logger.trace("doRequest");
		DefaultHttpClient httpclient = new DefaultHttpClient();
		 
		HttpPost method = new HttpPost(sURL);
		method.setHeader("Accept-Language", sLang);
		MultipartEntity entity = new MultipartEntity();

		for (final String sName : this.mapOfParameter.keySet()) {
			final String sValue = this.mapOfParameter.get(sName);
			Request.logger.trace("addParameter");
			if (Request.logger.isDebugEnabled()) {
				Request.logger.debug("Name :" + sName + ".");
				Request.logger.debug("Value :" + sValue + ".");
			}
			entity.addPart(sName, new StringBody(sValue));
			//entity.addPart(sName, new StringBody(sValue, Charset.forName("UTF-8")));
		}

		InputStreamBody file = new InputStreamBody(this.aUploadInputModule.getInputStream(), 
				this.aUploadInputModule.getMimeType().toString(), 
				this.aUploadInputModule.getFileName());
		entity.addPart(this.sInputParameterName, file);
		method.setEntity(entity);
		 
		HttpResponse response = httpclient.execute(method);
		return streamToResponse(response.getEntity().getContent());
	}
	
	@Override
	public EnumInputMethod getInputMethod() {
		Request.logger.trace("getInputMethod");
		return EnumInputMethod.UPLOAD;
	}

	/*@Override
	public String toString() {
		final int iStringBufferSize = 1000;
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append("ClientHttpRequest:").append(
				this.aClientHttpRequest);
		return aStringBuffer.toString();
	}*/

}
