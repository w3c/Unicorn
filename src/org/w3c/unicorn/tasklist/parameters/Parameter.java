// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist.parameters;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3.unicorn.tasklist.TUi;
import org.w3c.unicorn.exceptions.ParameterException;
import org.w3c.unicorn.util.LocalizedString;

/**
 * Parameter<br />
 * Created: May 29, 2006 5:57:26 PM<br />
 * This class represents a parameter used in the tasklist
 * 
 * @author Jean-Guilhem ROUEL
 */
public abstract class Parameter {

	protected static final Log logger = LogFactory
			.getLog("org.w3c.unicorn.tasklist");

	/**
	 * Name of the parameter
	 */
	private String sName;

	/**
	 * Long name of the parameter
	 */
	private LocalizedString longnames;

	/**
	 * Level of the interface in which the parameter appears
	 */
	private TUi.Enum aTUiLevel;

	/**
	 * Returns the default values of this parameter.
	 * 
	 * @return The default values of this parameter.
	 */
	public abstract Map<String, Value> getMapOfDefaultValue();

	/**
	 * Returns the possible values of this parameter.
	 * 
	 * @return The possible values of this parameter.
	 */
	public abstract Map<String, Value> getMapOfValue();

	/**
	 * Creates a new Parameter.
	 */
	public Parameter() {
		this("", new LocalizedString(), TUi.ADVANCED);
	}

	/**
	 * Creates a new Parameter.
	 * 
	 * @param aLocalizedString
	 * @param sName
	 * @param aTUi
	 */
	public Parameter(final String sName,
			final LocalizedString aLocalizedString, final TUi.Enum aTUi) {
		super();
		this.longnames = aLocalizedString;
		this.sName = sName;
		this.aTUiLevel = aTUi;
	}

	/**
	 * Sets the name of this parameter
	 * 
	 * @param sName
	 *            The name to set.
	 */
	public void setName(final String sName) {
		this.sName = sName;
	}

	/**
	 * Returns the name of this parameter
	 * 
	 * @return the name of this parameter
	 */
	public String getName() {
		return this.sName;
	}

	/**
	 * Returns the internationalized long name of this parameter
	 * 
	 * @return Returns the longname.
	 */
	public LocalizedString getLongNames() {
		return this.longnames;
	}

	/**
	 * Sets the internationalized long name of this parameter
	 * 
	 * @param longname
	 *            The longname to set.
	 */
	public void setLongNames(final LocalizedString aLocalizedString) {
		this.longnames = aLocalizedString;
	}

	/**
	 * Returns a localized long name of this parameter
	 * 
	 * @param sLocale
	 *            locale of the long name
	 * @return the localized long name
	 */
	public String getLongName(final String sLocale) {
		return this.longnames.getLocalization(sLocale);
	}

	/**
	 * Adds a localization to this parameter's long name
	 * 
	 * @param sLocale
	 *            locale of the long name
	 * @param sLongName
	 *            the localized long name
	 */
	public void addLongName(final String sLocale, final String sLongName) {
		this.longnames.addLocalization(sLocale, sLongName);
	}

	/**
	 * Returns the interface level
	 * 
	 * @return Returns the uiLevel.
	 */
	public TUi.Enum getUiLevel() {
		return this.aTUiLevel;
	}

	/**
	 * Sets the interface level
	 * 
	 * @param aTUiLevel
	 *            The uiLevel to set.
	 */
	public void setUiLevel(final TUi.Enum aTUiLevel) {
		this.aTUiLevel = aTUiLevel;
	}

	public abstract void addValue(final Value aValue);

	public abstract ParameterType getType();

	public abstract Value getValue(final String sName);

	public abstract void setDefaultValues(final String sDefaultValues);

	public abstract void setMapOfValue(final Map<String, Value> mapOfValue)
			throws ParameterException;

	/**
	 * Merges two parameters.
	 * 
	 * @param aParameter
	 *            TODO End coding this function. TODO Check this function.
	 */
	public boolean merge(final Parameter aParameter) {
		Parameter.logger.trace("merge");
		// UI must match
		if (!this.getUiLevel().equals(aParameter.getUiLevel())) {
			Parameter.logger.warn("UI of parameter " + this.sName + " and "
					+ aParameter.sName + " not matching.");
			return false;
		}
		// merge long name
		for (final String sLocale : aParameter.getLongNames().getSetOfLocale()) {
			if (this.getLongNames().hasLocale(sLocale)) {
				this.addLongName(sLocale, aParameter.getLongName(sLocale));
			}
		}
		return true;
	}

	@Override
	public String toString() {
		final int iStringBufferSize = 1000;
		final String sVariableSeparator = "\n";
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);

		aStringBuffer.append("name:").append(sName);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("ui:").append(aTUiLevel);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("default:[").append(this.getMapOfDefaultValue())
				.append("]");
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("values:").append(getMapOfValue());

		return aStringBuffer.toString();
	}

}
