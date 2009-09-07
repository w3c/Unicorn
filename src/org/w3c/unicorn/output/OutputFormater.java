// $Id: OutputFormater.java,v 1.4 2009-09-07 16:32:20 tgambet Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.Writer;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.w3c.unicorn.util.Message;

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
	 * @param errorMessage 
	 * @param aWriter
	 */
	public abstract void produceError(Message errorMessage, Writer output);

}