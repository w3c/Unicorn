// $Id: OutputFormater.java,v 1.6 2009-09-21 15:50:19 tgambet Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.Writer;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * SimpleOutputFormater<br />
 * Created: Jul 19, 2006 5:54:33 PM<br />
 * 
 * @author Jean-Guilhem Rouel
 */
public interface OutputFormater {

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

}