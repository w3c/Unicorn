// $Id: SimpleOutputModule.java,v 1.3 2009-09-07 16:32:20 tgambet Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.Writer;
import java.util.Map;

import org.w3c.unicorn.util.Message;

/**
 * This module allow to generate output in text format.
 * 
 * @author Damien LEROY
 */
public class SimpleOutputModule implements OutputModule {

	public void produceOutput(final OutputFormater aOutputFormater, Map<String, Object> mapOfStringObject,
			final Map<String, String[]> mapOfParameter, final Writer aWriter) {
		aOutputFormater.produceOutput(mapOfStringObject, aWriter);
	}

	public void produceError(final OutputFormater aOutputFormater, Message errorMessage,
			final Map<String, String[]> mapOfParameter, final Writer aWriter) {
		aOutputFormater.produceError(errorMessage, aWriter);
	}

}
