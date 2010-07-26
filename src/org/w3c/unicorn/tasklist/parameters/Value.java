// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist.parameters;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.w3c.unicorn.util.LocalizedString;

/**
 * Value<br />
 * Created: May 30, 2006 11:34:57 AM<br />
 * A value, as seen in the tasklist: a name and a list of mappings.
 * 
 * @author Jean-Guilhem ROUEL
 */
public class Value {

	/**
	 * Name of the value
	 */
	private String sName;

	/**
	 * Internationalized long name of the task
	 */
	private LocalizedString longNames;

	/**
	 * List of mappings for this value
	 */
	private Map<String, List<Mapping>> mapOfListOfMapping;

	/**
	 * Creates a new Value.
	 */
	public Value() {
		this.sName = "";
		this.longNames = new LocalizedString();
		this.mapOfListOfMapping = new LinkedHashMap<String, List<Mapping>>();
	}

	/**
	 * Creates a new Value.
	 * 
	 * @param mapOfListOfMapping
	 *            List of mappings.
	 * @param sName
	 *            Name of this value.
	 */
	public Value(final Map<String, List<Mapping>> mapOfListOfMapping,
			final String sName) {
		this.longNames = new LocalizedString();
		this.mapOfListOfMapping = mapOfListOfMapping;
		this.sName = sName;
	}

	/**
	 * Creates a new Value.
	 * 
	 * @param longname
	 *            internationalized long name of this value
	 * @param mapOfListOfMapping
	 *            list of mappings
	 * @param sName
	 *            name of this value
	 */
	public Value(final LocalizedString aInternationalizedMessageLongName,
			final Map<String, List<Mapping>> mapOfListOfMapping,
			final String sName) {
		super();
		this.longNames = aInternationalizedMessageLongName;
		this.mapOfListOfMapping = mapOfListOfMapping;
		this.sName = sName;
	}

	public boolean hasLongName(final String sLocale) {
		return this.longNames.hasLocale(sLocale);
	}

	/**
	 * Returns the internationalized long name of this value
	 * 
	 * @return Returns the longname.
	 */
	public LocalizedString getLongName() {
		return this.longNames;
	}

	/**
	 * Sets the internationalized long name of this value
	 * 
	 * @param longname
	 *            The longname to set.
	 */
	public void setLongName(final LocalizedString longNames) {
		this.longNames = longNames;
	}

	/**
	 * Adds a list of mapping to the mapOfListOfMapping.
	 * 
	 * @param sKey
	 *            The key for the list.
	 * @param listOfMapping
	 *            The list to add.
	 */
	public void addListOfMapping(final String sKey,
			final List<Mapping> listOfMapping) {
		this.mapOfListOfMapping.put(sKey, listOfMapping);
	}

	/**
	 * Checks if the given mapping exists.
	 * 
	 * @param sMapping
	 * @return True if the map contains sMapping, else false.
	 */
	public boolean hasMapping(final String sMapping) {
		return null != this.mapOfListOfMapping.get(sMapping);
	}

	/**
	 * Finds a list of mapping given its name.
	 * 
	 * @param sName
	 *            The name of the mapping.
	 * @return The list of mapping if the name matches an entry.
	 */
	public List<Mapping> getListOfMapping(final String sName) {
		return this.mapOfListOfMapping.get(sName);
	}

	/**
	 * Returns all the mappings for this value
	 * 
	 * @return Returns the mappings.
	 */
	public Map<String, List<Mapping>> getMapOfMapping() {
		return this.mapOfListOfMapping;
	}

	/**
	 * Sets the mappings for this value
	 * 
	 * @param mappings
	 *            The mappings to set.
	 */
	public void setMapOfMapping(final Map<String, List<Mapping>> mappings) {
		this.mapOfListOfMapping = mappings;
	}

	/**
	 * Sets the name of this value
	 * 
	 * @param sName
	 *            The name to set.
	 */
	public void setName(final String sName) {
		this.sName = sName;
	}

	/**
	 * Gets the name of this value
	 * 
	 * @return the name of this value
	 */
	public String getName() {
		return this.sName;
	}

	/**
	 * Returns a localized long name of this value
	 * 
	 * @param sLang
	 *            the locale
	 * @return the localized long name
	 */
	public String getLongName(final String sLang) {
		final String sName = this.longNames.getLocalization(sLang);
		if (sName != null) {
			return sName;
		}
		return this.sName;
	}

	/**
	 * Adds a localized long name to this value
	 * 
	 * @param sLang
	 *            the locale
	 * @param sLongName
	 *            the localized name
	 */
	public void addLongName(final String sLang, final String sLongName) {
		this.longNames.addLocalization(sLang, sLongName);
	}

	/**
	 * Adds or merges a mapping to this value, depending on the existence of a
	 * similar mapping.
	 * 
	 * @param m
	 *            the mapping to add or merge
	 */
	/*
	 * public void addMapping (Mapping m) { boolean done = false; for (Mapping
	 * mapping : this.mappings.get(m.getObserver().getDescription().getId())) { //
	 * A mapping with these observer, param and value already exists // We only
	 * need to add the input method
	 * if(mapping.getObserver().getDescription().getId().equals(
	 * m.getObserver().getDescription().getId()) &&
	 * mapping.getParam().equals(m.getParam()) &&
	 * mapping.getValue().equals(m.getValue())) {
	 * 
	 * List<EnumInputMethod> inputMethods = mapping.getInputMethodsList(); for
	 * (EnumInputMethod im : m.getInputMethodsList()) {
	 * if(inputMethods.contains(im)) { System.err.println("The value " + name + "
	 * already" + " contains this mapping"); } else {
	 * mapping.addInputMethod(im); } } // ok, the mapping has been merged done =
	 * true; } } // no similar mapping exists, so we simply add it if(!done) {
	 * mappings.add(m); } }
	 */

	@Override
	public String toString() {
		final int iStringBufferSize = 1000;
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);

		aStringBuffer.append(this.sName);
		aStringBuffer.append("=>");
		aStringBuffer.append(this.mapOfListOfMapping);

		return aStringBuffer.toString();
	}

}
