// $Id: TemplateHelper.java,v 1.3 2009-08-28 16:11:41 jean-gui Exp $
// Author: Thomas GAMBET.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2009.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.w3c.unicorn.Framework;

/**
 * TemplateHelper provides functionalities to merge properties object, to load
 * properties objects in a velocity context, to get an internationalized
 * template, and to write internationalized templates to a file.
 * 
 * @author Thomas GAMBET
 */
public class TemplateHelper {
	public static final Log logger = LogFactory.getLog(TemplateHelper.class);

	private static VelocityContext context = new VelocityContext();

	private static VelocityEngine engine; // = new VelocityEngine();

	public static void init() {
		
		engine = Framework.getVelocityEngine();
		
		//Properties aProperties = new Properties();
		
		/*Properties aProperties = Property.getProps("velocity.properties");
		
		try {
			//aProperties.load(Property.getPropertyFileURL("velocity.properties")
			//		.openStream());

			aProperties.put(RuntimeConstants.FILE_RESOURCE_LOADER_PATH,
					Property.get("PATH_TO_TEMPLATES"));

			engine.init(aProperties);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}

	@SuppressWarnings("finally")
	public static Properties getPropsFromFile(File propFile) {
		Properties props = new Properties();
		try {
			props.load(propFile.toURI().toURL().openStream());
		} catch (IOException e) {
			logger.error("Unable to load properties file : " + e.getMessage(),
					e);
			e.printStackTrace();
		} finally {
			return props;
		}
	}

	public static Properties getMergeProps(Properties defaultProps, 
			Properties sourceProps) {
		
		Properties propMerge = new Properties();

		Set<Object> keys = defaultProps.keySet();
		Iterator<Object> itr = keys.iterator();
		String key;

		while (itr.hasNext()) {
			key = itr.next().toString();
			if (sourceProps.containsKey(key)) {
				propMerge.put(key, sourceProps.get(key));
			} else {
				propMerge.put(key, defaultProps.get(key));
			}
		}

		keys = sourceProps.keySet();
		itr = keys.iterator();
		while (itr.hasNext()) {
			key = itr.next().toString();
			if (!defaultProps.containsKey(key)) {
				propMerge.put(key, sourceProps.get(key));
			}
		}

		return propMerge;
	}

	public static Properties getMergePropsFromFiles(File defaultPropFile,
			File sourcePropFile) {
		Properties defaultProps = new Properties();
		Properties sourceProps = new Properties();
		try {
			defaultProps.load(defaultPropFile.toURI().toURL().openStream());
		} catch (IOException e) {
			logger.error("Unable to load default language properties : "
					+ e.getMessage(), e);
			e.printStackTrace();
			return null;
		}

		try {
			sourceProps.load(sourcePropFile.toURI().toURL().openStream());
		} catch (IOException e) {
			logger.error("Unable to find desired language properties : "
					+ e.getMessage(), e);
			e.printStackTrace();
			return defaultProps;
		}

		return getMergeProps(defaultProps, sourceProps);

		/*
		 * Properties propMerge = new Properties();
		 * 
		 * Set<Object> keys = defaultProps.keySet(); Iterator<Object> itr =
		 * keys.iterator(); String key;
		 * 
		 * while (itr.hasNext()) { key = itr.next().toString(); if
		 * (sourceProps.containsKey(key)) propMerge.put(key,
		 * sourceProps.get(key)); else propMerge.put(key,
		 * defaultProps.get(key)); }
		 * 
		 * return propMerge;
		 */
	}

	public static void loadInVelocityContext(Properties props,
			VelocityContext context) {
		Set<Object> keys = props.keySet();
		Iterator<Object> itr = keys.iterator();
		String key;
		while (itr.hasNext()) {
			key = itr.next().toString();
			context.put(key, props.get(key));
		}
	}

	public static Template getInternationalizedTemplate(String templateName,
			String langCode, VelocityContext context) {

		if (langCode != null) {
			context.put("lang", langCode);

			// Error templates have the same language properties file that their
			// coresponding non-error template
			String langFileName = templateName;
			if (templateName.length() > 6
					&& templateName.substring(templateName.length() - 6,
							templateName.length()).equals(".error")) {
				langFileName = templateName.substring(0,
						templateName.length() - 6);
			}

			// Language file for this template
			File langFile = new File(Property.get("PATH_TO_LANGUAGE_FILES")
					+ langFileName + "." + langCode + ".properties");

			// Default language file
			File defaultLangFile = new File(Property
					.get("PATH_TO_LANGUAGE_FILES")
					+ langFileName
					+ "."
					+ Property.get("DEFAULT_LANGUAGE")
					+ ".properties");

			// Merge the properties or use default language
			Properties mergedProps = new Properties();

			if (langFile.exists()) {
				mergedProps = getMergePropsFromFiles(defaultLangFile, langFile);
			} else {
				try {
					mergedProps.load(defaultLangFile.toURI().toURL()
							.openStream());
				} catch (IOException e1) {
					logger.error("IOException : " + e1.getMessage(), e1);
					e1.printStackTrace();
				}
			}

			File generalLangFile = new File(Property
					.get("PATH_TO_LANGUAGE_FILES")
					+ "general." + langCode + ".properties");

			if (!generalLangFile.exists()) {
				generalLangFile = new File(Property
						.get("PATH_TO_LANGUAGE_FILES")
						+ "general."
						+ Property.get("DEFAULT_LANGUAGE")
						+ ".properties");
			}

			mergedProps = getMergeProps(mergedProps,
					getPropsFromFile(generalLangFile));

			// Load in velocity context
			TemplateHelper.loadInVelocityContext(mergedProps, context);
		}

		// Return the template
		try {
			Template resultTemplate = engine.getTemplate(templateName + ".vm",
					"UTF-8");
			return resultTemplate;
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
			e.printStackTrace();
			return null;
		}
	}

	public static String generateFileFromTemplate(String templateName,
			String langCode, String destination, String fileExtension,
			VelocityContext context) {

		String destFileName;
		String tempFileName;

		String[] split = templateName.split("/");
		if (split.length > 0) {
			tempFileName = split[split.length - 1];
		} else {
			tempFileName = templateName;
		}

		if (langCode != null) {
			destFileName = tempFileName + "." + langCode + "." + fileExtension;
		} else {
			destFileName = tempFileName + "." + fileExtension;
		}

		if ((new File(destination + destFileName)).exists()) {
			return destination + destFileName;
		}

		if (langCode != null) {
			File langFile = new File(Property.get("PATH_TO_LANGUAGE_FILES")
					+ templateName + "." + langCode + ".properties");

			if (!langFile.exists()
					&& !langCode.equals(Property.get("DEFAULT_LANGUAGE"))) {
				return generateFileFromTemplate(templateName, Property
						.get("DEFAULT_LANGUAGE"), destination, fileExtension,
						context);
			}
		}

		Template template = getInternationalizedTemplate(templateName,
				langCode, context);

		try {
			OutputStreamWriter fileWriter = new OutputStreamWriter(
					new FileOutputStream(destination + destFileName), "UTF-8");
			template.merge(context, fileWriter);
			fileWriter.close();
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
			e.printStackTrace();
		}

		return destination + destFileName;
	}

	public static String generateFileFromTemplate(String templateName,
			String langCode, String destination, String fileExtension) {
		return generateFileFromTemplate(templateName, langCode, destination,
				fileExtension, context);
	}
}
