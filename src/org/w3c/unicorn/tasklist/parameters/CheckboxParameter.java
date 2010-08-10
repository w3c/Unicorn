// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist.parameters;

import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.unicorn.exceptions.ParameterException;

/**
 * CheckboxParameter<br />
 * Created: May 30, 2006 11:23:34 AM<br />
 */
public class CheckboxParameter extends Parameter {

	private boolean bCheckedByDefault;

	private Map<String, Value> mapOfValue = null;

	/**
	 * Default constructor for a CheckboxParameter (see the Parameter default
	 * constructor).
	 */
	protected CheckboxParameter() {
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
		mapOfValue.put((this.bCheckedByDefault) ? "checked" : "unchecked",
				(this.bCheckedByDefault) ? this.mapOfValue.get("checked")
						: this.mapOfValue.get("unchecked"));
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
		this.bCheckedByDefault = (sDefaultValues != null && "checked"
				.equals(sDefaultValues));
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
		if (mapOfValue.size() > 2 || mapOfValue.size() < 1) {
			Parameter.logger.error("Checkbox parameter " + this.getName()
					+ "must have only one or two values.");
			throw new ParameterException("Checkbox parameter " + this.getName()
					+ "must have only one or two values.");
		}
		this.mapOfValue = mapOfValue;
	}

	/**
	 * Gets the checked Value in the mapOfValue.
	 * 
	 * @return Returns the checked Value.
	 */
	public Value getChecked() {
		return this.mapOfValue.get("checked");
	}

	/**
	 * Sets the given Value to "checked" or adds it with this key.
	 * 
	 * @param aValueChecked
	 *            The Value to set as checked.
	 */
	public void setChecked(final Value aValueChecked) {
		this.mapOfValue.put("checked", aValueChecked);
	}

	/**
	 * Gets the unchecked Value in the mapOfValue.
	 * 
	 * @return Returns the unchecked.
	 */
	public Value getUnchecked() {
		return this.mapOfValue.get("unchecked");
	}

	/**
	 * Sets the given Value to "unchecked" or adds it with this key.
	 * 
	 * @param aValueUnchecked
	 *            The Value to set as unchecked.
	 */
	public void setUnchecked(final Value aValueUnchecked) {
		this.mapOfValue.put("unchecked", aValueUnchecked);
	}

	/**
	 * Returns the type of the parameter.
	 * 
	 * @return The type CHECKBOX.
	 */
	@Override
	public ParameterType getType() {
		return ParameterType.CHECKBOX;
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
		if (!(aParameter instanceof CheckboxParameter)) {
			Parameter.logger.warn("Type of parameter " + this.getName()
					+ " and " + aParameter.getName() + " not matching.");
			return false;
		}
		if (!super.merge(aParameter)) {
			return false;
		}
		final CheckboxParameter aCheckboxParameter = (CheckboxParameter) aParameter;
		for (final Value aValue : aCheckboxParameter.getMapOfValue().values()) {
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
