// $Id: InputFactory.java,v 1.2 2008-06-17 13:41:12 fbatard Exp $
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
 * @author Damien LEROY Class which deals with the creation of input method
 */
public class InputFactory {

	/**
	 * Object used for complex logging purpose
	 */
	private static final Log logger = InputModule.logger;

	/**
	 * Used to create an inputModule
	 * 
	 * @param aMimeType
	 *            the mime-type of the input
	 * @param aEnumInputMethod
	 *            the method used for the input
	 * @param oInputParameterValue
	 *            the parameter for the input
	 * @return an input Module ready to use
	 */
	public static InputModule createInputModule(final MimeType aMimeType,
			final EnumInputMethod aEnumInputMethod,
			final Object oInputParameterValue) {
		InputFactory.logger.trace("createInputModule");
		if (InputFactory.logger.isDebugEnabled()) {
			InputFactory.logger.debug("Mime type : " + aMimeType + ".");
			InputFactory.logger.debug("Input method : " + aEnumInputMethod
					+ ".");
			InputFactory.logger.debug("Input parameter value : "
					+ oInputParameterValue + ".");
		}
		switch (aEnumInputMethod) {
		case DIRECT:
			return new DirectInputModule(aMimeType, oInputParameterValue);
		case UPLOAD:
			return new FileItemInputModule(aMimeType, oInputParameterValue);
		case URI:
			return new URIInputModule(aMimeType, oInputParameterValue);
		}
		return null;
	}

	/**
	 * Data structure for all the input modules
	 */
	private Map<EnumInputMethod, InputModule> mapOfInputModule = null;

	/**
	 * Default input module
	 */
	private InputModule aInputModuleDefault = null;

	/**
	 * Constructor of the input factory
	 * 
	 * @param aMimeType
	 *            mime-type of the input
	 * @param aEnumInputMethod
	 *            method used for the input
	 * @param oInputParameterValue
	 *            parameter of the input
	 */
	public InputFactory(final MimeType aMimeType,
			final EnumInputMethod aEnumInputMethod,
			final Object oInputParameterValue) {
		InputFactory.logger.trace("Constructor");
		if (InputFactory.logger.isDebugEnabled()) {
			InputFactory.logger.debug("Mime type : " + aMimeType + ".");
			InputFactory.logger.debug("Input method : " + aEnumInputMethod
					+ ".");
			InputFactory.logger.debug("Input parameter value : "
					+ oInputParameterValue + ".");
		}
		this.mapOfInputModule = new LinkedHashMap<EnumInputMethod, InputModule>();
		this.aInputModuleDefault = InputFactory.createInputModule(aMimeType,
				aEnumInputMethod, oInputParameterValue);
		this.mapOfInputModule.put(aEnumInputMethod, this.aInputModuleDefault);
	}

	/**
	 * Get an input module depending on its method
	 * 
	 * @param aEnumInputMethod
	 *            method used for the input
	 * @return the input module found or created
	 * @throws IOException
	 *             odd error occured
	 */
	public InputModule getInputModule(final EnumInputMethod aEnumInputMethod)
			throws IOException {
		InputFactory.logger.trace("getInputModule");
		if (InputFactory.logger.isDebugEnabled()) {
			InputFactory.logger.debug("Input method : " + aEnumInputMethod
					+ ".");
		}
		final InputModule aInputModule = this.mapOfInputModule
				.get(aEnumInputMethod);
		if (null != aInputModule) {
			return aInputModule;
		}
		return this.createInputModule(aEnumInputMethod);
	}

	/**
	 * Creation of an input module
	 * 
	 * @param aEnumInputMethod
	 *            method of the input module
	 * @return a new input module
	 * @throws IOException
	 *             odd error occured
	 */
	public InputModule createInputModule(final EnumInputMethod aEnumInputMethod)
			throws IOException {
		InputFactory.logger.trace("createInputModule");
		if (InputFactory.logger.isDebugEnabled()) {
			InputFactory.logger.debug("Input method : " + aEnumInputMethod
					+ ".");
		}
		switch (aEnumInputMethod) {
		case DIRECT:
			return new DirectInputModule(this.aInputModuleDefault);
		case UPLOAD:
			return new FakeUploadInputModule(this.aInputModuleDefault);
		case URI:
			return new URIInputModule(this.aInputModuleDefault);
		}
		return null;
	}

	public MimeType getMimeType() {
		InputFactory.logger.trace("getMimetype");
		return this.aInputModuleDefault.getMimeType();
	}

	public InputModule getDefaultInputModule() {
		InputFactory.logger.trace("getDefaultInputModule");
		return this.aInputModuleDefault;
	}

	/**
	 * Dispose the object
	 * 
	 */
	public void dispose() {
		InputFactory.logger.trace("dispose");
		for (final InputModule aInputModule : this.mapOfInputModule.values()) {
			aInputModule.dispose();
		}
	}

	/**
	 * Prints the object
	 */
	public String toString() {
		final int iStringBufferSize = 500;
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append("InputFactory").append("\n");
		aStringBuffer.append("Default : ").append(
				this.aInputModuleDefault.getEnumInputMethod()).append("\n");
		for (final InputModule aInputModule : this.mapOfInputModule.values()) {
			aStringBuffer.append(aInputModule).append("\n");
		}
		return aStringBuffer.toString();
	}

}
