// $Id: InputFactory.java,v 1.1.1.1 2006-08-31 09:09:25 dleroy Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.input;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.activation.MimeType;

import org.apache.commons.logging.Log;
import org.w3c.unicorn.contract.EnumInputMethod;

/**
 * @author Damien LEROY
 *
 */
public class InputFactory {

	private static final Log logger = InputModule.logger;

	public static InputModule createInputModule (
			final MimeType aMimeType,
			final EnumInputMethod aEnumInputMethod,
			final Object oInputParameterValue) {
		InputFactory.logger.trace("createInputModule");
		if (InputFactory.logger.isDebugEnabled()) {
			InputFactory.logger.debug("Mime type : " + aMimeType + ".");
			InputFactory.logger.debug("Input method : " + aEnumInputMethod + ".");
			InputFactory.logger.debug("Input parameter value : " + oInputParameterValue + ".");
		}
		switch (aEnumInputMethod) {
			case DIRECT :
				return new DirectInputModule(aMimeType, oInputParameterValue);
			case UPLOAD :
				return new FileItemInputModule(aMimeType, oInputParameterValue);
			case URI :
				return new URIInputModule(aMimeType, oInputParameterValue);
		}
		return null;
	}

	private Map<EnumInputMethod, InputModule> mapOfInputModule = null;
	private InputModule aInputModuleDefault = null;

	public InputFactory (
			final MimeType aMimeType,
			final EnumInputMethod aEnumInputMethod,
			final Object oInputParameterValue) {
		InputFactory.logger.trace("Constructor");
		if (InputFactory.logger.isDebugEnabled()) {
			InputFactory.logger.debug("Mime type : " + aMimeType + ".");
			InputFactory.logger.debug("Input method : " + aEnumInputMethod + ".");
			InputFactory.logger.debug("Input parameter value : " + oInputParameterValue + ".");
		}
		this.mapOfInputModule = new LinkedHashMap<EnumInputMethod, InputModule>();
		this.aInputModuleDefault = InputFactory.createInputModule(aMimeType, aEnumInputMethod, oInputParameterValue);
		this.mapOfInputModule.put(aEnumInputMethod, this.aInputModuleDefault);
	}

	public InputModule getInputModule (
			final EnumInputMethod aEnumInputMethod) throws IOException {
		InputFactory.logger.trace("getInputModule");
		if (InputFactory.logger.isDebugEnabled()) {
			InputFactory.logger.debug("Input method : " + aEnumInputMethod + ".");
		}
		final InputModule aInputModule = this.mapOfInputModule.get(aEnumInputMethod);
		if (null != aInputModule) {
			return aInputModule;
		}
		return this.createInputModule(aEnumInputMethod);
	}

	public InputModule createInputModule (
			final EnumInputMethod aEnumInputMethod) throws IOException {
		InputFactory.logger.trace("createInputModule");
		if (InputFactory.logger.isDebugEnabled()) {
			InputFactory.logger.debug("Input method : " + aEnumInputMethod + ".");
		}
		switch (aEnumInputMethod) {
			case DIRECT :
				return new DirectInputModule(this.aInputModuleDefault);
			case UPLOAD :
				return new FakeUploadInputModule(this.aInputModuleDefault);
			case URI :
				return new URIInputModule(this.aInputModuleDefault);
		}
		return null;
	}

	public MimeType getMimeType () {
		InputFactory.logger.trace("getMimetype");
		return this.aInputModuleDefault.getMimeType();
	}

	public InputModule getDefaultInputModule () {
		InputFactory.logger.trace("getDefaultInputModule");
		return this.aInputModuleDefault;
	}

	public void dispose () {
		InputFactory.logger.trace("dispose");
		for (final InputModule aInputModule : this.mapOfInputModule.values()) {
			aInputModule.dispose();
		}
	}

	public String toString () {
		final int iStringBufferSize = 500;
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append("InputFactory").append("\n");
		aStringBuffer.append("Default : ").append(this.aInputModuleDefault.getEnumInputMethod()).append("\n");
		for (final InputModule aInputModule : this.mapOfInputModule.values()) {
			aStringBuffer.append(aInputModule).append("\n");
		}
		return aStringBuffer.toString();
	}

}
