// $Id: InputMethod.java,v 1.1.1.1 2006-08-31 09:09:20 dleroy Exp $
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

	private CallMethod aCallMethod = null;
	private CallParameter aCallParameter = null;
	private List<MimeType> listOfMimeType = null;

	public InputMethod () {
		this.listOfMimeType = new ArrayList<MimeType>();
	}

	public CallMethod getCallMethod () {
		return this.aCallMethod;
	}

	public void setCallMethod (final CallMethod aCallMethod) {
		this.aCallMethod = aCallMethod;
	}

	public CallParameter getCallParameter () {
		return this.aCallParameter;
	}

	public void setCallParameter (final CallParameter aCallParameter) {
		this.aCallParameter = aCallParameter;
	}

	public CallParameter getCallParameterByName (final String sCallParameterName) {
		return aCallMethod.getCallParameterByName(sCallParameterName);
	}

	public void addMimeType (final MimeType aMimeType) {
		this.listOfMimeType.add(aMimeType);
	}

	public boolean canHandleMimeType (final MimeType aMimeType) {
		for (final MimeType handler : this.listOfMimeType) {
			if (handler.match(aMimeType)) {
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public final String toString () {		
		final int iStringBufferSize = 1000;
		final String sVariableSeparator = "\n";
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);

		aStringBuffer.append("CallMethod:[").append(this.aCallMethod).append("]");
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("CallParameter:[").append(this.aCallParameter).append("]");

		return aStringBuffer.toString();
	}

}
