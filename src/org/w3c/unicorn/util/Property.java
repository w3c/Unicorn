package org.w3c.unicorn.util;

import java.util.Properties;

import org.w3c.unicorn.Framework;

public class Property {
	
	public static String get(String key) {
		return Framework.getUnicornPropertiesFiles().get("unicorn.properties").getProperty(key);
	}
	
	public static String get(String key1, String key2) {
		return Framework.getUnicornPropertiesFiles().get("unicorn.properties").getProperty(key1) 
			 + Framework.getUnicornPropertiesFiles().get("unicorn.properties").getProperty(key2);
	}

	public static Properties getProps(String fileName) {
		return Framework.getUnicornPropertiesFiles().get(fileName);
	}
}
