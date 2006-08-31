// $Id: SimpleOutputModule.java,v 1.1.1.1 2006-08-31 09:09:25 dleroy Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.Writer;
import java.util.Map;

import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * This module allow to generate output in text format.
 * @author Damien LEROY
 */
public class SimpleOutputModule implements OutputModule {

	public void produceOutput (
			final OutputFormater aOutputFormater,
			final Map<String, Object> mapOfStringObject,
			final Map<String, String[]> mapOfParameter,
			final Writer aWriter) throws
			ResourceNotFoundException,
			ParseErrorException,
			MethodInvocationException,
			Exception {
		SimpleOutputModule.logger.trace("Constructor");
		aOutputFormater.produceOutput(mapOfStringObject, aWriter);
	}
	
	public void produceError (
			final OutputFormater aOutputFormater,
			final Exception aException,
			final Map<String, String[]> mapOfParameter,
			final Writer aWriter) throws
			ResourceNotFoundException,
			ParseErrorException,
			MethodInvocationException,
			Exception {
		SimpleOutputModule.logger.trace("produceError");
		aOutputFormater.produceError(aException, aWriter);
	}

}
