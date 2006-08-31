// $Id: SimpleOutputFormater.java,v 1.1.1.1 2006-08-31 09:09:25 dleroy Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.Writer;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.w3c.unicorn.util.Property;

/**
 * Class for simple output formater.
 * @author Jean-Guilhem ROUEL
 */
public class SimpleOutputFormater implements OutputFormater {

	private static final Log logger = OutputFactory.logger;

	private Template aTemplateOutput = null;
	private Template aTemplateError = null;

	private static VelocityEngine aVelocityEngineOutput = new VelocityEngine();			
	private static VelocityEngine aVelocityEngineError = new VelocityEngine();

	public SimpleOutputFormater (
			final String sOutputFormat,
			final String sLang) throws
			ResourceNotFoundException,
			ParseErrorException,
			Exception {
		SimpleOutputFormater.logger.trace("Constructor");
		if (SimpleOutputFormater.logger.isDebugEnabled()) {
			SimpleOutputFormater.logger.debug("Output format : " + sOutputFormat + ".");
			SimpleOutputFormater.logger.debug("Output language : " + sLang + ".");
		}
		final String sFileName;
		sFileName = sLang + "_" +	sOutputFormat +	Property.get("TEMPLATE_FILE_EXTENSION");
		this.aTemplateOutput = SimpleOutputFormater.aVelocityEngineOutput.getTemplate(sFileName);
		this.aTemplateError = SimpleOutputFormater.aVelocityEngineError.getTemplate(sFileName);
	}

	/* (non-Javadoc)
	 * @see org.w3c.unicorn.output.OutputFormater#produceOutput(java.util.Map, java.io.Writer)
	 */
	public void produceOutput (
			final Map<String, Object> mapOfStringObject,
			final Writer aWriter) 
	throws ResourceNotFoundException, ParseErrorException, 
			MethodInvocationException, Exception {
		SimpleOutputFormater.logger.trace("produceOutput");
		if (SimpleOutputFormater.logger.isDebugEnabled()) {
			SimpleOutputFormater.logger.debug("Map of String -> Object : " + mapOfStringObject + ".");
			SimpleOutputFormater.logger.debug("Writer : " + aWriter + ".");
		}
		final VelocityContext aVelocityContext = new VelocityContext();
		for (final String sObjectName : mapOfStringObject.keySet()) {
			aVelocityContext.put(sObjectName, mapOfStringObject.get(sObjectName));
		}
		this.aTemplateOutput.merge(aVelocityContext, aWriter);
	}

	/* (non-Javadoc)
	 * @see org.w3c.unicorn.output.OutputFormater#produceError(java.lang.Exception, java.io.Writer)
	 */
	public void produceError (
			final Exception aException,
			final Writer aWriter) throws
			ResourceNotFoundException,
			ParseErrorException,
			MethodInvocationException,
			Exception {
		SimpleOutputFormater.logger.trace("produceError");
		if (SimpleOutputFormater.logger.isDebugEnabled()) {
			SimpleOutputFormater.logger.debug("Error : " + aException.getMessage() + ".");
			SimpleOutputFormater.logger.debug("Writer : " + aWriter + ".");
		}
		final VelocityContext aVelocityContext = new VelocityContext();
		aVelocityContext.put("error", aException);
		this.aTemplateError.merge(aVelocityContext, aWriter);
	}

	static {		
		try {
			final Properties aProperties = new Properties();
			aProperties.load(new URL("file:" + Property.get("VELOCITY_CONFIG_FILE")).openStream());
			
			aProperties.put(Velocity.FILE_RESOURCE_LOADER_PATH, 
					Property.get("PATH_TO_OUTPUT_TEMPLATES")); 			
			SimpleOutputFormater.aVelocityEngineOutput.init(aProperties);
			SimpleOutputFormater.logger.debug(
					"OutputEngine " +
					Velocity.FILE_RESOURCE_LOADER_PATH);
			aProperties.put(
					Velocity.FILE_RESOURCE_LOADER_PATH, 
					Property.get("PATH_TO_OUTPUT_ERROR_TEMPLATES"));
			SimpleOutputFormater.aVelocityEngineError.init(aProperties);
		}
		catch (final Exception e) {
			SimpleOutputFormater.logger.error("Exception : "+e.getMessage(), e);
			e.printStackTrace();
		}
	}

}
