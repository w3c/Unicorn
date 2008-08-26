// $Id: IndexGenerator.java,v 1.3 2008-08-26 15:30:04 fbatard Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.index;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.w3c.unicorn.Framework;
import org.w3.unicorn.tasklist.TUi;
import org.w3c.unicorn.tasklist.parameters.ParameterType;
import org.w3c.unicorn.util.ListFiles;
import org.w3c.unicorn.util.Property;

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
			IndexGenerator.aProperties.load(new URL("file:"
					+ Property.get("VELOCITY_CONFIG_FILE")).openStream());
			IndexGenerator.aProperties.put(Velocity.FILE_RESOURCE_LOADER_PATH,
					Property.get("PATH_TO_INDEX_TEMPLATES"));
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

		final File[] tFile = ListFiles.listFiles(Property
				.get("PATH_TO_INDEX_TEMPLATES"), "\\.vm$");
		for (final File aFile : tFile) {
			final String sName = aFile.getName();
			final String sOutputName = sName.substring(0, sName.length() - 3);

			final Template aTemplate = IndexGenerator.aVelocityEngine
					.getTemplate(sName, "UTF-8");

			final FileWriter aFileWriter = new FileWriter(Property
					.get("PATH_TO_INDEX_OUTPUT")
					+ sOutputName);
			aTemplate.merge(IndexGenerator.aVelocityContext, aFileWriter);
			aFileWriter.close();

			IndexGenerator.logger.debug("Index file "
					+ Property.get("PATH_TO_INDEX_OUTPUT") + sOutputName
					+ " generated.");
		}
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
