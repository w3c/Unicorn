// $Id: XMLOutputFormater.java,v 1.9 2009-08-11 13:43:01 jean-gui Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.Writer;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.event.EventCartridge;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.w3c.unicorn.util.TemplateHelper;

/**
 * Class for XML output formater.
 * 
 * @author Jean-Guilhem ROUEL
 */
public class XMLOutputFormater implements OutputFormater {

	/**
	 * Object used for complex logging purpose
	 */
	private static final Log logger = OutputFactory.logger;

	/**
	 * Template for the standard output
	 */
	private Template aTemplateOutput = null;

	/**
	 * Template used for the standard error
	 */
	private Template aTemplateError = null;

	/**
	 * Apache velocity context
	 */
	private static VelocityContext aVelocityContext = new VelocityContext();

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
		XMLOutputFormater.logger
				.debug("Output format : " + sOutputFormat + ".");
		XMLOutputFormater.logger.debug("Output language : " + sLang + ".");

		aTemplateOutput = TemplateHelper.getInternationalizedTemplate(
				sOutputFormat, sLang, aVelocityContext);
		aTemplateError = TemplateHelper.getInternationalizedTemplate(
				sOutputFormat + ".error", sLang, aVelocityContext);
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
		XMLOutputFormater.logger.trace("produceOutput");
		XMLOutputFormater.logger.debug("Map of String -> Object : "
				+ mapOfStringObject + ".");
		XMLOutputFormater.logger.debug("Writer : " + aWriter + ".");
		final EventCartridge aEventCartridge = new EventCartridge();
		aEventCartridge.addEventHandler(new XHTMLize());
		// aEventCartridge.addEventHandler(new EscapeXMLEntities());
		aEventCartridge.attachToContext(aVelocityContext);
		for (final String sObjectName : mapOfStringObject.keySet()) {
			aVelocityContext.put(sObjectName, mapOfStringObject
					.get(sObjectName));
		}
		this.aTemplateOutput.merge(aVelocityContext, aWriter);
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
		XMLOutputFormater.logger.trace("produceError");
		XMLOutputFormater.logger.debug("Error : " + aException + ".");
		XMLOutputFormater.logger.debug("Writer : " + aWriter + ".");
		final EventCartridge aEventCartridge = new EventCartridge();
		aEventCartridge.addEventHandler(new EscapeXMLEntities());
		aEventCartridge.attachToContext(aVelocityContext);
		aVelocityContext.put("error", aException);
		this.aTemplateError.merge(aVelocityContext, aWriter);
	}
}
