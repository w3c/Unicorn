// $Id: DirectInputModule.java,v 1.2 2008-06-17 13:41:12 fbatard Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.input;

import java.io.IOException;

import javax.activation.MimeType;

import org.w3c.unicorn.contract.EnumInputMethod;

/**
 * @author Damien LEROY Class used for the direct input method check
 */
public class DirectInputModule implements InputModule {

	/**
	 * Sets the input method to DIRECT
	 */
	private final EnumInputMethod aEnumInputMethod = EnumInputMethod.DIRECT;

	/**
	 * Parameter used for the direct call
	 */
	private String sInput = null;

	/**
	 * Mime-type used for the call
	 */
	private MimeType aMimeType = null;

	/**
	 * Build the DirectInput Module by filling the properties
	 * 
	 * @param aMimeType
	 *            mime-type of the module
	 * @param oInputParameterValue
	 *            input parameter for the module
	 */
	protected DirectInputModule(final MimeType aMimeType,
			final Object oInputParameterValue) {
		DirectInputModule.logger.trace("Constructor");
		if (DirectInputModule.logger.isDebugEnabled()) {
			DirectInputModule.logger.debug("Mime type : " + aMimeType + ".");
			DirectInputModule.logger.debug("Input parameter value : "
					+ oInputParameterValue + ".");
		}
		if (!(oInputParameterValue instanceof String)) {
			throw new IllegalArgumentException("Object oInputParameterValue : "
					+ oInputParameterValue.toString() + ".");
		}
		this.aMimeType = aMimeType;
		this.sInput = (String) oInputParameterValue;
	}

	/**
	 * Constructor for the Direct Input Module
	 * 
	 * @param aInputModule
	 *            InputModule to nest into a direct input module
	 * @throws IOException
	 *             for unknown error
	 */
	protected DirectInputModule(final InputModule aInputModule)
			throws IOException {
		DirectInputModule.logger.trace("Constructor");
		if (DirectInputModule.logger.isDebugEnabled()) {
			DirectInputModule.logger.debug("Input module : " + aInputModule
					+ ".");
		}
		this.sInput = aInputModule.getStringContent();
		this.aMimeType = aInputModule.getMimeType();
	}

	public EnumInputMethod getEnumInputMethod() {
		DirectInputModule.logger.trace("getEnumInputMethod");
		return this.aEnumInputMethod;
	}

	public MimeType getMimeType() {
		DirectInputModule.logger.trace("getMimeType");
		return this.aMimeType;
	}

	public Object getParameterValue() {
		DirectInputModule.logger.trace("getParameterValue");
		return this.sInput;
	}

	public String getStringContent() {
		DirectInputModule.logger.trace("getStringContent");
		return this.sInput;
	}

	/**
	 * Dispose the object
	 */
	public void dispose() {
		DirectInputModule.logger.trace("dispose");
	}

	/**
	 * Prints the object
	 */
	public String toString() {
		final int iStringBufferSize = 500;
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append("DirectInputModule");
		aStringBuffer.append("\n");
		aStringBuffer.append("Mime type : ").append(this.aMimeType);
		return aStringBuffer.toString();
	}

}
