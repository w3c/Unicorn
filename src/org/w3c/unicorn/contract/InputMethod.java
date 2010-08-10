// Author: Damien LEROY
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.contract;

/**
 * @author Damien LEROY
 * 
 */
public class InputMethod {

	/**
	 * Method of the call
	 */
	private CallMethod aCallMethod = null;

	/**
	 * Parameter of the call
	 */
	private CallParameter aCallParameter = null;

	private EnumInputMethod method;

	public InputMethod(EnumInputMethod method) {
		this.method = method;
	}

	public InputMethod(String method) {
		if ("URI".equals(method)) {
			this.method = EnumInputMethod.URI;
		} else if ("UPLOAD".equals(method)) {
			this.method = EnumInputMethod.UPLOAD;
		} else if ("DIRECT".equals(method)) {
			this.method = EnumInputMethod.DIRECT;
		}
	}

	public EnumInputMethod getMethod() {
		return this.method;
	}

	/**
	 * Get the current calling method
	 * 
	 * @return current calling method
	 */
	public CallMethod getCallMethod() {
		return this.aCallMethod;
	}

	/**
	 * Set the method of the call
	 * 
	 * @param aCallMethod
	 *            new calling method to add
	 */
	public void setCallMethod(final CallMethod aCallMethod) {
		this.aCallMethod = aCallMethod;
	}

	/**
	 * Get the current call parameter
	 * 
	 * @return the current call parameter
	 */
	public CallParameter getCallParameter() {
		return this.aCallParameter;
	}

	/**
	 * Set the current Call parameter
	 * 
	 * @param aCallParameter
	 *            call parameter to set
	 */
	public void setCallParameter(final CallParameter aCallParameter) {
		this.aCallParameter = aCallParameter;
	}

	/**
	 * Get a parameter by its name
	 * 
	 * @param sCallParameterName
	 *            name of the parameter to look for
	 * @return the parameter researched
	 */
	public CallParameter getCallParameterByName(final String sCallParameterName) {
		return aCallMethod.getCallParameterByName(sCallParameterName);
	}

	/**
	 * Prints the object
	 */
	@Override
	public final String toString() {
		final int iStringBufferSize = 1000;
		final String sVariableSeparator = "\n";
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		aStringBuffer.append("/BeginInputMethod/");
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("CallMethod:{").append(this.aCallMethod).append(
				"}");
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("CallParameter:{").append(this.aCallParameter)
				.append("}");
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("/EndInputMethod/");

		return aStringBuffer.toString();
	}

}
