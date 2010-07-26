// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist.parameters;

import org.w3c.unicorn.contract.Observer;

/**
 * Mapping<br />
 * Created: May 30, 2006 12:25:00 PM<br />
 * This class represents a mapping between a value of parameter in a task and a
 * value of a parameter in an observer.
 * 
 * @author Jean-Guilhem ROUEL
 */
public class Mapping {

	/**
	 * Mapped observer
	 */
	private Observer aObserver;

	/**
	 * Mapped parameter
	 */
	private String sParam;

	/**
	 * Mapped value
	 */
	private String sValue;

	/**
	 * List of mapped input methods
	 */
	// private List<EnumInputMethod> listOfEnumInputMethod;
	public Mapping() {
	}

	/**
	 * Creates a new Mapping.
	 * 
	 * @param aObserver
	 * @param sParam
	 * @param sValue
	 */
	public Mapping(final Observer aObserver, final String sParam,
			final String sValue/*
								 * , final List<EnumInputMethod>
								 * listOfEnumInputMethod
								 */) {
		super();

		this.aObserver = aObserver;
		this.sParam = sParam;
		this.sValue = sValue;
		// this.listOfEnumInputMethod = listOfEnumInputMethod;
	}

	/**
	 * Returns the observer mapped
	 * 
	 * @return Returns the observer.
	 */
	public Observer getObserver() {
		return this.aObserver;
	}

	/**
	 * Sets the mapped observer
	 * 
	 * @param aObserver
	 *            The observer to set.
	 */
	public void setObserver(final Observer aObserver) {
		this.aObserver = aObserver;
	}

	/**
	 * Gets the name of the mapped parameter
	 * 
	 * @return Returns the param.
	 */
	public String getParam() {
		return this.sParam;
	}

	/**
	 * Sets the name of the mapped parameter
	 * 
	 * @param sParam
	 *            The param to set.
	 */
	public void setParam(final String sParam) {
		this.sParam = sParam;
	}

	/**
	 * Gets the name of the mapped value
	 * 
	 * @return Returns the value.
	 */
	public String getValue() {
		return this.sValue;
	}

	/**
	 * Sets the name of the mapped value
	 * 
	 * @param sValue
	 *            The value to set.
	 */
	public void setValue(final String sValue) {
		this.sValue = sValue;
	}

	/**
	 * Returns the list of input methods to whose the value is mapped
	 * 
	 * @return Returns the inputMethodsList.
	 */
	/*
	 * private List<EnumInputMethod> getInputMethodsList() { return
	 * this.listOfEnumInputMethod; }
	 */

	/**
	 * Sets the input methods
	 * 
	 * @param listOfEnumInputMethod
	 *            The inputMethodsList to set.
	 */
	/*
	 * private void setInputMethodsList (final List<EnumInputMethod>
	 * listOfEnumInputMethod) { this.listOfEnumInputMethod =
	 * listOfEnumInputMethod; }
	 */

	/**
	 * Adds an input method to this mapping
	 * 
	 * @param aEnumInputMethod
	 *            the input method to add
	 * @return
	 */
	/*
	 * private boolean addInputMethod (final EnumInputMethod aEnumInputMethod) {
	 * if (this.listOfEnumInputMethod.contains(aEnumInputMethod)) { return
	 * false; } return this.listOfEnumInputMethod.add(aEnumInputMethod); }
	 */

	@Override
	public String toString() {
		final int iStringBufferSize = 1000;
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);

		aStringBuffer.append(this.aObserver.getID());
		aStringBuffer.append('.');
		aStringBuffer.append('.');
		aStringBuffer.append(this.sParam);
		aStringBuffer.append('=');
		aStringBuffer.append(this.sValue);

		return aStringBuffer.toString();
	}

}
