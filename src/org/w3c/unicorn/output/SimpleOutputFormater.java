// $Id: SimpleOutputFormater.java,v 1.4 2009-09-07 16:32:20 tgambet Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.util.Message;
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
	
	public void produceOutput(final Map<String, Object> mapOfStringObject, final Writer output) {

		OutputFormater.logger.trace("produceOutput");
		OutputFormater.logger.debug("Map of String -> Object : " + mapOfStringObject + ".");
		OutputFormater.logger.debug("Writer : " + output + ".");
		
		for (final String sObjectName : mapOfStringObject.keySet())
			aVelocityContext.put(sObjectName, mapOfStringObject.get(sObjectName));
		
		Templates.write(format + ".vm", aVelocityContext, output);
		try {
			output.close();
		} catch (IOException e) {
			logger.error("Error closing output writer: " + e.getMessage(), e);
		}
	}

	public void produceError(Message errorMessage, final Writer output) {
		
		logger.trace("produceError");
		logger.debug("ErrorMessage : " + errorMessage.getMessage() + ".");
		logger.debug("Writer : " + output + ".");
		
		Message[] messages = {errorMessage};
		aVelocityContext.put("messages", messages);
		
		Templates.write(format + ".error.vm", aVelocityContext, output);
		try {
			output.close();
		} catch (IOException e) {
			logger.error("Error closing output writer: " + e.getMessage(), e);
		}
	}

}
