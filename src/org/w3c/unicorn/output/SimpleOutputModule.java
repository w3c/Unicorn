// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.Writer;
import java.util.Map;

/**
 * This module allow to generate output in text format.
 * 
 * @author Damien LEROY
 */
public class SimpleOutputModule extends OutputModule {
	
	public SimpleOutputModule(Map<String, String> mapOfOutputParameters, Map<String, String> mapOfSpecificParameters) {
		super(mapOfOutputParameters, mapOfSpecificParameters);
	}
	
	public void produceOutput(Map<String, Object> mapOfStringObject, final Writer aWriter) {
		defaultOutputFormater.produceOutput(mapOfStringObject, aWriter);
	}

	public void produceError(Map<String, Object> mapOfStringObject, final Writer aWriter) {
		if (defaultOutputFormater.getFormat().equals("xhtml10")) {
			displayOnIndex(mapOfStringObject, aWriter);
			return;
		}
		defaultOutputFormater.produceError(mapOfStringObject, aWriter);
	}
	
}
