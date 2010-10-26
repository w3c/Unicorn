// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.util;

import java.util.Properties;

import org.w3c.unicorn.Framework;

public class Property {
	
	public static String get(String key) {
		try {
			return Framework.getUnicornPropertiesFiles().get("unicorn.properties").getProperty(key);
		} catch (java.lang.NullPointerException e) {
			return null;
		}
	}
	
	public static String get(String key1, String key2) {
		try {
			return Framework.getUnicornPropertiesFiles().get("unicorn.properties").getProperty(key1) 
				 + Framework.getUnicornPropertiesFiles().get("unicorn.properties").getProperty(key2);
		} catch (java.lang.NullPointerException e) {
			return null;
		}
	}

	public static Properties getProps(String fileName) {
		return Framework.getUnicornPropertiesFiles().get(fileName);
	}
}
