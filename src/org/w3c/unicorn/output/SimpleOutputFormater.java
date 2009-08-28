// $Id: SimpleOutputFormater.java,v 1.2 2009-08-28 12:40:06 jean-gui Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.Writer;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.util.Templates;

/**
 * Class for simple output formater.
 * 
 * @author Jean-Guilhem ROUEL
 */
public class SimpleOutputFormater implements OutputFormater {

	private static final Log logger = LogFactory.getLog(SimpleOutputFormater.class);

	private static VelocityContext aVelocityContext;
	
	private String sOutputFormat;
	
	private String sLang;

	public SimpleOutputFormater(final String sOutputFormat, final String sLang)
		throws ResourceNotFoundException, ParseErrorException, Exception {
		SimpleOutputFormater.logger.trace("Constructor");
		SimpleOutputFormater.logger.debug("Output format : " + sOutputFormat + ".");
		SimpleOutputFormater.logger.debug("Output language : " + sLang + ".");
		
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
			aVelocityContext = new VelocityContext(Framework.getLanguageContexts().get(Property.get("DEFAULT_LANGUAGE")));
		}
		
		SimpleOutputFormater.logger.trace("produceOutput");
		SimpleOutputFormater.logger.debug("Map of String -> Object : "
				+ mapOfStringObject + ".");
		SimpleOutputFormater.logger.debug("Writer : " + aWriter + ".");
		
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
			aVelocityContext = new VelocityContext(Framework.getLanguageContexts().get(Property.get("DEFAULT_LANGUAGE")));
		}
		
		SimpleOutputFormater.logger.trace("produceError");
		SimpleOutputFormater.logger.debug("Error : " + aException.getMessage()
				+ ".");
		SimpleOutputFormater.logger.debug("Writer : " + aWriter + ".");
		if (aException != null)
			aVelocityContext.put("error", aException);
		
		Templates.write(sOutputFormat + ".error.vm", aVelocityContext, aWriter);
		aWriter.close();
	}
}
