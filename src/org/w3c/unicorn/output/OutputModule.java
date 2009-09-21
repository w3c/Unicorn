// $Id: OutputModule.java,v 1.6 2009-09-21 15:50:19 tgambet Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.Writer;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Interface for all output module.
 * 
 * @author Damien LEROY
 */
public interface OutputModule {

	public static final Log logger = LogFactory.getLog(OutputModule.class);

	/**
	 * Generate the output of all response.
	 * 
	 */
	public abstract void produceOutput(final OutputFormater aOutputFormater, Map<String, Object> mapOfStringObject,
			final Map<String, String> mapOfParameter, final Writer aWriter);

	/**
	 * Generates an error output
	 * 
	 */
	public abstract void produceError(final OutputFormater aOutputFormater, Map<String, Object> mapOfStringObject,
			final Map<String, String> mapOfParameter, final Writer aWriter);

}
