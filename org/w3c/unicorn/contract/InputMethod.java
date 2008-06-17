// $Id: InputMethod.java,v 1.4 2008-06-17 13:41:12 fbatard Exp $
// Author: Damien LEROY
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.contract;

import java.util.ArrayList;
import java.util.List;

import javax.activation.MimeType;

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

	/**
	 * List of the mime-types of the calls
	 */
	private List<MimeType> listOfMimeType = null;

	/**
	 * Set the list of mime-types
	 */
	public InputMethod() {
		this.listOfMimeType = new ArrayList<MimeType>();
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
	 * Add a mime-type
	 * 
	 * @param aMimeType
	 *            mime-type to add
	 */
	public void addMimeType(final MimeType aMimeType) {
		this.listOfMimeType.add(aMimeType);
	}

	/**
	 * Tells if the mime-types can be handled
	 * 
	 * @param aMimeType
	 *            mime-type to check
	 * @return true if the mime type is handled
	 */
	public boolean canHandleMimeType(final MimeType aMimeType) {
		for (final MimeType handler : this.listOfMimeType) {
			if (handler.match(aMimeType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Set the list of the MIME-Types
	 * 
	 * @param listOfMimeType
	 *            list of the mime-type
	 */
	public void setListOfMimeType(List<MimeType> listOfMimeType) {
		this.listOfMimeType = listOfMimeType;
	}

	/**
	 * Prints the object
	 */
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
		aStringBuffer.append("MimeType:{").append(this.listOfMimeType).append(
				"}");
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("/EndInputMethod/");

		return aStringBuffer.toString();
	}

}
