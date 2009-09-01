// $Id: XMLOutputFormater.java,v 1.4 2009-09-01 13:39:41 tgambet Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.Writer;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.event.EventCartridge;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.util.Templates;
import org.w3c.unicorn.util.XHTMLize;

/**
 * Class for XML output formater.
 * 
 * @author Jean-Guilhem ROUEL
 */
public class XMLOutputFormater implements OutputFormater {

	/**
	 * Object used for complex logging purpose
	 */
	private static final Log logger = LogFactory.getLog(XMLOutputFormater.class);

	/**
	 * Apache velocity context
	 */
	private VelocityContext aVelocityContext;
	
	private String sOutputFormat;
	
	private String sLang;

	/**
	 * Write the result of the XML in a file
	 * 
	 * @param sOutputFormat
	 *            format of the output
	 * @param sLang
	 *            Language of the output
	 * @throws ResourceNotFoundException
	 *             exception when resources not found using the path
	 * @throws ParseErrorException
	 *             error in the parser
	 * @throws Exception
	 *             odd error occur
	 */
	public XMLOutputFormater(final String sOutputFormat, final String sLang)
			throws ResourceNotFoundException, ParseErrorException, Exception {
		XMLOutputFormater.logger.trace("Constructor");
		XMLOutputFormater.logger.debug("Output format : " + sOutputFormat + ".");
		XMLOutputFormater.logger.debug("Output language : " + sLang + ".");

		this.sOutputFormat = sOutputFormat;
		this.sLang = sLang;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.unicorn.output.OutputFormater#produceOutput(java.util.Map,
	 *      java.io.Writer)
	 */
	public void produceOutput(final Map<String, Object> mapOfStringObject,
			final Writer aWriter) throws ResourceNotFoundException,
			ParseErrorException, MethodInvocationException, Exception {
		
		if (Framework.getLanguageContexts().get(sLang) != null) {
			aVelocityContext = new VelocityContext(Framework.getLanguageContexts().get(sLang));
		} else {
			logger.debug("Context for " + sLang + " doesn't exist.");
			aVelocityContext = new VelocityContext(Framework.getLanguageContexts().get(Property.get("DEFAULT_LANGUAGE")));
		}
		
		XMLOutputFormater.logger.trace("produceOutput");
		XMLOutputFormater.logger.debug("Map of String -> Object : "
				+ mapOfStringObject + ".");
		XMLOutputFormater.logger.debug("Writer : " + aWriter + ".");
		
		final EventCartridge aEventCartridge = new EventCartridge();
		aEventCartridge.addEventHandler(new XHTMLize());
		aEventCartridge.attachToContext(aVelocityContext);
		for (final String sObjectName : mapOfStringObject.keySet()) {
			aVelocityContext.put(sObjectName, mapOfStringObject
					.get(sObjectName));
		}
		
		Templates.write(sOutputFormat + ".vm", aVelocityContext, aWriter);
		aWriter.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.unicorn.output.OutputFormater#produceError(java.lang.Exception,
	 *      java.io.Writer)
	 */
	public void produceError(final Exception aException, final Writer aWriter)
			throws ResourceNotFoundException, ParseErrorException,
			MethodInvocationException, Exception {
		
		if (Framework.getLanguageContexts().get(sLang) != null) {
			aVelocityContext = new VelocityContext(Framework.getLanguageContexts().get(sLang));
		} else {
			logger.debug("Context for " + sLang + " doesn't exist.");
			aVelocityContext = new VelocityContext(Framework.getLanguageContexts().get(Property.get("DEFAULT_LANGUAGE")));
		}
		
		XMLOutputFormater.logger.trace("produceError");
		XMLOutputFormater.logger.debug("Error : " + aException + ".");
		XMLOutputFormater.logger.debug("Writer : " + aWriter + ".");
		final EventCartridge aEventCartridge = new EventCartridge();
		aEventCartridge.addEventHandler(new XHTMLize());
		aEventCartridge.attachToContext(aVelocityContext);
		aVelocityContext.put("error", aException);
		Templates.write(sOutputFormat + ".error.vm", aVelocityContext, aWriter);
		aWriter.close();
	}
}
