// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.w3c.unicorn.exceptions.UnicornException;
import org.w3c.unicorn.util.Language;
import org.w3c.unicorn.util.Templates;

/**
 * Interface for all output module.
 * 
 * @author Damien LEROY
 */
public abstract class OutputModule {
	
	protected OutputFormater defaultOutputFormater;
	
	protected Map<String, String> outputParameters;
	
	protected Map<String, String> specificParameters;
	
	public static final Log logger = LogFactory.getLog(OutputModule.class);
	
	public OutputModule(Map<String, String> mapOfOutputParameters, Map<String, String> mapOfSpecificParameters) {
		this.outputParameters = mapOfOutputParameters;
		this.specificParameters = mapOfSpecificParameters;
		this.defaultOutputFormater = OutputFactory.createOutputFormater(outputParameters.get("format"), outputParameters.get("lang"));
	}
	
	/**
	 * Generate a first output before the requests are launched.
	 * 
	 */
	public void produceFirstOutput(Map<String, Object> mapOfStringObject, final Writer aWriter) throws UnicornException {
		return;
	}
	
	/**
	 * Generate the output of all response.
	 * 
	 */
	public void produceOutput(Map<String, Object> mapOfStringObject, final Writer aWriter) throws UnicornException {
		defaultOutputFormater.produceOutput(mapOfStringObject, aWriter);
	}

	/**
	 * Generates an error output
	 * 
	 */
	public void produceError(Map<String, Object> mapOfStringObject, final Writer aWriter) {
		defaultOutputFormater.produceError(mapOfStringObject, aWriter);
	}

	protected void displayOnIndex(Map<String, Object> mapOfStringObject, Writer writer) {
		VelocityContext context = new VelocityContext(Language.getContext(Language.getLocale(outputParameters.get("lang"))));
		
		for (final String sObjectName : mapOfStringObject.keySet())
			context.put(sObjectName, mapOfStringObject.get(sObjectName));
		
		Templates.write("index.vm", context, writer);
		try {
			writer.close();
		} catch (IOException e) {
			logger.error("Error closing output writer: " + e.getMessage(), e);
		}
	}
	
	public String getOutputParameter(String string) {
		return outputParameters.get(string);
	}
	
	public Map<String, String> getOutputParameters() {
		return outputParameters;
	}

	public void setOutputParameters(Map<String, String> mapOfOutputParameters) {
		this.outputParameters = mapOfOutputParameters;
	}
	
	public String getMimeType() {
		return defaultOutputFormater.getMimeType();
	}
}
