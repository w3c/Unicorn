// $Id: XMLOutputFormater.java,v 1.3 2008-06-17 13:41:11 fbatard Exp $
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
import org.apache.velocity.app.event.EventCartridge;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.w3c.unicorn.util.Property;

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
	 * Apache velocity engine for the output
	 */
	private static VelocityEngine aVelocityEngineOutput = new VelocityEngine();

	/**
	 * Apache velocity engine for the error
	 */
	private static VelocityEngine aVelocityEngineError = new VelocityEngine();

	/**
	 * Write the result of the XML in a file
	 * @param sOutputFormat format of the output
	 * @param sLang Language of the output
	 * @throws ResourceNotFoundException exception when resources not found using the path
	 * @throws ParseErrorException error in the parser
	 * @throws Exception odd error occur
	 */
	public XMLOutputFormater(final String sOutputFormat, final String sLang)
			throws ResourceNotFoundException, ParseErrorException, Exception {
		XMLOutputFormater.logger.trace("Constructor");
		if (XMLOutputFormater.logger.isDebugEnabled()) {
			XMLOutputFormater.logger.debug("Output format : " + sOutputFormat
					+ ".");
			XMLOutputFormater.logger.debug("Output language : " + sLang + ".");
		}
		String sFileName;
		sFileName = sLang + "_" + sOutputFormat
				+ Property.get("TEMPLATE_FILE_EXTENSION");
		// check if sFileName exist
		try {
			this.aTemplateOutput = XMLOutputFormater.aVelocityEngineOutput
					.getTemplate(sFileName);
		} catch (final ResourceNotFoundException aRNFE) {
			XMLOutputFormater.logger.warn("Resource " + sFileName
					+ " not found.", aRNFE);
			sFileName = Property.get("DEFAULT_LANGUAGE") + "_" + sOutputFormat
					+ Property.get("TEMPLATE_FILE_EXTENSION");
			XMLOutputFormater.logger.warn("Redirect to resource : " + sFileName
					+ ".");
			this.aTemplateOutput = XMLOutputFormater.aVelocityEngineOutput
					.getTemplate(sFileName);
		}
		try {
			this.aTemplateError = XMLOutputFormater.aVelocityEngineError
					.getTemplate(sFileName);
		} catch (final ResourceNotFoundException aRNFE) {
			XMLOutputFormater.logger.warn("Resource " + sFileName
					+ " not found.", aRNFE);
			sFileName = Property.get("DEFAULT_LANGUAGE") + "_" + sOutputFormat
					+ Property.get("TEMPLATE_FILE_EXTENSION");
			XMLOutputFormater.logger.warn("Redirect to resource : " + sFileName
					+ ".");
			this.aTemplateError = XMLOutputFormater.aVelocityEngineError
					.getTemplate(sFileName);
		}
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
		if (XMLOutputFormater.logger.isDebugEnabled()) {
			XMLOutputFormater.logger.debug("Map of String -> Object : "
					+ mapOfStringObject + ".");
			XMLOutputFormater.logger.debug("Writer : " + aWriter + ".");
		}

		final VelocityContext aVelocityContext = new VelocityContext();
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
		if (XMLOutputFormater.logger.isDebugEnabled()) {
			XMLOutputFormater.logger.debug("Error : " + aException + ".");
			XMLOutputFormater.logger.debug("Writer : " + aWriter + ".");
		}
		final VelocityContext aVelocityContext = new VelocityContext();
		final EventCartridge aEventCartridge = new EventCartridge();
		aEventCartridge.addEventHandler(new EscapeXMLEntities());
		aEventCartridge.attachToContext(aVelocityContext);

		aVelocityContext.put("error", aException);
		this.aTemplateError.merge(aVelocityContext, aWriter);
	}

	static {
		try {
			final Properties aProperties = new Properties();
			aProperties.load(new URL("file:"
					+ Property.get("VELOCITY_CONFIG_FILE")).openStream());

			aProperties.put(Velocity.FILE_RESOURCE_LOADER_PATH, Property
					.get("PATH_TO_OUTPUT_TEMPLATES"));
			XMLOutputFormater.aVelocityEngineOutput.init(aProperties);
			XMLOutputFormater.logger.debug("OutputEngine "
					+ Velocity.FILE_RESOURCE_LOADER_PATH);
			aProperties.put(Velocity.FILE_RESOURCE_LOADER_PATH, Property
					.get("PATH_TO_OUTPUT_ERROR_TEMPLATES"));
			XMLOutputFormater.aVelocityEngineError.init(aProperties);
		} catch (final Exception e) {
			XMLOutputFormater.logger.error("Exception : " + e.getMessage(), e);
			e.printStackTrace();
		}
	}

}
