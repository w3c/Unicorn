// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.input;

import java.io.IOException;

import javax.activation.MimeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.contract.EnumInputMethod;

/**
 * Interface for an input module
 * 
 * @author Damien LEROY
 */
public interface InputModule {

	public static final Log logger = LogFactory.getLog(InputModule.class);

	public EnumInputMethod getEnumInputMethod();

	public MimeType getMimeType();

	public Object getParameterValue();

	public String getStringContent() throws IOException;

	/**
	 * Make all action necessary to remove input module.
	 */
	public void dispose();

}
