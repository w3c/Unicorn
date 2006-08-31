// $Id: TextFieldParameter.java,v 1.1.1.1 2006-08-31 09:09:27 dleroy Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist.parameters;

import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.unicorn.exceptions.ParameterException;

/**
 * TextFieldParameter<br />
 * Created: Jun 8, 2006 4:30:49 PM<br />
 * @author Jean-Guilhem ROUEL
 */
public class TextFieldParameter extends Parameter {

	private Value aValueDefault;

	protected TextFieldParameter () {
		super();
		TextFieldParameter.logger.trace("Constructor()");
	}

	public void addValue (final Value aValue) {
		this.aValueDefault = aValue;
	}

	public Value getValue (final String sName) {
		if (this.aValueDefault.getName().equals(sName)) {
			return this.aValueDefault;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.w3c.unicorn.tasklist.parameters.Parameter#getDefault()
	 */
	@Override
	public Map<String, Value> getMapOfDefaultValue () {
		final Map<String, Value> mapOfValue = new LinkedHashMap<String, Value>();
		mapOfValue.put(this.aValueDefault.getName(), this.aValueDefault);
		return mapOfValue;
	}

	public void setDefaultValues (final String sDefaultValues) {
		this.aValueDefault.setName(sDefaultValues);
	}

	/* (non-Javadoc)
	 * @see org.w3c.unicorn.tasklist.parameters.Parameter#getValues()
	 */
	@Override
	public Map<String, Value> getMapOfValue () {
		// no value because we allow any text
		return null;
	}

	public void setMapOfValue (final Map<String, Value> mapOfValue) throws ParameterException {
		if (mapOfValue.size() != 1) {
			TextFieldParameter.logger.error("TextField parameter should have exactly one value.");
			throw new ParameterException("TextField parameter should have exactly one value.");
		}
		this.aValueDefault = mapOfValue.values().iterator().next();
	}

	public ParameterType getType () {
		return ParameterType.TEXTFIELD;
	}

	public boolean merge (final Parameter aParameter) {
		TextFieldParameter.logger.trace("merge");
		// Types must match
		if (!(aParameter instanceof TextFieldParameter)) {
			TextFieldParameter.logger.warn("Type of parameter "+this.getName()+" and "+aParameter.getName()+" not matching.");
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
