// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.input;

import java.io.IOException;

import javax.activation.MimeType;

import org.w3c.unicorn.contract.EnumInputMethod;

/**
 * Class used for the direct input method check
 * 
 * @author Damien LEROY
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
		logger.trace("Constructor");
		logger.debug("Mime type : " + aMimeType + ".");
		logger.debug("Input parameter value : " + oInputParameterValue + ".");
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
	public DirectInputModule(final InputModule aInputModule)
			throws IOException {
		logger.trace("Constructor");
		logger.debug("Input module : " + aInputModule + ".");
		this.sInput = aInputModule.getStringContent();
		this.aMimeType = aInputModule.getMimeType();
	}

	public EnumInputMethod getEnumInputMethod() {
		logger.trace("getEnumInputMethod");
		return this.aEnumInputMethod;
	}

	public MimeType getMimeType() {
		logger.trace("getMimeType");
		return this.aMimeType;
	}

	public Object getParameterValue() {
		logger.trace("getParameterValue");
		return this.sInput;
	}

	public String getStringContent() {
		logger.trace("getStringContent");
		return this.sInput;
	}

	/**
	 * Dispose the object
	 */
	public void dispose() {
		logger.trace("dispose");
	}

	/**
	 * Prints the object
	 */
	@Override
	public String toString() {
		final int iStringBufferSize = 500;
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append("DirectInputModule{mimetype: ").append(
				this.aMimeType).append("}");

		return aStringBuffer.toString();
	}

}
