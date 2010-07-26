// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist.parameters;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * CheckboxListParameter<br />
 * Created: Jun 8, 2006 4:13:46 PM<br />
 * 
 * @author Jean-Guilhem ROUEL
 */
public class CheckboxListParameter extends Parameter {

	private Map<String, Value> mapOfValue;

	private Map<String, Value> mapOfDefaultValue;

	/**
	 * Default constructor for a CheckboxListParameter (see Parameter default
	 * constructor).
	 */
	protected CheckboxListParameter() {
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
		return this.mapOfDefaultValue;
	}

	/**
	 * Sets the default Value in the mapOfDefaultValue.
	 * 
	 * @param sDefaultValues
	 *            The new default value.
	 */
	@Override
	public void setDefaultValues(final String sDefaultValues) {
		this.mapOfDefaultValue = new LinkedHashMap<String, Value>();
		for (String sDefault : sDefaultValues.split(",")) {
			sDefault = sDefault.trim();
			final Value aValue = this.mapOfValue.get(sDefault);
			if (aValue != null) {
				this.mapOfDefaultValue.put(sDefault, aValue);
			} else if (!sDefaultValues.equals("")) {
				Parameter.logger.error("The default value " + sDefault
						+ " is not a valid value.");
			}
		}
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
	public void setMapOfValue(final Map<String, Value> mapOfValue) {
		this.mapOfValue = mapOfValue;
	}

	/**
	 * Returns the type of the parameter.
	 * 
	 * @return The type CHECKBOXLIST.
	 */
	@Override
	public ParameterType getType() {
		return ParameterType.CHECKBOXLIST;
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
		if (!(aParameter instanceof CheckboxListParameter)) {
			Parameter.logger.warn("Type of parameter " + this.getName()
					+ " and " + aParameter.getName() + " not matching.");
			return false;
		}
		if (!super.merge(aParameter)) {
			return false;
		}
		final CheckboxListParameter aCheckboxListParameter = (CheckboxListParameter) aParameter;
		for (final Value aValue : aCheckboxListParameter.getMapOfValue()
				.values()) {
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
