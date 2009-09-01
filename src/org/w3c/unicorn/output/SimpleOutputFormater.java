// $Id: SimpleOutputFormater.java,v 1.3 2009-09-01 16:00:24 jean-gui Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.Writer;
import java.util.Map;

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

	protected VelocityContext aVelocityContext;
	
	private String format;
	
	private String lang;
	
	public SimpleOutputFormater() {
		setLang(Property.get("DEFAULT_LANGUAGE"));
		setFormat(Property.get("DEFAULT_FORMAT"));
	}

	public SimpleOutputFormater(final String format, final String lang) {
		OutputFormater.logger.trace("Constructor");
		OutputFormater.logger.debug("Output format : " + format + ".");
		OutputFormater.logger.debug("Output language : " + lang + ".");
		
		setFormat(format);
		setLang(lang);		
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
		if (Framework.getLanguageContexts().get(lang) != null) {
			aVelocityContext = new VelocityContext(Framework.getLanguageContexts().get(lang));
		} else {
			aVelocityContext = new VelocityContext(Framework.getLanguageContexts().get(Property.get("DEFAULT_LANGUAGE")));
		}
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String outputFormat) {
		this.format = outputFormat;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.unicorn.output.OutputFormater#produceOutput(java.util.Map,
	 *      java.io.Writer)
	 */
	public void produceOutput(final Map<String, Object> mapOfStringObject,
			final Writer output) throws ResourceNotFoundException,
			ParseErrorException, MethodInvocationException, Exception {

		OutputFormater.logger.trace("produceOutput");
		OutputFormater.logger.debug("Map of String -> Object : "
				+ mapOfStringObject + ".");
		OutputFormater.logger.debug("Writer : " + output + ".");
		
		for (final String sObjectName : mapOfStringObject.keySet()) {
			aVelocityContext.put(sObjectName, mapOfStringObject
					.get(sObjectName));
		}
		
		Templates.write(format + ".vm", aVelocityContext, output);
		output.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.unicorn.output.OutputFormater#produceError(java.lang.Exception,
	 *      java.io.Writer)
	 */
	public void produceError(final Exception aException, final Writer output)
			throws ResourceNotFoundException, ParseErrorException,
			MethodInvocationException, Exception {
		
		OutputFormater.logger.trace("produceError");
		OutputFormater.logger.debug("Error : " + aException.getMessage()
				+ ".");
		OutputFormater.logger.debug("Writer : " + output + ".");
		if (aException != null)
			aVelocityContext.put("error", aException);
		
		Templates.write(format + ".error.vm", aVelocityContext, output);
		output.close();
	}

}
