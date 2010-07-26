// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * LocalizedString<br />
 * Created: May 30, 2006 11:55:23 AM<br />
 * 
 * @author Jean-Guilhem ROUEL & Damien LEROY
 */
public class LocalizedString {

	private static final Log logger = LogFactory.getLog(LocalizedString.class);

	private Map<String, String> mapOfString = null;

	/**
	 * Default constructor for LocalizedString.
	 * 
	 */
	public LocalizedString() {
		logger.trace("Constructor");
		this.mapOfString = new LinkedHashMap<String, String>();
	}

	/**
	 * Constructs a LocalizedString with an initial string paired with a
	 * language.
	 * 
	 * @param s
	 *            The string to be localized.
	 * @param lang
	 *            The corresponding language.
	 */
	public LocalizedString(String s, String lang) {
		this();
		mapOfString.put(lang, s);
	}

	/**
	 * Looks for the existence of a specified sLocale string in the map.
	 * 
	 * @param sLocale
	 *            The string to look for.
	 * @return True if the sLocale string is in the map, else false.
	 */
	public boolean hasLocale(final String sLocale) {
		return null != this.mapOfString.get(sLocale);
	}

	/**
	 * Adds a message and its corresponding localization to the mapOfString
	 * attribute.
	 * 
	 * @param sLocale
	 *            The localization.
	 * @param sMessage
	 *            The message to be written.
	 * @return The previous value associated with specified key, or null if
	 *         there was no mapping for key.
	 */
	public String addLocalization(final String sLocale, final String sMessage) {
		logger.trace("addLocalization\n" +
				     "Locale : " + sLocale + ".\n" +
				     "Message : " + sMessage + ".");
		return this.mapOfString.put(sLocale, sMessage);
	}

	/**
	 * Finds and returns the message corresponding to the specified localization
	 * in the mapOfString.
	 * 
	 * @param sLocale
	 *            The localization wanted.
	 * @return The message corresponding to the localization or if there's none,
	 *         the one corresponding to the default language.
	 */
	public String getLocalization(final String sLocale) {
		final String sMessage = this.mapOfString.get(sLocale);
		if (null != sMessage) {
			return sMessage;
		}
		return this.mapOfString.get(Property.get("DEFAULT_LANGUAGE"));
	}

	/**
	 * Returns the keys available in the mapOfString.
	 * 
	 * @return A set with all the keys.
	 */
	public Set<String> getSetOfLocale() {
		return this.mapOfString.keySet();
	}

	/**
	 * Returns the message in in DEFAULT_LANGUAGE (en) or in the first language
	 * in the list.
	 */
	@Override
	public String toString() {
		String res = this.mapOfString.get(Property.get("DEFAULT_LANGUAGE"));
		if (res == null) {
			for (String s : this.mapOfString.values()) {
				return s;
			}
		}
		if (res == null) {
			return "";
		}
		return res;
	}

}
