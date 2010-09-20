// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2010.
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
public class SimpleOutputFormater extends OutputFormater {

	protected VelocityContext aVelocityContext;
	
	public SimpleOutputFormater() {
		this(Property.get("DEFAULT_FORMAT"), Property.get("DEFAULT_LANGUAGE"), Property.getProps("output.properties").getProperty(Property.get("DEFAULT_FORMAT") + ".mimetype"));
	}

	public SimpleOutputFormater(final String format, final String lang, String mimeType) {
		super(format, lang, mimeType);
		
		if (Framework.getLanguageContexts().get(lang) != null) {
			aVelocityContext = new VelocityContext(Framework.getLanguageContexts().get(lang));
		} else {
			aVelocityContext = new VelocityContext(Framework.getLanguageContexts().get(Property.get("DEFAULT_LANGUAGE")));
		}
	}
	
	public void produceOutput(Map<String, Object> mapOfStringObject, Writer output) {
		logger.trace("produceOutput");
		logger.debug("Map of String -> Object : " + mapOfStringObject + ".");
		logger.debug("Writer : " + output + ".");
		
		if (mapOfStringObject != null)
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
		
		if (mapOfStringObject != null)
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
