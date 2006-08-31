// $Id: DirectInputModule.java,v 1.1.1.1 2006-08-31 09:09:25 dleroy Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.input;

import java.io.IOException;

import javax.activation.MimeType;

import org.w3c.unicorn.contract.EnumInputMethod;

/**
 * @author Damien LEROY
 *
 */
public class DirectInputModule implements InputModule {

	private final EnumInputMethod aEnumInputMethod = EnumInputMethod.DIRECT;

	private String sInput = null;
	private MimeType aMimeType = null;

	protected DirectInputModule (
			final MimeType aMimeType,
			final Object oInputParameterValue) {
		DirectInputModule.logger.trace("Constructor");
		if (DirectInputModule.logger.isDebugEnabled()) {
			DirectInputModule.logger.debug("Mime type : " + aMimeType + ".");
			DirectInputModule.logger.debug("Input parameter value : " + oInputParameterValue + ".");
		}
		if (!(oInputParameterValue instanceof String)) {
			throw new IllegalArgumentException("Object oInputParameterValue : " + oInputParameterValue.toString() + ".");
		}
		this.aMimeType = aMimeType;
		this.sInput = (String) oInputParameterValue;
	}

	protected DirectInputModule (
			final InputModule aInputModule) throws IOException {
		DirectInputModule.logger.trace("Constructor");
		if (DirectInputModule.logger.isDebugEnabled()) {
			DirectInputModule.logger.debug("Input module : " + aInputModule + ".");
		}
		this.sInput = aInputModule.getStringContent();
		this.aMimeType = aInputModule.getMimeType();
	}

	public EnumInputMethod getEnumInputMethod () {
		DirectInputModule.logger.trace("getEnumInputMethod");
		return this.aEnumInputMethod;
	}

	public MimeType getMimeType () {
		DirectInputModule.logger.trace("getMimeType");
		return this.aMimeType;
	}

	public Object getParameterValue () {
		DirectInputModule.logger.trace("getParameterValue");
		return this.sInput;
	}

	public String getStringContent () {
		DirectInputModule.logger.trace("getStringContent");
		return this.sInput;
	}

	public void dispose () {
		DirectInputModule.logger.trace("dispose");
	}

	public String toString () {
		final int iStringBufferSize = 500;
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append("DirectInputModule");
		aStringBuffer.append("\n");
		aStringBuffer.append("Mime type : ").append(this.aMimeType);
		return aStringBuffer.toString();
	}

}
