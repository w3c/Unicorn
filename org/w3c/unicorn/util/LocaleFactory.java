// $Id: LocaleFactory.java,v 1.1.1.1 2006-08-31 09:09:28 dleroy Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;

/**
 * LocaleFactory<br />
 * Created: May 30, 2006 12:08:37 PM<br />
 * @author Jean-Guilhem ROUEL
 */
public class LocaleFactory {

	private static final LinkedHashMap<String, Locale> mapOfLocale = new LinkedHashMap<String, Locale>();

	public static Locale getLocale (final String sLocale) {
		return LocaleFactory.mapOfLocale.get(sLocale);
	}

	public static Collection<Locale> values () {
		return LocaleFactory.mapOfLocale.values();
	}

	static {
		final Locale[] tLocale = Locale.getAvailableLocales();
		for (final Locale aLocale : tLocale) {
			final String sLanguage = aLocale.getLanguage();
			final String sCountry = aLocale.getCountry();
			LocaleFactory.mapOfLocale.put(sLanguage + "-" + sCountry, aLocale);
		}
	}

}
