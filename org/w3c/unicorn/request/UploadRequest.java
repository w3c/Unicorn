// $Id: UploadRequest.java,v 1.1.1.1 2006-08-31 09:09:25 dleroy Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.bind.JAXBException;

import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.generated.observationresponse.Observationresponse;
import org.w3c.unicorn.input.InputModule;
import org.w3c.unicorn.input.UploadInputModule;
import org.w3c.unicorn.util.ClientHttpRequest;

/**
 * 
 * @author Damien LEROY
 */
public class UploadRequest extends Request {

	private ClientHttpRequest aClientHttpRequest = null;

	protected UploadRequest (
			final String sURL,
			final String sInputParameterName,
			final InputModule aInputModule) throws MalformedURLException, IOException {
		UploadRequest.logger.trace("Constructor");
		if (UploadRequest.logger.isDebugEnabled()) {
			UploadRequest.logger.debug("URL : " + sURL + ".");
			UploadRequest.logger.debug("Input parameter name : " + sInputParameterName + ".");
			UploadRequest.logger.debug("Input module : " + aInputModule + ".");
		}
		if (!(aInputModule instanceof UploadInputModule)) {
			throw new IllegalArgumentException("InputModule : " + aInputModule.toString() + ".");
		}
		this.aClientHttpRequest = new ClientHttpRequest(sURL);
		final UploadInputModule aUploadInputModule = (UploadInputModule) aInputModule;
		this.aClientHttpRequest.setParameter(
				sInputParameterName,
				aUploadInputModule.getFileName(),
				aUploadInputModule.getInputStream());
	}

	@Override
	public void addParameter (final String sName, final String sValue) throws IOException {
		UploadRequest.logger.trace("addParameter");
		if (UploadRequest.logger.isDebugEnabled()) {
			UploadRequest.logger.debug("Name :" + sName + ".");
			UploadRequest.logger.debug("Value :" + sValue + ".");
		}
		this.aClientHttpRequest.setParameter(sName, sValue);
	}

	@Override
	public Observationresponse doRequest() throws JAXBException, IOException {
		UploadRequest.logger.trace("doRequest");
		final Observationresponse aObservationResponse;
		aObservationResponse = (Observationresponse) UploadRequest.aUnmarshaller.unmarshal(this.aClientHttpRequest.post());
		return aObservationResponse;
	}

	@Override
	public EnumInputMethod getInputMethod () {
		UploadRequest.logger.trace("getInputMethod");
		return EnumInputMethod.UPLOAD;
	}

	public void setLang (final String sLang) {
		this.aClientHttpRequest.setLang(sLang);
	}

	public String toString () {
		final int iStringBufferSize = 1000;
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append("ClientHttpRequest:").append(this.aClientHttpRequest);
		return aStringBuffer.toString();
	}

}
