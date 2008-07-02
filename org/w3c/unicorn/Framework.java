// $Id: Framework.java,v 1.12 2008-07-02 17:34:47 jean-gui Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.contract.Observer;
import org.w3c.unicorn.contract.WADLUnmarshaller;
import org.w3c.unicorn.contract.WADLUnmarshallerXPath;
import org.w3c.unicorn.response.parser.ResponseParser;
import org.w3c.unicorn.tasklist.RDFUnmarshaller;
import org.w3c.unicorn.tasklist.RDFUnmarshallerJena;
import org.w3c.unicorn.tasklist.Task;
import org.w3c.unicorn.tasklist.TasksListUnmarshaller;
import org.w3c.unicorn.tasklist.TasksListUnmarshallerJAXB;
import org.w3c.unicorn.util.ListFiles;
import org.w3c.unicorn.util.LocalizedString;
import org.w3c.unicorn.util.Property;

/**
 * Main class of the central module of UniCORN.
 * 
 * @author Damien LEROY
 */
public class Framework {

	/**
	 * Instance of log class to, as his name say, log any information during
	 * execution.
	 */
	public static final Log logger = LogFactory.getLog(Framework.class);

	/**
	 * Data structure for the Observers
	 */
	public static Map<String, Observer> mapOfObserver = null;

	/**
	 * Data structure for the tasks
	 */
	public static Map<String, Task> mapOfTask = null;

	/**
	 * Properties of the framework
	 */
	public static Properties aPropertiesExtension;

	/**
	 * List of availables output lang in PATH_TO_OUTPUT_TEMPLATES
	 */
	public static Set<String> outputLang;

	/**
	 * Data structure for the various response parser
	 */
	public static Map<String, ResponseParser> mapOfReponseParser = null;
	/**
	 * Load the list of extensions
	 */
	static {
		try {
			final URL aURLPropFile = Framework.class
					.getResource("extensions.properties");
			final Properties aProperties = new Properties();
			aProperties.load(aURLPropFile.openStream());
			Framework.aPropertiesExtension = aProperties;
		} catch (final IOException e) {
			Framework.logger.error("IOException : " + e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Load the map of ResponseParser
	 */
	static {
		if (Framework.logger.isDebugEnabled()) {
			Framework.logger.debug("Loading available parsers...");
		}
		try {
			// Retrieve the properties of the response parsers in the resources
			mapOfReponseParser = new LinkedHashMap<String, ResponseParser>();
			final URL aURLPropFile = Framework.class
					.getResource("responseParsers.properties");
			final Properties aProperties = new Properties();
			aProperties.load(aURLPropFile.openStream());

			for (Entry<Object, Object> e : aProperties.entrySet()) {
				ResponseParser aResponseParser = (ResponseParser) (Class
						.forName((String) (e.getValue())).newInstance());
				mapOfReponseParser.put((String) (e.getKey()), aResponseParser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (Framework.logger.isDebugEnabled()) {
				Framework.logger.debug("... Parsers loaded");
			}
		}
	}

	/**
	 * Statics operations to initialise framework the first time this class is
	 * used.
	 */
	static {
		if (Framework.logger.isDebugEnabled()) {
			Framework.logger.debug("Loading available observers...");
		}
		final String sLanguage = Property.get("DEFAULT_LANGUAGE");
		if (null != sLanguage) {
			LocalizedString.DEFAULT_LANGUAGE = sLanguage;
		}
		Framework.mapOfObserver = new LinkedHashMap<String, Observer>();
		try {
			// Add all observer contract
			final BufferedReader aBufferedReader;
			aBufferedReader = new BufferedReader(new FileReader(Property
					.get("OBSERVER_LIST_FILE")));

			// Observer list file contains URL contracts of observers
			for (String sReadLine = aBufferedReader.readLine(); null != sReadLine; sReadLine = aBufferedReader
					.readLine()) {

				if ("".equals(sReadLine.trim())) {
					continue;
				}

				// Get URL of the contract. If the name of wadl file is not
				// defined,
				// the contract's name will be observer.wadl
				final String sWADL;
				if (sReadLine.matches(".*\\.wadl$")) {
					sWADL = sReadLine;
				} else {
					sWADL = sReadLine + "/"
							+ Property.get("OBSERVER_XML_FILENAME");
				}

				try {
					if (Framework.logger.isDebugEnabled()) {
						Framework.logger.debug("Observer WADL file : " + sWADL
								+ ".");
					}
					// Create each observer
					final Observer aObserver = new Observer();
					final WADLUnmarshaller aWADLUnmarshaller = new WADLUnmarshallerXPath();
					aWADLUnmarshaller.addURL(new URL(sWADL));
					aWADLUnmarshaller.unmarshal();

					aObserver.setListOfCallMethod(aWADLUnmarshaller
							.getListOfCallMethod());
					aObserver.setParamLangName(aWADLUnmarshaller
							.getNameOfLangParameter());
					aObserver.setID(aWADLUnmarshaller.getID());
					aObserver.setName(aWADLUnmarshaller.getName());
					aObserver
							.setDescription(aWADLUnmarshaller.getDescription());
					aObserver.setHelpLocation(aWADLUnmarshaller
							.getHelpLocation());
					aObserver.setProvider(aWADLUnmarshaller.getProvider());
					aObserver.setMapOfInputMethod(aWADLUnmarshaller
							.getMapOfInputMethod());
					aObserver.setResponseType(aWADLUnmarshaller
							.getResponseType());
					Framework.mapOfObserver.put(new String(aObserver.getID()),
							aObserver);
				} catch (final Exception e) {
					Framework.logger.error("Exception : " + e.getMessage(), e);
					e.printStackTrace();
				}
			}
			if (Framework.logger.isDebugEnabled()) {
				Framework.logger.debug("... Observers loaded.");
			}
		} catch (final FileNotFoundException aFileNotFoundException) {
			Framework.logger.error("FileNotFoundException : "
					+ aFileNotFoundException.getMessage(),
					aFileNotFoundException);
			aFileNotFoundException.printStackTrace();
		}

		catch (final IOException ioe) {
			Framework.logger.error("IOException : " + ioe.getMessage(), ioe);
			ioe.printStackTrace();
		}

		if (Framework.logger.isDebugEnabled()) {
			Framework.logger.debug("Task initialisation.");
		}
		try {
			// parse all the task files

			final File[] tFileXML = ListFiles.listFiles(Property
					.get("PATH_TO_TASKLIST"), "\\.xml");

			final TasksListUnmarshaller aTaskListUnmarshaller = new TasksListUnmarshallerJAXB(
					Framework.mapOfObserver);
			for (final File aFile : tFileXML) {
				aTaskListUnmarshaller.addURL(aFile.toURI().toURL());			
				aTaskListUnmarshaller.unmarshal();
			}

			final File[] tFileRDF = ListFiles.listFiles(Property
					.get("PATH_TO_TASKLIST"), "\\.rdf");
			final RDFUnmarshaller aRDFUnmarshaller = new RDFUnmarshallerJena();
			aRDFUnmarshaller.setMapOfTask(aTaskListUnmarshaller.getMapOfTask());
			aRDFUnmarshaller.setMapOfObserver(Framework.mapOfObserver);
			for (final File aFile : tFileRDF) {
				aRDFUnmarshaller.addURL(aFile.toURI().toURL());
			}
			aRDFUnmarshaller.unmarshal();

			Framework.mapOfTask = aTaskListUnmarshaller.getMapOfTask();
		} catch (final JAXBException e) {
			Framework.logger.error("JAXBException : " + e.getMessage(), e);
			e.printStackTrace();
		} catch (final MalformedURLException e) {
			Framework.logger.error("MalformedURLException : " + e.getMessage(),
					e);
			e.printStackTrace();
		} catch (final Exception e) {
			Framework.logger.error("Exception : " + e.getMessage(), e);
			e.printStackTrace();
		}

		// Retrieve output lang from PATH_TO_OUTPUT_TEMPLATES
		File[] listFD = (new File(Property.get("PATH_TO_OUTPUT_TEMPLATES")))
				.listFiles(new FileFilter() {
					public boolean accept(File pathname) {
						return pathname.getName().matches(".*\\.vm$");
					}
				});
		outputLang = new HashSet<String>();
		for (int i = 0; i < listFD.length; i++) {
			outputLang.add((listFD[i].getName().split("_"))[0]);
		}

		Framework.logger.info("End of initialisation of UniCORN.");
	}
}
