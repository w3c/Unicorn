// $Id: OutputFormater.java,v 1.3 2009-09-01 16:00:24 jean-gui Exp $
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
	 * @throws ResourceNotFoundException
	 * @throws ParseErrorException
	 * @throws MethodInvocationException
	 * @throws Exception
	 */
	public abstract void produceOutput(
			final Map<String, Object> mapOfStringObject, final Writer output)
			throws ResourceNotFoundException, ParseErrorException,
			MethodInvocationException, Exception;

	/**
	 * @param aException
	 * @param aWriter
	 * @throws Exception
	 * @throws MethodInvocationException
	 * @throws ParseErrorException
	 * @throws ResourceNotFoundException
	 */
	public abstract void produceError(final Exception aException,
			final Writer output) throws ResourceNotFoundException,
			ParseErrorException, MethodInvocationException, Exception;

}