// $Id: MailOutputModule.java,v 1.1 2009-09-22 12:39:29 tgambet Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.Writer;
import java.util.Map;

/**
 * This module allow to generate output in mail format.
 * 
 * @author Thomas GAMBET
 */
public class MailOutputModule implements OutputModule {

	public void produceFirstOutput(OutputFormater aOutputFormater,
			Map<String, Object> mapOfStringObject,
			Map<String, String> mapOfParameter, Writer aWriter) {
		
		
		aOutputFormater.produceOutput(mapOfStringObject, aWriter);
		
	}
	
	public void produceOutput(final OutputFormater aOutputFormater, Map<String, Object> mapOfStringObject,
			final Map<String, String> mapOfParameter, final Writer aWriter) {
		
		
		
		aOutputFormater.produceOutput(mapOfStringObject, aWriter);
	}

	public void produceError(final OutputFormater aOutputFormater, Map<String, Object> mapOfStringObject,
			final Map<String, String> mapOfParameter, final Writer aWriter) {
		
		
		
		aOutputFormater.produceError(mapOfStringObject, aWriter);
	}



}
