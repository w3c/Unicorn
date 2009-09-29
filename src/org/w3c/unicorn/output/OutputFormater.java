// $Id: OutputFormater.java,v 1.7 2009-09-29 16:03:31 tgambet Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.Writer;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.util.Property;

/**
 * SimpleOutputFormater<br />
 * Created: Jul 19, 2006 5:54:33 PM<br />
 * 
 * @author Jean-Guilhem Rouel
 */
public abstract class OutputFormater {

	protected String mimeType;
	
	protected String format;
	
	protected String lang;
	
	public OutputFormater(String format, String lang) {
		logger.trace("Constructor");
		logger.debug("Output format : " + format + ".");
		logger.debug("Output language : " + lang + ".");
		
		this.mimeType = Property.getProps("output.properties").getProperty(format + ".mimetype");
		this.format = format;
		this.lang = lang;
	}
	
	public static final Log logger = LogFactory.getLog(OutputFormater.class);
	
	/**
	 * @param mapOfStringObject
	 * @param output
	 */
	public abstract void produceOutput(Map<String, Object> mapOfStringObject, Writer output);

	/**
	 * @param messages 
	 * @param aWriter
	 */
	public abstract void produceError(Map<String, Object> mapOfStringObject, Writer output);	

	public String getMimeType() {
		return mimeType;
	}
	
	public String getLang() {
		return lang;
	}

	public String getFormat() {
		return format;
	}

}