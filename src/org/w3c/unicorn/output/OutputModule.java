// $Id: OutputModule.java,v 1.9 2009-09-24 15:28:42 tgambet Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.w3c.unicorn.util.Language;
import org.w3c.unicorn.util.Templates;

/**
 * Interface for all output module.
 * 
 * @author Damien LEROY
 */
public abstract class OutputModule {
	
	protected Map<String, String> outputParameters;
	
	protected Map<String, String> specificParameters;
	
	public static final Log logger = LogFactory.getLog(OutputModule.class);
	
	public OutputModule(Map<String, String> mapOfOutputParameters, Map<String, String> mapOfSpecificParameters) {
		this.outputParameters = mapOfOutputParameters;
		this.specificParameters = mapOfSpecificParameters;
	}
	
	/**
	 * Generate a first output before the requests are launched.
	 * 
	 */
	public abstract void produceFirstOutput(Map<String, Object> mapOfStringObject, final Writer aWriter);
	
	/**
	 * Generate the output of all response.
	 * 
	 */
	public abstract void produceOutput(Map<String, Object> mapOfStringObject, final Writer aWriter);

	/**
	 * Generates an error output
	 * 
	 */
	public abstract void produceError(Map<String, Object> mapOfStringObject, final Writer aWriter);

	protected void displayOnIndex(Map<String, Object> mapOfStringObject, Writer writer) {
		VelocityContext context = new VelocityContext(Language.getContext(outputParameters.get("lang")));
		
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
	
}
