package org.w3c.unicorn.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.velocity.VelocityContext;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.action.LanguageAction;

import com.ibm.icu.util.LocaleMatcher;
import com.ibm.icu.util.LocalePriorityList;
import com.ibm.icu.util.ULocale;

public class Language {
	
	private static ULocale defaultLocale;
	private static ArrayList<ULocale> uiLocales;
	private static ArrayList<ULocale> availableLocales;
	private static LocaleMatcher uiLocaleMatcher;
	private static LocaleMatcher availableLocaleMatcher;
	private static LocaleMatcher installedLocaleMatcher;
	
	public static void reset() {
		defaultLocale = null;
		uiLocales = null;
		availableLocales = null;
		uiLocaleMatcher = null;
		availableLocaleMatcher = null;
		installedLocaleMatcher = null;
	}
	
	public static void initLocaleMatcher(ULocale defaultLocale) {
		Language.defaultLocale = defaultLocale;
		availableLocales = new ArrayList<ULocale>();
		
		LocalePriorityList.Builder builder = LocalePriorityList.add(defaultLocale);
		for (ULocale locale : ULocale.getAvailableLocales())
			if (locale != defaultLocale)
				builder = builder.add(locale);
		LocalePriorityList installedLocalesPriorityList = builder.build();
		installedLocaleMatcher = new LocaleMatcher(installedLocalesPriorityList);
		
		TreeMap<String, ULocale> availableLocalesTree = new TreeMap<String, ULocale>();
		for (ULocale locale : ULocale.getAvailableLocales()) {
			if (!availableLocalesTree.containsKey(locale.getLanguage())) {
				availableLocalesTree.put(locale.getLanguage(), locale);
				continue;
			} else {
				String localeName = locale.getLanguage() + "_" + locale.getScript();
				if (!availableLocalesTree.get(locale.getLanguage()).getScript().equals(locale.getScript()) && !availableLocalesTree.containsKey(localeName))
					availableLocalesTree.put(localeName, locale);
			}
		}
		LocalePriorityList.Builder builder2 = LocalePriorityList.add(defaultLocale);
		for (String key : availableLocalesTree.keySet()) {
			availableLocales.add(availableLocalesTree.get(key));
			if (availableLocalesTree.get(key) != defaultLocale)
				builder2 = builder2.add(availableLocalesTree.get(key));
		}
		LocalePriorityList availableLocalesPriorityList = builder2.build();
		availableLocaleMatcher = new LocaleMatcher(availableLocalesPriorityList);
		sortByDisplayName(availableLocales);
	}
	
	public static void initUILocaleMatcher() {
		LocalePriorityList.Builder builder = null;
		builder = LocalePriorityList.add(defaultLocale);
		for (ULocale locale : uiLocales)
			if (locale != defaultLocale)
				builder = builder.add(locale);
		LocalePriorityList uiLocalesPriorityList = builder.build();
		uiLocaleMatcher = new LocaleMatcher(uiLocalesPriorityList);
		sortByDisplayName(uiLocales);
	}
	
	public static ULocale getLocale(String languageCode) {
		return installedLocaleMatcher.getBestMatch(languageCode);
	}
	
	public static ULocale getUILocale(String languageCode) {
		return uiLocaleMatcher.getBestMatch(languageCode);
	}
	
	public static ULocale getAvailableLocale(String languageCode) {
		return availableLocaleMatcher.getBestMatch(languageCode);
	}
	
	public static VelocityContext getContext(ULocale localeParam) {
		if (localeParam != null && Framework.getLanguageContexts().containsKey(localeParam))
			return Framework.getLanguageContexts().get(localeParam);
		else
			return Framework.getLanguageContexts().get(defaultLocale);
	}

	public static void complete(Properties props, Properties defaultProps, String fileName) {
		for (Object key : defaultProps.keySet()) {
			if (!props.containsKey(key) && key != "complete") {
				props.put(key, defaultProps.get(key));
				Framework.logger.info(">> Missing property in " + fileName + " for key: \"" + (String) key + "\". Added default property for this key: \"" + defaultProps.get(key) + "\""); 
			}
		}
	}
	
	public static void clean(UCNProperties props, UCNProperties defaultProps, String fileName) {
		ArrayList<String> keys = new ArrayList<String>();
		for (Object key : props.keySet()) {
			if (!defaultProps.containsKey(key)) {
				keys.add((String) key);
				Framework.logger.warn(">> Inexistent property in " + fileName + " for key: \"" + (String) key + "\". This property should be removed manually from the language file."); 
			}
		}
		for (String key : keys)
			props.remove(key);
	}
	
	public static UCNProperties load(File langFile) throws IllegalArgumentException, FileNotFoundException, IOException {
		FileInputStream fis = new FileInputStream(langFile);
		InputStreamReader isr;
		try {
			isr = new InputStreamReader(fis, "UTF-8");
			UCNProperties props = new UCNProperties();
			props.load(isr);
			return props;
		} catch (UnsupportedEncodingException e) {
			// This should not happen
			Framework.logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	public static ULocale getLocaleFromFileName(String fileName) throws IllegalArgumentException{
		String localeString = fileName.split("\\.")[0];
		ULocale locale = Language.getLocale(localeString);
		if (locale == null) {
			throw new IllegalArgumentException("Locale not found for file name: " + fileName);
		} else if (!availableLocales.contains(locale)) {
			throw new IllegalArgumentException("Locale for file name: " + fileName + " is not a Unicorn available locale.");
		}
		return locale;
	}
	
	public static boolean isComplete(ULocale localeParam) {
		Properties testedProps = LanguageAction.getLanguageProperties().get(localeParam);
		Properties testedMetadataProps = LanguageAction.getMetadataProperties().get(localeParam);
		
		if (testedProps == null)
			return false;
		
		if (testedProps.get("complete") == null) {
			for (Object key : LanguageAction.getLanguageProperties().get(defaultLocale).keySet()) {
				if (!testedProps.containsKey(key) && key != "complete") {
					testedProps.put("complete", "false");
					return false;
				}
			}
			for (Object key : LanguageAction.getMetadataProperties().get(defaultLocale).keySet()) {
				if (!testedMetadataProps.containsKey(key)) {
					testedProps.put("complete", "false");
					return false;
				}
			}
			return true;
		} else {
			return testedProps.get("complete").equals("true");
		}
	}
	
	public static String evaluate(String lang, String messageKey, String... args) {
		if (Framework.getLanguageProperties().get(lang) == null)
			return messageKey;
		
		if (messageKey.startsWith("$"))
			messageKey = messageKey.replace("$", "");
		
		String message = Framework.getLanguageProperties().get(lang).getProperty(messageKey);
		
		if (message == null)
			return messageKey;
		
		if (!message.contains("%"))
			return message;
		
		String result = message;
		int i = 1;
		if (args == null)
			return result;
		for (String str : args) {
			if (str.startsWith("$"))
				str = str.replace("$", "");
			
			String string = Framework.getLanguageProperties().get(lang).getProperty(str) != null ? Framework.getLanguageProperties().get(lang).getProperty(str) : str; 
			result = result.replaceAll("%"+i, string);
			i++;
		}
		return result;
	}
	
	public static String getLocaleDirection(ULocale locale) {
		if (locale.getLineOrientation().equals("left-to-right"))
			return "ltr";
		if (locale.getLineOrientation().equals("right-to-left"))
			return "rtl";
		return "ltr";
	}
	
	private static void sortByDisplayName(ArrayList<ULocale> localeArray) {
		Collections.sort(localeArray, new Comparator<ULocale>() {
			public int compare(ULocale l1, ULocale l2) {
				String loc1 = l1.getDisplayName(l1);
				String loc2 = l2.getDisplayName(l2);
				return loc1.compareToIgnoreCase(loc2);
			}
		});
	}
	
	public static ULocale getDefaultLocale() {
		return defaultLocale;
	}

	public static void addUiLocale(ULocale locale) {
		if (uiLocales == null)
			uiLocales = new ArrayList<ULocale>();
		uiLocales.add(locale);
	}

	public static ArrayList<ULocale> getUiLocales() {
		return uiLocales;
	}

	public static ArrayList<ULocale> getAvailableLocales() {
		return availableLocales;
	}
	
}
