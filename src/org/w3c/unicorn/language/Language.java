package org.w3c.unicorn.language;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import org.apache.velocity.VelocityContext;
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.Framework;

public class Language {
	
	public static boolean isISOLanguageCode(String languageCode) {
		String[] isoCodes = Locale.getISOLanguages();
		for (String code : isoCodes) {
			if (code.equals(languageCode))
				return true;
		}
		return false;
	}
	
	public static void addToContext(VelocityContext velocityContext, Locale lang) throws Exception {
		File languageFile = new File(Property.get("PATH_TO_LANGUAGE_FILES") + lang.getLanguage() + ".properties");
		FileReader fr = new FileReader(languageFile);
		
		Properties langProps = new Properties();
		langProps.load(fr);
		
	    Set<Object> keys = langProps.keySet();
	    Iterator<Object> itr = keys.iterator();
	    String key;
	    while (itr.hasNext()) {
			key = itr.next().toString();
			velocityContext.put(key, langProps.get(key));
	    }
	}
	
	public static void addToContext(Properties langProps, VelocityContext context) throws Exception {
	    Set<Object> keys = langProps.keySet();
	    Iterator<Object> itr = keys.iterator();
	    String key;
	    while (itr.hasNext()) {
			key = itr.next().toString();
			context.put(key, langProps.get(key));
	    }
	    
	    for (Object keyss : langProps.keySet()) {
	    	context.put((String) keyss, langProps.get(keyss));
	    }
	}
	
	public static String negociate(Enumeration<?> locales) {
		while (locales.hasMoreElements()) {
			Locale loc = (Locale) locales.nextElement();
			if (Framework.getLanguageProperties().containsKey(loc.getLanguage())) {
				return loc.getLanguage();
			}
		}
		return Property.get("DEFAULT_LANGUAGE");
	}
	
	public static void addLanguageFile(File langFile) throws MalformedURLException, IOException {
		String fileName = langFile.getName();
		String locale = fileName.split("\\.")[0];
		if (!isISOLanguageCode(locale)) {
			return;
		}
		//Locale locale = new Locale(localeString);
		Properties props = new Properties();
		props.load(langFile.toURI().toURL().openStream());
		//languages.put(locale, props);
	}

	public static VelocityContext getContext(String langParameter) {
		if (langParameter != null && Framework.getLanguageContexts().containsKey(langParameter))
			return Framework.getLanguageContexts().get(langParameter);
		else
			return Framework.getLanguageContexts().get(Property.get("DEFAULT_LANGUAGE"));
	}
	
}
