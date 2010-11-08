// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
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

	/**
	 * Object for complex logging purpose
	 */
	private static final Log logger = LogFactory
			.getLog(CallParameter.class);

	// Attributes of the parameter
	/**
	 * Name of the parameter
	 */
	private String sName = null;

	/**
	 * Style of the parameter
	 */
	private String sStyle = null;

	/**
	 * ID of the parameter
	 */
	private String sID = null;

	/**
	 * Type of the parameter
	 */
	private String sType = null;

	/**
	 * Default value for the parameter
	 */
	private String sDefaultValue;

	/**
	 * Defines whether or not the parameter is mandatory
	 */
	private boolean bRequired; // Is this parameter mandatory

	/**
	 * Defines whether or not the parameter can be repeated
	 */
	private boolean bRepeating; // Can this parameter be repeated

	/**
	 * Indicates if the parameter can be manually set or not
	 */
	private String sFixed = null; // Indicates if the parameter can be

	// manually set or not

	/**
	 * Defines the path of the parameter
	 */
	private String sPath = null;

	/**
	 * Possible values for this parameter
	 */
	private List<Option> listOfPossibleOptions = null;

	/**
	 * Constructor for a parameter
	 * 
	 * @param sName
	 *            Name of the parameter
	 * @param listOfPossibleOptions
	 *            all the possibles values
	 * @param sFixed
	 *            Indicates if the parameter can be manually set or not
	 */
	public CallParameter(final String sName, final List<Option> listOfOptions,
			final String sFixed, final boolean bRequired,
			final boolean bRepeating) {
		super();

		logger.trace("Constructor(String, List<String>, String, boolean, boolean)\n" +
					 "Name : " + sName + ".\n" +
					 "List of value : " + listOfOptions + ".\n" +
					 "Fixed : " + sFixed + ".\n" +
					 "Required : " + bRequired + ".\n" +
					 "Repeating : " + bRepeating + ".");

		this.sName = sName;
		this.listOfPossibleOptions = listOfOptions;
		this.sFixed = sFixed;
		this.bRequired = bRequired;
		this.bRepeating = bRepeating;
	}

	/**
	 * Various constructor with only the name and default value
	 * 
	 * @param sName
	 *            name of the parameter
	 */
	public CallParameter(final String sName) {
		this(sName, new ArrayList<Option>(), null, false, false);
		logger.trace("Constructor(String)");
	}

	/**
	 * Various constructor with empty name
	 */
	public CallParameter() {
		this("", new ArrayList<Option>(), null, false, false);
		logger.trace("Constructor()");
	}

	// ===================
	// ===== GETTERS =====
	// ===================
	public String getName() {
		return this.sName;
	}

	public String getStyle() {
		return this.sStyle;
	}

	public String getID() {
		return this.sID;
	}

	public String getType() {
		return this.sType;
	}

	public String getDefaultValue() {
		return this.sDefaultValue;
	}

	public boolean isRequired() {
		return this.bRequired;
	}

	public boolean isRepeating() {
		return this.bRepeating;
	}

	public boolean isFixed() {
		return this.sFixed != null;
	}

	public String getFixed() {
		return this.sFixed;
	}

	public String getPath() {
		return this.sPath;
	}

	public List<Option> getListOfPossibleOptions() {
		return listOfPossibleOptions;
	}

	// ==================
	// ===== SETTERS ====
	// ==================
	public void setName(final String sName) {
		this.sName = sName;
	}

	public void setStyle(String style) {
		this.sStyle = style;
	}

	public void setID(String sid) {
		this.sID = sid;
	}

	public void setType(String type) {
		this.sType = type;
	}

	public void setDefaultValue(String defaultValue) {
		this.sDefaultValue = defaultValue;
	}

	public void setRequired(final boolean bRequired) {
		this.bRequired = bRequired;
	}

	public void setRepeating(final boolean bRepeating) {
		this.bRepeating = bRepeating;
	}

	public void setFixed(final String sFixed) {
		this.sFixed = sFixed;
	}

	public void setPath(final String sPath) {
		this.sPath = sPath;
	}

	public void setPossibleOptions(final List<Option> listOfOptions) {
		this.listOfPossibleOptions = listOfOptions;
	}

	public void addOption(final Option option) {
		if (this.listOfPossibleOptions == null) {
			this.listOfPossibleOptions = new ArrayList<Option>();
		}
		this.listOfPossibleOptions.add(option);
	}

	public Option getOption(final int iPosition) {
		return this.listOfPossibleOptions.get(iPosition);
	}

	public boolean contains(final String sValue) {
		for (Option option : listOfPossibleOptions) {
			if (option.getValue().equals(sValue))
				return true;
		}
		return false;
	}

	/**
	 * Prints the object
	 */
	@Override
	public String toString() {
		final int iSize = 1000;
		final String sVariableSeparator = "\n";
		final StringBuffer aStringBuffer = new StringBuffer(iSize);

		aStringBuffer.append("[begin CallParameter]\n");
		aStringBuffer.append("name:=").append(sName);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("id:=").append(sID);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("possibleValues:=").append(listOfPossibleOptions);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("fixed:=").append(sFixed);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("required:=").append(bRequired);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("repeating:=").append(bRepeating);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("style:=").append(sStyle);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("path:=").append(sPath);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("style:=").append(sStyle);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("default:=").append(sDefaultValue);
		aStringBuffer.append("[end CallParameter]\n");

		return aStringBuffer.toString();
	}

}
