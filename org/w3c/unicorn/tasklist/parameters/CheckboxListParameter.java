// $Id: CheckboxListParameter.java,v 1.1.1.1 2006-08-31 09:09:27 dleroy Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist.parameters;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * CheckboxListParameter<br />
 * Created: Jun 8, 2006 4:13:46 PM<br />
 * @author Jean-Guilhem ROUEL
 */
public class CheckboxListParameter extends Parameter {
	
	private Map<String, Value> mapOfValue;
	private Map<String, Value> mapOfDefaultValue;

	protected CheckboxListParameter () {
		super();
		CheckboxListParameter.logger.trace("Constructor()");
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
	@Override
	public Map<String, Value> getMapOfDefaultValue() {
		return this.mapOfDefaultValue;
	}

	public void setDefaultValues (final String sDefaultValues) {
		this.mapOfDefaultValue = new LinkedHashMap<String, Value>();
		for (String sDefault : sDefaultValues.split(",")) {
			sDefault = sDefault.trim();
			final Value aValue = this.mapOfValue.get(sDefault);
			if (aValue != null) {
				this.mapOfDefaultValue.put(sDefault, aValue);
			}
			else {
				CheckboxListParameter.logger.error(
						"The default value " + sDefault +
						" is not a valid value.");
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.w3c.unicorn.tasklist.parameters.Parameter#getValues()
	 */
	@Override
	public Map<String, Value> getMapOfValue () {
		return this.mapOfValue;
	}

	public void setMapOfValue (final Map<String, Value> mapOfValue) {
		this.mapOfValue = mapOfValue;
	}

	public ParameterType getType () {
		return ParameterType.CHECKBOXLIST;
	}

	public boolean merge (final Parameter aParameter) {
		CheckboxListParameter.logger.trace("merge");
		// Types must match
		if (!(aParameter instanceof CheckboxListParameter)) {
			CheckboxListParameter.logger.warn("Type of parameter "+this.getName()+" and "+aParameter.getName()+" not matching.");
			return false;
		}
		if (!super.merge(aParameter)) {
			return false;
		}
		final CheckboxListParameter aCheckboxListParameter = (CheckboxListParameter) aParameter;
		for (final Value aValue : aCheckboxListParameter.getMapOfValue().values()) {
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
