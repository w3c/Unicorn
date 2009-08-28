// $Id: OutputModule.java,v 1.2 2009-08-28 12:40:06 jean-gui Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

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
	 * @throws IOException
	 * @throws Exception
	 * @throws MethodInvocationException
	 * @throws ParseErrorException
	 * @throws ResourceNotFoundException
	 */
	public abstract void produceOutput(final OutputFormater aOutputFormater,
			final Map<String, Object> mapOfStringObject,
			final Map<String, String[]> mapOfParameter, final Writer aWriter)
			throws IOException, ResourceNotFoundException, ParseErrorException,
			MethodInvocationException, Exception;

	/**
	 * Generates an error output
	 * 
	 * @throws IOException
	 * @throws Exception
	 * @throws MethodInvocationException
	 * @throws ParseErrorException
	 * @throws ResourceNotFoundException
	 */
	public abstract void produceError(final OutputFormater aOutputFormater,
			final Exception error, final Map<String, String[]> mapOfParameter,
			final Writer aWriter) throws IOException,
			ResourceNotFoundException, ParseErrorException,
			MethodInvocationException, Exception;

}
