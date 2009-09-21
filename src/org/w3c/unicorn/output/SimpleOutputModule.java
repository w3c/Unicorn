// $Id: SimpleOutputModule.java,v 1.5 2009-09-21 15:50:19 tgambet Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.Writer;
import java.util.Map;

/**
 * This module allow to generate output in text format.
 * 
 * @author Damien LEROY
 */
public class SimpleOutputModule implements OutputModule {

	public void produceOutput(final OutputFormater aOutputFormater, Map<String, Object> mapOfStringObject,
			final Map<String, String> mapOfParameter, final Writer aWriter) {
		aOutputFormater.produceOutput(mapOfStringObject, aWriter);
	}

	public void produceError(final OutputFormater aOutputFormater, Map<String, Object> mapOfStringObject,
			final Map<String, String> mapOfParameter, final Writer aWriter) {
		aOutputFormater.produceError(mapOfStringObject, aWriter);
	}

}
