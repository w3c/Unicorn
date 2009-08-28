// $Id: IndexGenerator.java,v 1.2 2009-08-28 12:40:17 jean-gui Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.index;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.w3.unicorn.tasklist.TUi;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.tasklist.parameters.ParameterType;
import org.w3c.unicorn.util.ListFiles;
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.util.TemplateHelper;

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
	 * Load the properties and initialize apache velocity
	 */
	public static void init() {
		IndexGenerator.aVelocityContext = new VelocityContext();
		IndexGenerator.aVelocityContext.put("tasklist", Framework.mapOfTask);

		IndexGenerator.aVelocityContext.put("dropdown", ParameterType.DROPDOWN);
		IndexGenerator.aVelocityContext.put("checkbox", ParameterType.CHECKBOX);
		IndexGenerator.aVelocityContext.put("checkboxlist",
				ParameterType.CHECKBOXLIST);
		IndexGenerator.aVelocityContext.put("radio", ParameterType.RADIO);
		IndexGenerator.aVelocityContext.put("textarea", ParameterType.TEXTAREA);
		IndexGenerator.aVelocityContext.put("textfield",
				ParameterType.TEXTFIELD);

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

		// Get the list of the language properties files
		File[] langFiles = ListFiles.listFiles(Property
				.get("PATH_TO_LANGUAGE_FILES"), "index");

		// Get all the languages and their associated code (defined in the name
		// of the properties file) in a hashtable
		Map<String, String> languages = new Hashtable<String, String>();
		for (File langFile : langFiles) {
			Properties props = new java.util.Properties();
			props.load(langFile.toURI().toURL().openStream());
			languages.put(langFile.getName().split("\\.")[1], props
					.getProperty("language"));
		}

		IndexGenerator.logger.info("Found Languages : " + languages.toString());
		aVelocityContext.put("languages", languages);
		aVelocityContext.put("param_prefix", Property
				.get("UNICORN_PARAMETER_PREFIX"));

		for (File langFile : langFiles) {
			String langCode = langFile.getName().split("\\.")[1];
			// aVelocityContext.put("lang", langCode);
			TemplateHelper.generateFileFromTemplate("index", langCode, Property
					.get("PATH_TO_INDEX_OUTPUT"), "html", aVelocityContext);
		}

		TemplateHelper.generateFileFromTemplate("index/en_parameters", null,
				Property.get("PATH_TO_INDEX_OUTPUT"), "js", aVelocityContext);
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
