// $Id: OutputFactory.java,v 1.7 2009-09-23 09:26:05 tgambet Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

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
		
		String format = mapOfOutputParameter.get("format");
		String lang = mapOfOutputParameter.get("lang"); 
		String mimeType = mapOfOutputParameter.get("mimetype");
		
		/* Commented out for now as this is unnecessary and that doesn't seem quite safe */		
//		if(null == module || "".equals(module)) {
//			module = "simple";
//		}
//		
//		module = module.substring(0, 1).toUpperCase() + module.substring(1);
//		
//		Class<?> moduleClass;
//		try {
//			moduleClass = Class.forName("org.w3c.unicorn.output." + module + "OutputModule");
//			return (OutputModule) moduleClass.getConstructor().newInstance();
//		} catch (Exception e) {
//			logger.error("Couldn't create output module " + module + ". Will use SimpleOutputModule", e);
//		}

		return new SimpleOutputModule(mapOfOutputParameter, mapOfSpecificParameter);
		//return new MailOutputModule(mapOfOutputParameter, mapOfSpecificParameter);
	}

	/**
	 * Create a new output formatter.
	 * 
	 * @param sOutputFormat
	 *            The format who the output formatter must produce.
	 * @return The new output formatter.
	 */
	public static OutputFormater createOutputFormater(final String sOutputFormat,
			final String sLang, final String sMimeType)  {
		
		logger.trace("createOutputformater");
		logger.debug("Output format : " + sOutputFormat + ".");
		logger.debug("Language : " + sLang + ".");
		logger.debug("Mime type : " + sMimeType + ".");

		OutputFormater aOutputFormater;
		
		String sFormaterName = Property.getProps("specialFormaters.properties").getProperty(sMimeType);
		
		if (null != sFormaterName) {
			try {
				final Class<?> aFormaterClass = Class.forName("org.w3c.unicorn.output." + sFormaterName);
				final Class<?>[] tClassParamType = { String.class, String.class };
				final Object[] tObjectParamValue = { sOutputFormat, sLang };
				aOutputFormater = (OutputFormater) aFormaterClass.getConstructor(tClassParamType).newInstance(tObjectParamValue);
			} catch (Exception e) {
				logger.error("Error instanciating outputFormater: " + sFormaterName + ". Using SimpleOutputFormater instead.", e);
				aOutputFormater = new SimpleOutputFormater(sOutputFormat, sLang);
			} 
		}
		else {
			aOutputFormater = new SimpleOutputFormater(sOutputFormat, sLang);
		}

		return aOutputFormater;
	}
	
}
