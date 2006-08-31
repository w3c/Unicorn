// $Id: CallParameter.java,v 1.1.1.1 2006-08-31 09:09:20 dleroy Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.contract;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * CallParameter<br />
 * Created: May 22, 2006 3:26:19 PM<br />
 */
public class CallParameter {

	private static final Log logger = LogFactory.getLog("org.w3c.unicorn.contract");

	// Name of this parameter     
	private String sName;    
	
	// Possible values for this parameter    
	private List<String> listOfPossibleValue;        
	
	// Indicates wheter the parameter can be manually set or not
	private String sFixed;
	
	// Is this parameter mandatory     
	private boolean bRequired;
	
	// Can this parameter be repeated
	private boolean bRepeating;
	
	/**
	 * @param sName
	 * @param listOfPossibleValue
	 * @param sFixed
	 */
	public CallParameter (
			final String sName,
			final List<String> listOfValue,
			final String sFixed,
			final boolean bRequired,
			final boolean bRepeating) {
		super();

		CallParameter.logger.trace("Constructor(String, List<String>, String, boolean, boolean)");
		if (CallParameter.logger.isDebugEnabled()) {
			CallParameter.logger.debug("Name : " + sName + ".");
			CallParameter.logger.debug("List of value : " + listOfValue + ".");
			CallParameter.logger.debug("Fixed : " + sFixed + ".");
			CallParameter.logger.debug("Required : " + bRequired + ".");
			CallParameter.logger.debug("Repeating : " + bRepeating + ".");
		}

		this.sName = sName;
		this.listOfPossibleValue = listOfValue;
		this.sFixed = sFixed;
		this.bRequired = bRequired;
		this.bRepeating = bRepeating;
	}	
	
	/**
	 * @param sName
	 */
	public CallParameter (final String sName) {
		this(sName, new ArrayList<String>(), null, false, false);
		CallParameter.logger.trace("Constructor(String)");
	}

	public CallParameter () {
		this("", new ArrayList<String>(), null, false, false);
		CallParameter.logger.trace("Constructor()");
	}

	public String getName() {
		return this.sName;
	}

	public void setName (final String sName) {
		this.sName = sName;
	}

	public List<String> getListOfPossibleValue () {
		return listOfPossibleValue;
	}

	public void setPossibleValues (final List<String> listOfValue) {
		this.listOfPossibleValue = listOfValue;
	}
	
	public void addValue (final String sValue) {
		if (this.listOfPossibleValue == null) {
			this.listOfPossibleValue = new ArrayList<String>();
		}
		this.listOfPossibleValue.add(sValue);
	}
	
	public Object getValue (final int iPosition) {
		return this.listOfPossibleValue.get(iPosition);
	}
	
	public boolean contains (final String sValue) {
		return this.listOfPossibleValue.contains(sValue) ||
			(this.listOfPossibleValue.size() == 1 && this.listOfPossibleValue.contains(""));
	}
	
	/**
	 * @return Returns the fixed.
	 */
	public boolean isFixed() {
		return this.sFixed != null;
	}
	
	public String getFixed() {
		return this.sFixed;
	}
	
	/**
	 * @param sFixed The fixed to set.
	 */
	public void setFixed (final String sFixed) {
		this.sFixed = sFixed;
	}
	
	/**
	 * @return Returns the repeating.
	 */
	public boolean isRepeating() {
		return this.bRepeating;
	}
	
	/**
	 * @param bRepeating The repeating to set.
	 */
	public void setRepeating(final boolean bRepeating) {
		this.bRepeating = bRepeating;
	}
	
	/**
	 * @return Returns the required.
	 */
	public boolean isRequired() {
		return this.bRequired;
	}
	
	/**
	 * @param bRequired The required to set.
	 */
	public void setRequired (final boolean bRequired) {
		this.bRequired = bRequired;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		final int iSize = 1000;
		final String sVariableSeparator = "  ";
		final StringBuffer aStringBuffer = new StringBuffer(iSize);
		
		aStringBuffer.append("name=").append(sName);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("possibleValues=").append(listOfPossibleValue);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("fixed=").append(sFixed);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("required=").append(bRequired);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("repeating=").append(bRepeating);
		
		return aStringBuffer.toString();
	}
	
}
