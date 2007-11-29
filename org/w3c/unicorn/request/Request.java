// $Id: Request.java,v 1.3 2007-11-29 14:11:58 dtea Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.request;

import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.generated.observationresponse.Observationresponse;
import org.w3c.unicorn.input.InputModule;

/**
 * 
 * @author Damien LEROY
 */
public abstract class Request {

	protected static final Log logger = LogFactory.getLog("org.w3c.unicorn.request");

	public Unmarshaller aUnmarshaller = null;
	protected static JAXBContext aJAXBContext = null;

	static {
		try {
			Request.aJAXBContext = JAXBContext.newInstance("org.w3c.unicorn.generated.observationresponse");
			
		}
		catch (final JAXBException e) {
			Request.logger.error("JAXBException : " + e.getMessage(), e);
			e.printStackTrace();
		}
	}

	protected String sLang = null;
	
	public Request(){
		try {
			this.aUnmarshaller = Request.aJAXBContext.createUnmarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setLang (final String sLang) throws IOException {
		Request.logger.debug("setLang("+sLang+")");
		this.sLang = sLang;
	}

	public abstract void addParameter (final String sName, final String sValue) throws IOException;

    public abstract Observationresponse doRequest () throws JAXBException, IOException;

	public abstract EnumInputMethod getInputMethod ();

	public static Request createRequest (
			final InputModule aInputModule,
			final String sURL,
			final String sInputParameterName,
			final boolean bIsPost) throws IOException {
		
		
		Request.logger.trace("createRequest");
		if (Request.logger.isDebugEnabled()) {
			Request.logger.debug("InputModule : " + aInputModule + ".");
			Request.logger.debug("URL : " + sURL + ".");
			Request.logger.debug("Input parameter name : " + sInputParameterName + ".");
			Request.logger.debug("POST method : " + bIsPost + ".");
		}
		switch (aInputModule.getEnumInputMethod()) {
			case DIRECT :
				if ( bIsPost) {
					return new DirectRequestPOST(sURL, sInputParameterName, aInputModule);
				} else {
					return new DirectRequestGET(sURL, sInputParameterName, aInputModule);
				}
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
