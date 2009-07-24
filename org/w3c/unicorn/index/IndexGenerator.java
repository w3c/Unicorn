// $Id: IndexGenerator.java,v 1.8 2009-07-24 13:47:46 tgambet Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.index;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.w3.unicorn.tasklist.TUi;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.tasklist.parameters.ParameterType;
import org.w3c.unicorn.util.ListFiles;
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.util.MergeProperties;;

/**
 * IndexGenerator<br />
 * Created: Jun 20, 2006 3:07:09 PM<br />
 * 
 * @author Jean-Guilhem Rouel
 */
public class IndexGenerator {

	/**
	 * Object used for complex logging purpose
	 */
	public static final Log logger = LogFactory.getLog("org.w3c.unicorn.index");

	/**
	 * Context to generate pages using Apache Velocity
	 */
	private static VelocityContext aVelocityContext;

	/**
	 * Properties of the index generator framework
	 */
	private static Properties aProperties = new Properties();

	/**
	 * Velocity Engine to create pages from the templates
	 */
	private static VelocityEngine aVelocityEngine = new VelocityEngine();

	/**
	 * Load the properties and initialize apache velocity
	 */
	static {
		try {
			IndexGenerator.aProperties.load(new URL(Property.class.getResource("/"),
					Property.get("REL_PATH_TO_CONF_FILES") + "velocity.properties").openStream());
			IndexGenerator.aProperties.put(Velocity.FILE_RESOURCE_LOADER_PATH,
					Property.get("PATH_TO_TEMPLATES"));
			IndexGenerator.aVelocityEngine.init(IndexGenerator.aProperties);
		} catch (final MalformedURLException e) {
			IndexGenerator.logger.error("MalformedURLException : "
					+ e.getMessage(), e);
			e.printStackTrace();
		} catch (final IOException e) {
			IndexGenerator.logger.error("IOException : " + e.getMessage(), e);
			e.printStackTrace();
		} catch (final Exception e) {
			IndexGenerator.logger.error("Exception : " + e.getMessage(), e);
			e.printStackTrace();
		}

		IndexGenerator.aVelocityContext = new VelocityContext();
		IndexGenerator.aVelocityContext.put("tasklist", Framework.mapOfTask);

		IndexGenerator.aVelocityContext.put("dropdown", ParameterType.DROPDOWN);
		IndexGenerator.aVelocityContext.put("checkbox", ParameterType.CHECKBOX);
		IndexGenerator.aVelocityContext.put("checkboxlist", ParameterType.CHECKBOXLIST);
		IndexGenerator.aVelocityContext.put("radio", ParameterType.RADIO);
		IndexGenerator.aVelocityContext.put("textarea", ParameterType.TEXTAREA);
		IndexGenerator.aVelocityContext.put("textfield", ParameterType.TEXTFIELD);
		
		IndexGenerator.aVelocityContext.put("simple", TUi.SIMPLE);
		IndexGenerator.aVelocityContext.put("advanced", TUi.ADVANCED);
		IndexGenerator.aVelocityContext.put("none", TUi.NONE);
	}

	/**
	 * Generate the multiple indexes for unicorn
	 * 
	 * @throws ResourceNotFoundException
	 *             when templates not found
	 * @throws ParseErrorException
	 *             when error while parsing the configuration
	 * @throws Exception
	 *             any unknown error
	 */
	public static void generateIndexes() throws ResourceNotFoundException,
			ParseErrorException, Exception {
		IndexGenerator.logger.trace("generateIndexes");
		
		// Get the Properties object for the default language
		File[] defaultLangFile = ListFiles.listFiles(Property.get("PATH_TO_LANGUAGE_FILES"),
				"index\\." + Property.get("DEFAULT_LANGUAGE"));
		
		// Get the list of the language properties files
		File[] langFiles = ListFiles.listFiles(Property.get("PATH_TO_LANGUAGE_FILES"), "index");
		
		// Get all the languages and their associated code (defined in the name of the properties file) in a hashtable
		Map<String, String> languages = new Hashtable<String, String>();
		for (File langFile : langFiles) { 
			Properties props = new java.util.Properties();
		    props.load(langFile.toURL().openStream());
		    languages.put(langFile.getName().split("\\.")[1], props.getProperty("language"));
		}
		
		IndexGenerator.logger.info("Found Languages : " + languages.toString());
		
		aVelocityContext.put("languages", languages);
		
		MergeProperties mergeProps = new MergeProperties();
		
		for (File langFile : langFiles) {
			
			String langCode = langFile.getName().split("\\.")[1];
		    
		    Properties props = mergeProps.getMergeProperties(defaultLangFile[0], langFile);
		    
		    // Iteration on the properties to add them to the Velocity context
		    Set<Object> keys = props.keySet();
		    Iterator<Object> itr = keys.iterator();
		    String key;
		    while (itr.hasNext()) {
				key = itr.next().toString();
				aVelocityContext.put(key, props.get(key));
		    }
			
		    if (langCode.equals(Property.get("DEFAULT_LANGUAGE"))) {
		    	writeIndex("index.html");
		    	IndexGenerator.logger.info("Default language is \"" + props.getProperty("language") + "\" : created index.html");
		    }
		    
		    
		   
		    String indexPageName = "index." + langCode + ".html";
			writeIndex(indexPageName);
			
			IndexGenerator.logger.info("Created index page for language \"" + props.getProperty("language") + "\" : " + indexPageName);
		}
		
		
		
		Template template = aVelocityEngine.getTemplate("index/en_parameters.js.vm");
		
		OutputStreamWriter fileWriter = new OutputStreamWriter(
				new FileOutputStream(Property.get("PATH_TO_INDEX_OUTPUT")  + "en_parameters.js"),
				"UTF-8");
		
		template.merge(IndexGenerator.aVelocityContext, fileWriter);
		fileWriter.close();
	}
	
	private static void writeIndex(String pageName) throws ResourceNotFoundException, ParseErrorException, Exception {
		
		// index.vm is located in PATH_TO_TEMPLATES
		Template template = aVelocityEngine.getTemplate("index.vm");
		
		// Generate the files
		OutputStreamWriter fileWriter = new OutputStreamWriter(
				new FileOutputStream(Property.get("PATH_TO_INDEX_OUTPUT")  + pageName),
				"UTF-8");
		
		template.merge(IndexGenerator.aVelocityContext, fileWriter);
		fileWriter.close();
	}
	
	/**
	 * Launch the creation of the indexes
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		try {
			IndexGenerator.generateIndexes();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final ResourceNotFoundException e) {
			e.printStackTrace();
		} catch (final ParseErrorException e) {
			e.printStackTrace();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
