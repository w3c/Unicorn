// $Id: Request.java,v 1.10 2008-09-23 14:26:19 jean-gui Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.input.InputModule;
import org.w3c.unicorn.response.Response;
import org.w3c.unicorn.response.parser.ResponseParserFactory;

/**
 * 
 * @author Damien LEROY
 */
public abstract class Request {
	/**
	 * Object used for complex logging purpose
	 */
	protected static final Log logger = LogFactory
			.getLog("org.w3c.unicorn.request");

	/**
	 * Language of the request
	 */
	protected String sLang = null;

	/**
	 * Type of the response for the request
	 */
	protected String responseType = null;

	/**
	 * Sets the language of the request
	 * 
	 * @param sLang
	 *            new language to set
	 * @throws IOException
	 *             odd error occured
	 */
	public void setLang(final String sLang) throws IOException {
		Request.logger.debug("setLang(" + sLang + ")");
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
	public abstract void addParameter(final String sName, final String sValue)
			throws IOException;

	/**
	 * Do the request to the observer
	 * 
	 * @return the response of the observer
	 * @throws IOException
	 *             odd error occured
	 */
	public abstract org.w3c.unicorn.response.Response doRequest()
			throws IOException;

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
			final boolean bIsPost, final String responseType)
			throws IOException {

		Request.logger.trace("createRequest");
		if (Request.logger.isDebugEnabled()) {
			Request.logger.debug("InputModule : " + aInputModule + ".");
			Request.logger.debug("URL : " + sURL + ".");
			Request.logger.debug("Input parameter name : "
					+ sInputParameterName + ".");
			Request.logger.debug("POST method : " + bIsPost + ".");
		}
		switch (aInputModule.getEnumInputMethod()) {
		case DIRECT:
			if (bIsPost) {
				return new DirectRequestPOST(sURL, sInputParameterName,
						aInputModule, responseType);
			} else {
				return new DirectRequestGET(sURL, sInputParameterName,
						aInputModule, responseType);
			}
		case UPLOAD:
			return new UploadRequest(sURL, sInputParameterName, aInputModule,
					responseType);
		case URI:
			return new URIRequest(sURL, sInputParameterName, aInputModule,
					responseType);
		}
		return null;
	}

	public String toString() {
		return "Abstract class org.w3c.unicorn.request.Request, toString function must be overrided.";
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	
  protected Response streamToResponse(InputStream is) throws IOException {
      StringBuilder builder = new StringBuilder();
      InputStreamReader isr = new InputStreamReader(is, "UTF-8");
      char[] chararray = new char[8192];
      int readLength = 0;
      while((readLength = isr.read(chararray, 0, 8192)) > -1) {
          builder.append(chararray, 0, readLength);
      }

      Response res = ResponseParserFactory.parse(builder.toString(), 
                                                 this.getResponseType());
      res.setXml(builder);

      return res;
  }
}
