// $Id: SimpleOutputFormater.java,v 1.6 2009-07-29 09:18:24 tgambet Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.Writer;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.w3c.unicorn.util.TemplateHelper;

/**
 * Class for simple output formater.
 * 
 * @author Jean-Guilhem ROUEL
 */
public class SimpleOutputFormater implements OutputFormater {

	private static final Log logger = OutputFactory.logger;

	private Template aTemplateOutput = null;

	private Template aTemplateError = null;
	
	private static VelocityContext aVelocityContext = new VelocityContext();

	public SimpleOutputFormater(final String sOutputFormat, final String sLang)
			throws ResourceNotFoundException, ParseErrorException, Exception {
		SimpleOutputFormater.logger.trace("Constructor");
		SimpleOutputFormater.logger.debug("Output format : " + sOutputFormat + ".");
		SimpleOutputFormater.logger.debug("Output language : " + sLang + ".");
		this.aTemplateOutput = TemplateHelper.getInternationalizedTemplate(sOutputFormat, sLang, aVelocityContext);
		this.aTemplateError = TemplateHelper.getInternationalizedTemplate(sOutputFormat + ".error", sLang, aVelocityContext);
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
		SimpleOutputFormater.logger.trace("produceOutput");
		SimpleOutputFormater.logger.debug("Map of String -> Object : " + mapOfStringObject + ".");
		SimpleOutputFormater.logger.debug("Writer : " + aWriter + ".");
		for (final String sObjectName : mapOfStringObject.keySet())
			aVelocityContext.put(sObjectName, mapOfStringObject.get(sObjectName));
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
		SimpleOutputFormater.logger.trace("produceError");
		SimpleOutputFormater.logger.debug("Error : " + aException.getMessage() + ".");
		SimpleOutputFormater.logger.debug("Writer : " + aWriter + ".");
		aVelocityContext.put("error", aException);
		this.aTemplateError.merge(aVelocityContext, aWriter);
	}
}
