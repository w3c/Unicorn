// $Id: UploadRequest.java,v 1.3 2007-11-29 14:11:58 dtea Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.Map;

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

	private String sURL = null;
	private String sInputParameterName = null;
	private ClientHttpRequest aClientHttpRequest = null;
	private UploadInputModule aUploadInputModule = null;
	private Map<String, String> mapOfParameter = null;

	protected UploadRequest (
			final String sURL,
			final String sInputParameterName,
			final InputModule aInputModule) throws MalformedURLException, IOException {
		super();
		UploadRequest.logger.trace("Constructor");
		if (UploadRequest.logger.isDebugEnabled()) {
			UploadRequest.logger.debug("URL : " + sURL + ".");
			UploadRequest.logger.debug("Input parameter name : " + sInputParameterName + ".");
			UploadRequest.logger.debug("Input module : " + aInputModule + ".");
		}
		if (!(aInputModule instanceof UploadInputModule)) {
			throw new IllegalArgumentException("InputModule : " + aInputModule.toString() + ".");
		}
		this.sURL = sURL;
		this.sInputParameterName = sInputParameterName;
		this.aUploadInputModule = (UploadInputModule) aInputModule;
		this.mapOfParameter = new Hashtable<String, String>();
		//this.aClientHttpRequest = new ClientHttpRequest(sURL);
		/*this.aClientHttpRequest.setParameter(
				this.sInputParameterName,
				this.aUploadInputModule.getFileName(),
				this.aUploadInputModule.getInputStream());*/
	}

	@Override
	public void addParameter (final String sName, final String sValue) throws IOException {
		UploadRequest.logger.trace("addParameter");
		if (UploadRequest.logger.isDebugEnabled()) {
			UploadRequest.logger.debug("Name :" + sName + ".");
			UploadRequest.logger.debug("Value :" + sValue + ".");
		}
		this.mapOfParameter.put(sName, sValue);
		//this.aClientHttpRequest.setParameter(sName, sValue);
	}

	@Override
	public Observationresponse doRequest() throws JAXBException, IOException {
		UploadRequest.logger.trace("doRequest");
		this.aClientHttpRequest = new ClientHttpRequest(sURL);
		UploadRequest.logger.debug("Lang : "+this.sLang+".");
		this.aClientHttpRequest.setLang(sLang); // meme place que pour directpost
		this.aClientHttpRequest.setParameter(
				this.sInputParameterName,
				this.aUploadInputModule.getFileName(),
				this.aUploadInputModule.getInputStream());
		for (final String sName : this.mapOfParameter.keySet()) {
			final String sValue = this.mapOfParameter.get(sName);
			DirectRequestPOST.logger.trace("addParameter");
			if (DirectRequestPOST.logger.isDebugEnabled()) {
				DirectRequestPOST.logger.debug("Name :" + sName + ".");
				DirectRequestPOST.logger.debug("Value :" + sValue + ".");
			}
			this.aClientHttpRequest.setParameter(sName, sValue);
		}
		final Observationresponse aObservationResponse;
		aObservationResponse = (Observationresponse) this.aUnmarshaller.unmarshal(this.aClientHttpRequest.post());
		return aObservationResponse;
	}

	@Override
	public EnumInputMethod getInputMethod () {
		UploadRequest.logger.trace("getInputMethod");
		return EnumInputMethod.UPLOAD;
	}

	public String toString () {
		final int iStringBufferSize = 1000;
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append("ClientHttpRequest:").append(this.aClientHttpRequest);
		return aStringBuffer.toString();
	}

}
