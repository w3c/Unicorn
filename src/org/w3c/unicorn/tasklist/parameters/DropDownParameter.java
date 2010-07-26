// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist.parameters;

import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.unicorn.exceptions.ParameterException;

/**
 * DropDownParameter<br />
 * Created: Jun 8, 2006 1:48:31 PM<br />
 * 
 * @author Jean-Guilhem ROUEL
 */
public class DropDownParameter extends Parameter {

	private Map<String, Value> mapOfValue;

	private Value aValueDefault;

	/**
	 * Default constructor for a DropDownParameter (see the Parameter default
	 * constructor).
	 */
	protected DropDownParameter() {
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
		this.mapOfValue.put(aValue.getName(), aValue);
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
		return this.mapOfValue.get(sName);
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
		this.aValueDefault = this.mapOfValue.get(sDefaultValues);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.unicorn.tasklist.parameters.Parameter#getValues()
	 */
	@Override
	public Map<String, Value> getMapOfValue() {
		return this.mapOfValue;
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
		if (mapOfValue.size() < 1) {
			Parameter.logger
					.error("Dropdown parameter must have at least one value.");
			throw new ParameterException(
					"Dropdown parameter must have at least one value.");
		}
		this.mapOfValue = mapOfValue;
	}

	/**
	 * Returns the type of the parameter.
	 * 
	 * @return The type DROPDOWN.
	 */
	@Override
	public ParameterType getType() {
		return ParameterType.DROPDOWN;
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
		if (!(aParameter instanceof DropDownParameter)) {
			Parameter.logger.warn("Type of parameter " + this.getName()
					+ " and " + aParameter.getName() + " not matching.");
			return false;
		}
		if (!super.merge(aParameter)) {
			return false;
		}
		final DropDownParameter aDropDownParameter = (DropDownParameter) aParameter;
		for (final Value aValue : aDropDownParameter.getMapOfValue().values()) {
			final Value aLocalValue = this.getValue(aValue.getName());
			if (null == aLocalValue) {
				this.addValue(aValue);
				continue;
			}
			for (final String sLocale : aValue.getLongName().getSetOfLocale()) {
				if (!aLocalValue.hasLongName(sLocale)) {
					aLocalValue.addLongName(sLocale, aValue
							.getLongName(sLocale));
					continue;
				}
			}
			for (final String sValue : aValue.getMapOfMapping().keySet()) {
				if (!aLocalValue.hasMapping(sValue)) {
					aLocalValue.addListOfMapping(sValue, aValue
							.getListOfMapping(sValue));
				}
			}
		}
		return true;
	}

}
