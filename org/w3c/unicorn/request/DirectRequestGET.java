// $Id: DirectRequestGET.java,v 1.2 2007-11-29 14:11:58 dtea Exp $
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
public class DirectRequestGET extends Request {

	private String sURL = null;
	private String sParameter = null;

	protected DirectRequestGET (
			final String sURL,
			final String sInputParameterName,
			final InputModule aInputModule) throws IOException {
		super();
		DirectRequestGET.logger.trace("Constructor");
		if (DirectRequestGET.logger.isDebugEnabled()) {
			DirectRequestGET.logger.debug("URL : " + sURL + ".");
			DirectRequestGET.logger.debug("Input parameter name : " + sInputParameterName + ".");
			DirectRequestGET.logger.debug("Input module : " + aInputModule + ".");
		}
		if (!(aInputModule instanceof DirectInputModule)) {
			throw new IllegalArgumentException("InputModule : " + aInputModule.toString() + ".");
		}
		this.sURL = sURL;
		this.addParameter(sInputParameterName, aInputModule.getStringContent());
	}

	@Override
	public void addParameter (final String sName, final String sValue) throws IOException {
		DirectRequestGET.logger.trace("addParameter");
		if (DirectRequestGET.logger.isDebugEnabled()) {
			DirectRequestGET.logger.debug("Name :" + sName + ".");
			DirectRequestGET.logger.debug("Value :" + sValue + ".");
		}
		if (null == this.sParameter) {
			this.sParameter = "";
		} else {
			this.sParameter += "&";
		}
		this.sParameter += sName + "=" + URLEncoder.encode(sValue, Property.get("UNICORN_ENCODING"));
		DirectRequestGET.logger.debug("Parameters : "+this.sParameter+".");
	}

	@Override
	public Observationresponse doRequest () throws JAXBException, IOException {
		DirectRequestGET.logger.trace("doRequest");
		final URL aURL;
		if (null == this.sParameter) {
			aURL = new URL(this.sURL);
		} else {
			DirectRequestGET.logger.debug(this.sParameter);
			aURL = new URL(this.sURL + "?" + this.sParameter);
		}
		final URLConnection aURLConnection = aURL.openConnection();
		aURLConnection.setRequestProperty("Accept-Language", this.sLang);
		return (Observationresponse) this.aUnmarshaller.unmarshal(aURLConnection.getInputStream());
	}

	@Override
	public EnumInputMethod getInputMethod () {
		DirectRequestGET.logger.trace("getInputMethod");
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
