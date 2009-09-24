// $Id: SimpleOutputFormater.java,v 1.7 2009-09-24 15:29:35 tgambet Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.velocity.VelocityContext;
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
		logger.trace("Constructor");
		logger.debug("Output format : " + format + ".");
		logger.debug("Output language : " + lang + ".");
		
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
	
	public void produceOutput(Map<String, Object> mapOfStringObject, Writer output) {
		logger.trace("produceOutput");
		logger.debug("Map of String -> Object : " + mapOfStringObject + ".");
		logger.debug("Writer : " + output + ".");
		
		for (String sObjectName : mapOfStringObject.keySet())
			aVelocityContext.put(sObjectName, mapOfStringObject.get(sObjectName));
		
		Templates.write(format + ".vm", aVelocityContext, output);
		try {
			output.close();
		} catch (IOException e) {
			logger.error("Error closing output writer: " + e.getMessage(), e);
		}
	}

	public void produceError(Map<String, Object> mapOfStringObject, Writer output) {
		logger.trace("produceError");
		logger.debug("Writer : " + output + ".");
		
		for (String sObjectName : mapOfStringObject.keySet())
			aVelocityContext.put(sObjectName, mapOfStringObject.get(sObjectName));
		
		Templates.write(format + ".error.vm", aVelocityContext, output);
		try {
			output.close();
		} catch (IOException e) {
			logger.error("Error closing output writer: " + e.getMessage(), e);
		}
	}

}
