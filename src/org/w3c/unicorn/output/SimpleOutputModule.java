// $Id: SimpleOutputModule.java,v 1.8 2009-09-24 15:30:49 tgambet Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.Writer;
import java.util.Map;

import org.w3c.unicorn.output.OutputFactory;

/**
 * This module allow to generate output in text format.
 * 
 * @author Damien LEROY
 */
public class SimpleOutputModule extends OutputModule {

	private  OutputFormater aOutputFormater;
	
	public SimpleOutputModule(Map<String, String> mapOfOutputParameters, Map<String, String> mapOfSpecificParameters) {
		super(mapOfOutputParameters, mapOfSpecificParameters);
		
		aOutputFormater = OutputFactory.createOutputFormater(mapOfOutputParameters.get("format"),
				mapOfOutputParameters.get("lang"), mapOfOutputParameters.get("mimetype"));
	}
	
	public void produceOutput(Map<String, Object> mapOfStringObject, final Writer aWriter) {
		aOutputFormater.produceOutput(mapOfStringObject, aWriter);
	}

	public void produceError(Map<String, Object> mapOfStringObject, final Writer aWriter) {
		if (outputParameters.get("mimetype").equals("text/html")) {
			displayOnIndex(mapOfStringObject, aWriter);
			return;
		}
		aOutputFormater.produceError(mapOfStringObject, aWriter);
	}

	public void produceFirstOutput(Map<String, Object> mapOfStringObject, Writer aWriter) {
		return;
	}
	
}
