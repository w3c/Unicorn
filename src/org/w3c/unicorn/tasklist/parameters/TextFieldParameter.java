// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist.parameters;

import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.unicorn.exceptions.ParameterException;

/**
 * TextFieldParameter<br />
 * Created: Jun 8, 2006 4:30:49 PM<br />
 * 
 * @author Jean-Guilhem ROUEL
 */
public class TextFieldParameter extends Parameter {

	private Value aValueDefault;

	/**
	 * Default constructor for a TextFieldParameter (see the Parameter default
	 * constructor).
	 */
	protected TextFieldParameter() {
		super();
		Parameter.logger.trace("Constructor()");
	}

	/**
	 * Adds a Value object to the mapOfValue.
	 * 
	 * @param aValue
	 *            The value to add.
	 */
	@Override
	public void addValue(final Value aValue) {
		this.aValueDefault = aValue;
	}

	/**
	 * Finds a Value object in the map given its name.
	 * 
	 * @param sName
	 *            The name of the Value.
	 * @return The Value object if the String corresponds to a key.
	 */
	@Override
	public Value getValue(final String sName) {
		if (this.aValueDefault.getName().equals(sName)) {
			return this.aValueDefault;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.unicorn.tasklist.parameters.Parameter#getDefault()
	 */
	@Override
	public Map<String, Value> getMapOfDefaultValue() {
		final Map<String, Value> mapOfValue = new LinkedHashMap<String, Value>();
		mapOfValue.put(this.aValueDefault.getName(), this.aValueDefault);
		return mapOfValue;
	}

	/**
	 * Sets the default Value in the mapOfDefaultValue.
	 * 
	 * @param sDefaultValues
	 *            The new default value.
	 */
	@Override
	public void setDefaultValues(final String sDefaultValues) {
		this.aValueDefault.setName(sDefaultValues);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.unicorn.tasklist.parameters.Parameter#getValues()
	 */
	@Override
	public Map<String, Value> getMapOfValue() {
		// no value because we allow any text
		return null;
	}

	/**
	 * Defines or replaces the mapOfValue.
	 * 
	 * @param mapOfValue
	 *            The new map of values.
	 */
	@Override
	public void setMapOfValue(final Map<String, Value> mapOfValue)
			throws ParameterException {
		if (mapOfValue.size() != 1) {
			Parameter.logger
					.error("TextField parameter should have exactly one value.");
			throw new ParameterException(
					"TextField parameter should have exactly one value.");
		}
		this.aValueDefault = mapOfValue.values().iterator().next();
	}

	/**
	 * Returns the type of the parameter.
	 * 
	 * @return The type TEXTFIELD.
	 */
	@Override
	public ParameterType getType() {
		return ParameterType.TEXTFIELD;
	}

	/**
	 * Merges a Parameter with this one if the type complies.
	 * 
	 * @param aParameter
	 *            The parameter to merge with the current one.
	 * @return True if they merged correctly, else false.
	 */
	@Override
	public boolean merge(final Parameter aParameter) {
		Parameter.logger.trace("merge");
		// Types must match
		if (!(aParameter instanceof TextFieldParameter)) {
			Parameter.logger.warn("Type of parameter " + this.getName()
					+ " and " + aParameter.getName() + " not matching.");
			return false;
		}
		if (!super.merge(aParameter)) {
			return false;
		}
		final TextFieldParameter aTextFieldParameter = (TextFieldParameter) aParameter;
		this.aValueDefault = aTextFieldParameter.aValueDefault;
		return true;
	}

}
