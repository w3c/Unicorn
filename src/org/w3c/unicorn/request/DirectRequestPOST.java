// $Id: DirectRequestPOST.java,v 1.3 2009-09-03 16:43:19 jean-gui Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.input.DirectInputModule;
import org.w3c.unicorn.input.InputModule;
import org.w3c.unicorn.response.Response;

/**
 * Class to make a request directly using POST method
 * 
 * @author Damien LEROY
 */
public class DirectRequestPOST extends Request {

	/**
	 * generate a random number
	 */
	private static Random aRandom = new Random();

	/**
	 * URL for the post direct request
	 */
	private String sURL = null;

	/**
	 * Data Structure for the parameters
	 */
	private Map<String, String> mapOfParameter = null;

	/**
	 * Random string for hazardous purpose
	 */
	private String sBoundary = "---------------------------"
			+ DirectRequestPOST.randomString()
			+ DirectRequestPOST.randomString()
			+ DirectRequestPOST.randomString();

	/**
	 * URL to connect
	 */
	private URLConnection aURLConnection = null;

	/**
	 * Output stream for the post
	 */
	private OutputStream aOutputStream = null;

	/**
	 * Generate random strings
	 * 
	 * @return a random string
	 */
	private static String randomString() {
		return Long.toString(DirectRequestPOST.aRandom.nextLong(), 36);
	}

	/**
	 * Constructor for a direct request using post method
	 * 
	 * @param sURL
	 *            URL to connect
	 * @param sInputParameterName
	 *            parameter name
	 * @param aInputModule
	 *            input module for the request
	 * @param responseType
	 *            type of the response of the observer
	 * @throws IOException
	 *             odd error occurs
	 */
	protected DirectRequestPOST(final String sURL,
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
		this.mapOfParameter = new Hashtable<String, String>();
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
		this.mapOfParameter.put(sName, sValue);
	}

	@Override
	public Response doRequest() throws Exception {
		Request.logger.trace("doRequest");
		final URL aURL = new URL(sURL);
		this.aURLConnection = aURL.openConnection();
		this.aURLConnection.setDoOutput(true);
		this.aURLConnection.setRequestProperty("Content-Type",
				"multipart/form-data; boundary=" + sBoundary);
		this.aURLConnection.setRequestProperty("Accept-Language", this.sLang);

		if (null == this.aOutputStream) {
			this.aOutputStream = this.aURLConnection.getOutputStream();
		}
		for (final String sName : this.mapOfParameter.keySet()) {
			final String sValue = this.mapOfParameter.get(sName);
			Request.logger.trace("addParameter");
			if (Request.logger.isDebugEnabled()) {
				Request.logger.debug("Name :" + sName + ".");
				Request.logger.debug("Value :" + sValue + ".");
			}
			Request.logger.debug("--");
			Request.logger.debug(this.sBoundary);
			Request.logger.debug("\r\n");
			Request.logger.debug("Content-Disposition: form-data; name=\"");
			Request.logger.debug(sName);
			Request.logger.debug('"');
			Request.logger.debug("\r\n");
			Request.logger.debug("\r\n");
			Request.logger.debug(sValue);
			Request.logger.debug("\r\n");
			// boundary
			this.aOutputStream.write("--".getBytes());
			this.aOutputStream.write(this.sBoundary.getBytes());
			// writeName
			this.aOutputStream.write("\r\n".getBytes());
			this.aOutputStream.write("Content-Disposition: form-data; name=\""
					.getBytes());
			this.aOutputStream.write(sName.getBytes());
			this.aOutputStream.write('"');
			// newline
			this.aOutputStream.write("\r\n".getBytes());
			// newline
			this.aOutputStream.write("\r\n".getBytes());
			// writeln
			this.aOutputStream.write(sValue.getBytes());
			this.aOutputStream.write("\r\n".getBytes());
		}
		Request.logger.debug("--");
		Request.logger.debug(this.sBoundary);
		Request.logger.debug("--");
		Request.logger.debug("\r\n");
		this.aOutputStream.write("--".getBytes());
		this.aOutputStream.write(this.sBoundary.getBytes());
		this.aOutputStream.write("--".getBytes());
		this.aOutputStream.write("\r\n".getBytes());
		this.aOutputStream.close();

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
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append("url:").append(this.sURL);
		return aStringBuffer.toString();
	}

}
