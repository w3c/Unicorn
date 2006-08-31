// $Id: Request.java,v 1.1.1.1 2006-08-31 09:09:25 dleroy Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.generated.observationresponse.Observationresponse;
import org.w3c.unicorn.input.InputModule;

/**
 * 
 * @author Damien LEROY
 */
public abstract class Request {

	protected static final Log logger = RequestList.logger;

	public static Unmarshaller aUnmarshaller = null;
	protected static JAXBContext aJAXBContext = null;

	static {
		try {
			Request.aJAXBContext = JAXBContext.newInstance("org.w3c.unicorn.generated.observationresponse");
			Request.aUnmarshaller = Request.aJAXBContext.createUnmarshaller();
		}
		catch (final JAXBException e) {
			Request.logger.error("JAXBException : " + e.getMessage(), e);
			e.printStackTrace();
		}
	}

	protected String sLang = null;

	public void setLang (final String sLang) {
		this.sLang = sLang;
	}

	public abstract void addParameter (final String sName, final String sValue) throws IOException;

	public abstract Observationresponse doRequest () throws JAXBException, IOException;

	public abstract EnumInputMethod getInputMethod ();

	public static Request createRequest (
			final InputModule aInputModule,
			final String sURL,
			final String sInputParameterName) throws IOException {
		Request.logger.trace("createRequest");
		if (Request.logger.isDebugEnabled()) {
			Request.logger.debug("InputModule : " + aInputModule + ".");
			Request.logger.debug("URL : " + sURL + ".");
			Request.logger.debug("Input parameter name : " + sInputParameterName + ".");
		}
		switch (aInputModule.getEnumInputMethod()) {
			case DIRECT :
				return new DirectRequest(sURL, sInputParameterName, aInputModule);
			case UPLOAD :
				return new UploadRequest(sURL, sInputParameterName, aInputModule);
			case URI :
				return new URIRequest(sURL, sInputParameterName, aInputModule);
		}
		return null;
	}

	public String toString () {
		return "Abstract class org.w3c.unicorn.request.Request, toString function must be overrided.";
	}

}
