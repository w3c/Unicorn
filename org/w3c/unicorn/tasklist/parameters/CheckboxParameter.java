// $Id: CheckboxParameter.java,v 1.1.1.1 2006-08-31 09:09:27 dleroy Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
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

	protected CheckboxParameter () {
		super();
		CheckboxParameter.logger.trace("Constructor()");
	}

	public void addValue (final Value aValue) {
		this.mapOfValue.put(aValue.getName(), aValue);
	}

	public Value getValue (final String sName) {
		return this.mapOfValue.get(sName);
	}

	/* (non-Javadoc)
	 * @see org.w3c.unicorn.tasklist.parameters.Parameter#getDefault()
	 */
	public Map<String, Value> getMapOfDefaultValue () {
		final Map<String, Value> mapOfValue = new LinkedHashMap<String, Value>();
		mapOfValue.put(
				(this.bCheckedByDefault) ? "checked" : "unchecked",
				(this.bCheckedByDefault) ? this.mapOfValue.get("checked") : this.mapOfValue.get("unchecked"));
		return mapOfValue;
	}

	public void setDefaultValues (final String sDefaultValues) {
		this.bCheckedByDefault = (
				sDefaultValues != null &&
				"checked".equals(sDefaultValues));
	}

	/* (non-Javadoc)
	 * @see org.w3c.unicorn.tasklist.parameters.Parameter#getValues()
	 */
	public Map<String, Value> getMapOfValue () {
		return this.mapOfValue;
	}
	
	public void setMapOfValue (final Map<String, Value> mapOfValue) throws ParameterException {
		if (mapOfValue.size() > 2 || mapOfValue.size() < 1) {
			CheckboxParameter.logger.error(
					"Checkbox parameter " + this.getName() +
					"must have only one or two values.");
			throw new ParameterException(
					"Checkbox parameter " + this.getName() +
					"must have only one or two values.");
		}
		this.mapOfValue = mapOfValue;
	}
	/**
	 * @return Returns the checked.
	 */
	public Value getChecked () {
		return this.mapOfValue.get("checked");
	}
	
	/**
	 * @param aValueChecked The checked to set.
	 */
	public void setChecked (final Value aValueChecked) {
		this.mapOfValue.put("checked", aValueChecked);
	}
	
	/**
	 * @return Returns the unchecked.
	 */
	public Value getUnchecked () {
		return this.mapOfValue.get("unchecked");
	}
	
	/**
	 * @param aValueUnchecked The unchecked to set.
	 */
	public void setUnchecked (final Value aValueUnchecked) {
		this.mapOfValue.put("unchecked", aValueUnchecked);
	}

	public ParameterType getType () {
		return ParameterType.CHECKBOX;
	}

	public boolean merge (final Parameter aParameter) {
		CheckboxParameter.logger.trace("merge");
		// Types must match
		if (!(aParameter instanceof CheckboxParameter)) {
			CheckboxParameter.logger.warn("Type of parameter "+this.getName()+" and "+aParameter.getName()+" not matching.");
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
					aLocalValue.addLongName(sLocale, aValue.getLongName(sLocale));
					continue;
				}
			}
			for (final String sValue : aValue.getMapOfMapping().keySet()) {
				if (!aLocalValue.hasMapping(sValue)) {
					aLocalValue.addListOfMapping(sValue, aValue.getListOfMapping(sValue));
				}
			}
		}
		return true;
	}

}
