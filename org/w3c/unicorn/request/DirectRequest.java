// $Id: DirectRequest.java,v 1.1.1.1 2006-08-31 09:09:25 dleroy Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.xml.bind.JAXBException;

import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.generated.observationresponse.Observationresponse;
import org.w3c.unicorn.input.DirectInputModule;
import org.w3c.unicorn.input.InputModule;
import org.w3c.unicorn.util.Property;

/**
 * 
 * @author Damien LEROY
 */
public class DirectRequest extends Request {

	private String sURL = null;
	private String sParameter = null;

	protected DirectRequest (
			final String sURL,
			final String sInputParameterName,
			final InputModule aInputModule) throws IOException {
		DirectRequest.logger.trace("Constructor");
		if (DirectRequest.logger.isDebugEnabled()) {
			DirectRequest.logger.debug("URL : " + sURL + ".");
			DirectRequest.logger.debug("Input parameter name : " + sInputParameterName + ".");
			DirectRequest.logger.debug("Input module : " + aInputModule + ".");
		}
		if (!(aInputModule instanceof DirectInputModule)) {
			throw new IllegalArgumentException("InputModule : " + aInputModule.toString() + ".");
		}
		this.sURL = sURL;
		this.addParameter(sInputParameterName, aInputModule.getStringContent());
	}

	@Override
	public void addParameter (final String sName, final String sValue) throws IOException {
		DirectRequest.logger.trace("addParameter");
		if (DirectRequest.logger.isDebugEnabled()) {
			DirectRequest.logger.debug("Name :" + sName + ".");
			DirectRequest.logger.debug("Value :" + sValue + ".");
		}
		if (null == this.sParameter) {
			this.sParameter = "";
		} else {
			this.sParameter += "&";
		}
		this.sParameter += sName + "=" + URLEncoder.encode(sValue, Property.get("UNICORN_ENCODING"));
		DirectRequest.logger.debug("Parameters : "+this.sParameter+".");
	}

	@Override
	public Observationresponse doRequest () throws JAXBException, IOException {
		DirectRequest.logger.trace("doRequest");
		final URL aURL;
		if (null == this.sParameter) {
			aURL = new URL(this.sURL);
		} else {
			DirectRequest.logger.debug(this.sParameter);
			aURL = new URL(this.sURL + "?" + this.sParameter);
		}
		final URLConnection aURLConnection = aURL.openConnection();
		aURLConnection.setRequestProperty("Accept-Language", this.sLang);
		return (Observationresponse) DirectRequest.aUnmarshaller.unmarshal(aURLConnection.getInputStream());
	}

	@Override
	public EnumInputMethod getInputMethod () {
		DirectRequest.logger.trace("getInputMethod");
		return EnumInputMethod.DIRECT;
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
