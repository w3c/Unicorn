// $Id: SimpleOutputModule.java,v 1.7 2009-09-23 09:26:05 tgambet Exp $
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
public class SimpleOutputModule implements OutputModule {

	private  OutputFormater aOutputFormater;
	
	private Map<String, String> mapOfOutputParameters;
	
	public SimpleOutputModule(Map<String, String> mapOfOutputParameters, Map<String, String> mapOfSpecificParameters) {
		this.mapOfOutputParameters = mapOfOutputParameters;
		aOutputFormater = OutputFactory.createOutputFormater(mapOfOutputParameters.get("format"),
				mapOfOutputParameters.get("lang"), mapOfOutputParameters.get("mimetype"));
	}
	
	public void produceOutput(Map<String, Object> mapOfStringObject, final Writer aWriter) {
		aOutputFormater.produceOutput(mapOfStringObject, aWriter);
	}

	public void produceError(Map<String, Object> mapOfStringObject, final Writer aWriter) {
		aOutputFormater.produceError(mapOfStringObject, aWriter);
	}

	public void produceFirstOutput(Map<String, Object> mapOfStringObject, Writer aWriter) {
		return;
	}

	public String getOutputParameter(String string) {
		return mapOfOutputParameters.get(string);
	}
	
	

}
