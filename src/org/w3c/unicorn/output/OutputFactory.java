// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.util.Property;

/**
 * This class allow to manage all output module and output formatter. It provide
 * a way to share an instance of a output module or output formater between
 * different call of the framework UniCORN.
 * 
 * @author Damien LEROY
 */
public class OutputFactory {

	protected static final Log logger = LogFactory.getLog(OutputFactory.class);

	/**
	 * Create a new output module
	 * @param mapOfSpecificParameter 
	 * 
	 * @param aEnumOutputModule
	 *            To identify which type of output module will be created.
	 * @return The new output module.
	 */
	public static OutputModule createOutputModule(Map<String, String> mapOfOutputParameter, Map<String, String> mapOfSpecificParameter) {
		logger.trace("createOutputModule");
		logger.debug("Output module : " + mapOfOutputParameter);
		
		String module = mapOfOutputParameter.get("output");
		
		/* Commented out for now as this is unnecessary and that doesn't seem quite safe */		
		if(null == module || "".equals(module) || Property.get("ENABLED_MODULES") == null || !Arrays.asList(Property.get("ENABLED_MODULES").split("\\s*,\\s*")).contains(module)) {
			module = "simple";
		}
		
		module = module.substring(0, 1).toUpperCase() + module.substring(1);
		
		Class<?> moduleClass;
		try {
			logger.debug("Trying to instantiate OutputModule: org.w3c.unicorn.output." + module + "OutputModule");
			moduleClass = Class.forName("org.w3c.unicorn.output." + module + "OutputModule");
			Class<?>[] parameters = {Map.class, Map.class};
			return (OutputModule) moduleClass.getConstructor(parameters).newInstance(mapOfOutputParameter, mapOfSpecificParameter);
		} catch (Exception e) {
			logger.error("Couldn't create output module " + module + ". Will use SimpleOutputModule", e);
		}

		return new SimpleOutputModule(mapOfOutputParameter, mapOfSpecificParameter);
	}

	/**
	 * Create a new output formatter.
	 * 
	 * @param sOutputFormat
	 *            The format who the output formatter must produce.
	 * @return The new output formatter.
	 */
	public static OutputFormater createOutputFormater(String sOutputFormat,	String sLang)  {
		
		if (sLang == null)
			sLang = Property.get("DEFAULT_LANGUAGE");
		
		if (sOutputFormat == null)
			sOutputFormat = Property.get("DEFAULT_FORMAT");
		
		logger.trace("createOutputformater");
		logger.debug("Output format : " + sOutputFormat + ".");
		logger.debug("Language : " + sLang + ".");
		
		OutputFormater aOutputFormater = null;
		
		String sFormaterName = Property.getProps("output.properties").getProperty(sOutputFormat + ".formater");
		String mimeType = Property.getProps("output.properties").getProperty(sOutputFormat + ".mimetype");
		
		if (sFormaterName == null)
			sFormaterName = "SimpleOutputFormater";
		if (mimeType == null)
			mimeType = "text/plain";
		
		try {
			final Class<?> aFormaterClass = Class.forName("org.w3c.unicorn.output." + sFormaterName);
			final Class<?>[] tClassParamType = { String.class, String.class, String.class };
			final Object[] tObjectParamValue = { sOutputFormat, sLang, mimeType };
			aOutputFormater = (OutputFormater) aFormaterClass.getConstructor(tClassParamType).newInstance(tObjectParamValue);
		} catch (Exception e) {
			logger.error("Error instanciating outputFormater: " + sFormaterName + ". Using SimpleOutputFormater instead.", e);
			aOutputFormater = new SimpleOutputFormater(sOutputFormat, sLang, mimeType);
		}

		return aOutputFormater;
	}
	
}
