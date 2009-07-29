// $Id: OutputFactory.java,v 1.6 2009-07-29 13:23:34 tgambet Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.w3c.unicorn.util.Property;

/**
 * This class allow to manage all output module and output formater. It provide
 * a way to share an instance of a output module or output formater between
 * different call of the framework UniCORN.
 * 
 * @author Damien LEROY
 */
public class OutputFactory {

	protected static final Log logger = LogFactory
			.getLog("org.w3c.unicorn.output");

	private static final Map<EnumOutputModule, OutputModule> mapOfOutputModule = new LinkedHashMap<EnumOutputModule, OutputModule>();

	private static final Map<String, OutputFormater> mapOfOutputFormater = new LinkedHashMap<String, OutputFormater>();

	private static final Properties aPropertiesSpecialFormaters = new Properties();

	static {
		try {
			OutputFactory.aPropertiesSpecialFormaters.load(
					Property.getPropertyFileURL("specialFormaters.properties").openStream()
			);
		} catch (final IOException e) {
			OutputFactory.logger.error("IOException : " + e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Create a new output module and add it to the map.
	 * 
	 * @param aEnumOutputModule
	 *            To identify which type of output module will be created.
	 * @return The new output module.
	 */
	private static OutputModule createOutputModule(
			final EnumOutputModule aEnumOutputModule) {
		OutputFactory.logger.trace("createOutputModule");
		if (OutputFactory.logger.isDebugEnabled()) {
			OutputFactory.logger.debug("Output module : " + aEnumOutputModule
					+ ".");
		}
		final OutputModule aOutputModule;
		switch (aEnumOutputModule) {
		case SIMPLE:
			aOutputModule = new SimpleOutputModule();
			break;
		default:
			return null;
		}
		OutputFactory.mapOfOutputModule.put(aEnumOutputModule, aOutputModule);
		return aOutputModule;
	}

	/**
	 * Create a new output formater and add it to the map.
	 * 
	 * @param sOutputFormat
	 *            The format who the output formater must produce.
	 * @return The new output formater.
	 * @throws ResourceNotFoundException
	 * @throws ParseErrorException
	 * @throws Exception
	 */
	private static OutputFormater createOutputFormater(
			final String sOutputFormat, final String sLang,
			final String sMimeType) throws ResourceNotFoundException,
			ParseErrorException, Exception {
		OutputFactory.logger.trace("createOutputformater");
		if (OutputFactory.logger.isDebugEnabled()) {
			OutputFactory.logger
					.debug("Output format : " + sOutputFormat + ".");
			OutputFactory.logger.debug("Language : " + sLang + ".");
			OutputFactory.logger.debug("Mime type : " + sMimeType + ".");
		}

		final String sFormaterName = OutputFactory.aPropertiesSpecialFormaters
				.getProperty(sMimeType);
		if (null != sFormaterName) {
			final Class aFormaterClass = Class
					.forName("org.w3c.unicorn.output." + sFormaterName);
			final Class[] tClassParamType = { String.class, String.class };
			final Object[] tObjectParamValue = { sOutputFormat, sLang };
			final OutputFormater aOutputFormater;
			aOutputFormater = (OutputFormater) aFormaterClass.getConstructor(
					tClassParamType).newInstance(tObjectParamValue);
			OutputFactory.mapOfOutputFormater.put(sMimeType + "_" + sLang + "_"
					+ sOutputFormat, aOutputFormater);
			return aOutputFormater;
		}

		final OutputFormater aOutputFormater;
		aOutputFormater = new SimpleOutputFormater(sOutputFormat, sLang);
		OutputFactory.mapOfOutputFormater.put(sLang + "_" + sOutputFormat,
				aOutputFormater);
		return aOutputFormater;
	}

	/**
	 * Return the output module asked.
	 * 
	 * @param sOutputModule
	 *            The name of the output module to return.
	 * @return The output module asked.
	 */
	public static OutputModule getOutputModule(final String sOutputModule) {
		OutputFactory.logger.trace("getOutputModule");
		if (OutputFactory.logger.isDebugEnabled()) {
			OutputFactory.logger
					.debug("Output module : " + sOutputModule + ".");
		}
		final EnumOutputModule aEnumOutputModule = EnumOutputModule
				.fromValue(sOutputModule);
		if (null == aEnumOutputModule) {
			OutputFactory.logger.error("Unknow output module.");
			return null;
		}
		return OutputFactory.getOutputModule(aEnumOutputModule);
	}

	/**
	 * Retuern the output module asked.
	 * 
	 * @param aEnumOutputModule
	 * @return The output module asked.
	 */
	public static OutputModule getOutputModule(
			final EnumOutputModule aEnumOutputModule) {
		OutputFactory.logger.trace("getOutputModule");
		if (OutputFactory.logger.isDebugEnabled()) {
			OutputFactory.logger.debug("Output module : " + aEnumOutputModule
					+ ".");
		}
		final OutputModule aOutputModule = OutputFactory.mapOfOutputModule
				.get(aEnumOutputModule);
		// if output module not already exist
		if (null == aOutputModule) {
			// create it
			return OutputFactory.createOutputModule(aEnumOutputModule);
		}
		return aOutputModule;
	}

	/**
	 * Return the output formater asked.
	 * 
	 * @param sOutputFormat
	 *            The output format who be puduce by the output formater.
	 * @return The output formater asked.
	 * @throws ResourceNotFoundException
	 * @throws ParseErrorException
	 * @throws Exception
	 */
	public static OutputFormater getOutputFormater(final String sOutputFormat,
			final String sLang, final String sMimeType)
			throws ResourceNotFoundException, ParseErrorException, Exception {
		OutputFactory.logger.trace("getOutputformater");
		if (OutputFactory.logger.isDebugEnabled()) {
			OutputFactory.logger
					.debug("Output format : " + sOutputFormat + ".");
			OutputFactory.logger.debug("Language : " + sLang + ".");
		}
		OutputFormater aOutputFormater = OutputFactory.mapOfOutputFormater
				.get(sMimeType + "_" + sLang + "_" + sOutputFormat);
		if (null != aOutputFormater) {
			return aOutputFormater;
		}
		aOutputFormater = OutputFactory.mapOfOutputFormater.get(sLang + "_"
				+ sOutputFormat);

		if (null != aOutputFormater) {
			return aOutputFormater;
		}
		// if output formater not already exist create it
		aOutputFormater = OutputFactory.createOutputFormater(sOutputFormat,
				sLang, sMimeType);

		return aOutputFormater;
	}

}
