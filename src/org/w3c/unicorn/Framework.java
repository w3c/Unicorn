// $Id: Framework.java,v 1.8 2009-09-02 10:39:15 tgambet Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.w3c.unicorn.contract.Observer;
import org.w3c.unicorn.contract.WADLUnmarshaller;
import org.w3c.unicorn.contract.WADLUnmarshallerXPath;
import org.w3c.unicorn.exceptions.InitializationFailedException;
import org.w3c.unicorn.exceptions.UnknownParserException;
import org.w3c.unicorn.response.parser.ResponseParser;
import org.w3c.unicorn.tasklist.RDFUnmarshaller;
import org.w3c.unicorn.tasklist.RDFUnmarshallerJena;
import org.w3c.unicorn.tasklist.Task;
import org.w3c.unicorn.tasklist.TaskListUnmarshallerBeans;
import org.w3c.unicorn.tasklist.Tasklist;
import org.w3c.unicorn.tasklist.TasksListUnmarshaller;
import org.w3c.unicorn.util.Language;
import org.w3c.unicorn.util.ListFiles;
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.util.UCNProperties;

import com.hp.hpl.jena.rdf.model.Model;

/**
 * Main class of the central module of UniCORN.
 * 
 * @author Damien LEROY
 */
public class Framework {

	/**
	 * Data structure for the Observers
	 */
	public static Map<String, Observer> mapOfObserver;

	/**
	 * Data structure for the tasks
	 */
	public static Tasklist mapOfTask;

	/**
	 * Data structure for the various response parser
	 */
	public static Map<String, ResponseParser> mapOfReponseParser;
	
	/**
	 * Logger
	 */
	public static Log logger = LogFactory.getLog(Framework.class);
	
	/**
	 * URI to unicorn home
	 */
	public static URI unicornHome;
	
	/**
	 * True if initialization did not throw any exception
	 */
	public static boolean isUcnInitialized;
	private static Hashtable<String, Properties> unicornPropertiesFiles;
	private static Hashtable<String, VelocityContext> languageContexts;
	private static Hashtable<String, Properties> languageProperties;
	private static Hashtable<String, String> languages;
	private static VelocityEngine velocityEngine;
	private static String[] configFiles = {
		"extensions.properties",
		"responseParsers.properties",
		"specialFormaters.properties",
		"velocity.properties"};

	public static void reset() {
		unicornPropertiesFiles = new Hashtable<String, Properties>();
		languageContexts = new Hashtable<String, VelocityContext>();
		languageProperties = new Hashtable<String, Properties>();
		languages = new Hashtable<String, String>();
		mapOfObserver = new LinkedHashMap<String, Observer>();
		mapOfReponseParser = new LinkedHashMap<String, ResponseParser>();
	}
	
	/**
	 * Initialize Unicorn
	 */
	public static void init() {
		isUcnInitialized = false;
		reset();
		try {
			initCore();
			initConfig();
			initUnmarshallers();
			initResponseParsers();
			initObservers();
			initTasklists();
			initLanguages();
			initVelocity();
			isUcnInitialized = true;
		} catch (InitializationFailedException e) {
			logger.fatal(e.getMessage(), e);
		}
		
	}
	
	public static void initCore() throws InitializationFailedException {
		
		// Checks that unicorn.home (JVM parameter) is set to an existing directory
		String ucnHome = System.getProperty("unicorn.home");
		if (ucnHome == null) {
			String fatal = "\"unicorn.home\" is not set in the JVM parameters. Please read the README file before trying to install Unicorn";
			System.err.println("FATAL: " + fatal);
			throw new InitializationFailedException(fatal);
		} else {
			File ucnHomeFile = new File(ucnHome);
			if (!ucnHomeFile.exists() || !ucnHomeFile.isDirectory()) {
				String fatal = "JVM parameter \"unicorn.home\" is not an existing directory: " + System.getProperty("unicorn.home");
				System.err.println("FATAL: " + fatal);
				throw new InitializationFailedException(fatal);
			} else {
				unicornHome = ucnHomeFile.toURI();
				logger.info("OK - JVM parameter \"unicorn.home\" was found: " + unicornHome.getPath());
			}
		}
		
		// Log4j initialization attempt
		String log4jPath = unicornHome.getPath() + "/WEB-INF/conf/log4j.properties";
		try {
			loadConfigFile(log4jPath, true);
			PropertyConfigurator.configure(unicornPropertiesFiles.get("log4j.properties"));
			logger.info("OK - JVM parameter \"unicorn.home\" was found: " + unicornHome.getPath());
			logger.info("OK - Log4j successfully initialized");
			logger.debug("> Used log4j.properties file: " + log4jPath);
			//logger.debug("> log4j.properties:" + (UCNProperties) unicornPropertiesFiles.get("log4j.properties")); // already logged by loadConfigFile()
		} catch (FileNotFoundException e) {
			logger.warn("Log4j config file \"log4j.properties\" could not be found: " + log4jPath);
			logger.warn("Log4j will not be initialized");
		} catch (IOException e) {
			logger.error("Error reading \"log4j.properties\": ", e);
			logger.warn("Log4j will not be initialized");
		}
		
	}	
	public static void initConfig() throws InitializationFailedException {
		// Load unicorn.properties	
		logger.debug("-------------------------------------------------------");
		String unicornPath = unicornHome.getPath() + "/WEB-INF/conf/unicorn.properties";
		try {
			loadConfigFile(unicornPath, true);
			logger.info("OK - Config file unicorn.properties successfully loaded");
		} catch (FileNotFoundException e) {
			throw new InitializationFailedException("Unicorn config file \"unicorn.properties\" could not be found: " + unicornPath);
		} catch (IOException e) {
			throw new InitializationFailedException("Error reading \"unicorn.properties\": " + e.getMessage());
		}

		// Loading other config files
		for (String fileName : configFiles) {
			String path = Property.get("PATH_TO_CONF_FILES") + fileName;
			logger.debug("-------------------------------------------------------");
			try {
				loadConfigFile(path, false);
				logger.info("OK - Config file " + fileName + " successfully loaded");
			} catch (FileNotFoundException e) {
				throw new InitializationFailedException("Mandatory config file \"" + fileName + "\" could not be found: " + path);
			} catch (IOException e) {
				throw new InitializationFailedException("Error reading \"" + fileName + "\": " + e.getMessage());
			}
		}
	}
	public static void initUnmarshallers() {
		// Initialize WADLUnmarshallerXPath (Gets the Namespace URI and the prefix)
		WADLUnmarshallerXPath.setNamespaceContext(new NamespaceContext() {
			public String getNamespaceURI(final String sPrefix) {
				if ("xs".equals(sPrefix)) {
					return "http://www.w3.org/2001/XMLSchema";
				} else if ("uco".equals(sPrefix)) {
					return "http://www.w3.org/unicorn/observationresponse";
				} else {
					return null;
				}
			}
			public String getPrefix(final String sNamespaceURI) {
				if ("http://www.w3.org/2001/XMLSchema".equals(sNamespaceURI)) {
					return "xs";
				} else if ("http://www.w3.org/unicorn/observationresponse"
						.equals(sNamespaceURI)) {
					return "uco";
				} else {
					return null;
				}
			}
			public Iterator<String> getPrefixes(final String sNamespaceURI) {
				return null;
			}
		});
		
		// Initialize RDFUnmarshallerJena
		logger.debug("-------------------------------------------------------");
		logger.debug("Initializing RDFUnmarshallerJena");
		
		try {
			FileInputStream fis = new FileInputStream(Property.get("TASKLIST_RDF_MODEL"));
			RDFUnmarshallerJena.getModel().read(fis, null);
			logger.debug("> Used model: " + Property.get("TASKLIST_RDF_MODEL"));
		} catch (FileNotFoundException e) {
			logger.fatal("The tasklist rdf model could not be found: " + Property.get("TASKLIST_RDF_MODEL"));
			return;
		}
		Model model = RDFUnmarshallerJena.getModel();
		String namespace = RDFUnmarshallerJena.getUcnNamespace();
		// define resource use to find information into the RDF graph
		RDFUnmarshallerJena.setRESOURCE_TASK(model.getProperty(
				namespace + "Task"));
		// define property use to find information into the RDF graph
		RDFUnmarshallerJena.setPROPERTY_DESCRIPTION(model.getProperty(
				namespace + "description"));
		RDFUnmarshallerJena.setPROPERTY_HASPARAMETER(model.getProperty(
				namespace + "hasParameter"));
		RDFUnmarshallerJena.setPROPERTY_HASVALUE(model.getProperty(
				namespace + "hasValue"));
		RDFUnmarshallerJena.setPROPERTY_LONGNAME(model.getProperty(
				namespace + "longName"));
		RDFUnmarshallerJena.setPROPERTY_PARAMETER(model.getProperty(
				namespace + "parameter"));
		RDFUnmarshallerJena.setPROPERTY_REFERENCE(model.getProperty(
				namespace + "reference"));
		RDFUnmarshallerJena.setPROPERTY_DEFAULT(model.getProperty(
				namespace + "default"));
		RDFUnmarshallerJena.setPROPERTY_TYPE(model.getProperty(
				"http://www.w3.org/1999/02/22-rdf-syntax-ns#type"));
		RDFUnmarshallerJena.setPROPERTY_VALUE(model.getProperty(
				namespace + "value"));
		logger.info("OK - RDFUnmarshallerJena successfully initialized.");
	}
	public static void initResponseParsers() throws InitializationFailedException {
	    // Load the map of ResponseParser
		logger.debug("-------------------------------------------------------");
		logger.debug("Loading available parsers form responseParsers.properties");
		Properties aProperties = Property.getProps("responseParsers.properties");
		for (Object key : aProperties.keySet()) {
			String className = aProperties.getProperty(key.toString());
			try {
				ResponseParser aResponseParser = (ResponseParser) Class
					.forName(className).newInstance();
				mapOfReponseParser.put(key.toString(), aResponseParser);
				logger.debug("> Parser loaded: " + mapOfReponseParser.get(key).getClass().toString());
			} catch (ClassNotFoundException e) {
				logger.warn("Class not found: " + className + ". Check responseParsers.properties.", e);
			} catch (Exception e) {
				logger.warn("Error trying to instanciate: " + className, e);
			}
		}
		if (mapOfReponseParser.size() == 0) {
			throw new InitializationFailedException("There is no parser loaded. Check responseParsers.properties.");
		} else {
			logger.info("OK - " + mapOfReponseParser.size() + " parser(s) successfully loaded.");
		}

	}
	public static void initObservers() throws InitializationFailedException {
		// Loading observers
		logger.debug("-------------------------------------------------------");
		logger.debug("Loading available observers from the observers list file.");
		BufferedReader aBufferedReader;
		try {
			aBufferedReader = new BufferedReader(new FileReader(Property.get("OBSERVER_LIST_FILE")));
			logger.debug("Using file: " + Property.get("OBSERVER_LIST_FILE"));
		} catch (FileNotFoundException e) {
			throw new InitializationFailedException("The list of observers could not be found: " + Property.get("OBSERVER_LIST_FILE"));
		}
		String readLine;
		do {
			try {
				readLine = aBufferedReader.readLine();
				if (readLine == null)
					break;
			} catch (IOException e) {
				throw new InitializationFailedException("Error while reading the observer list file: " + e.getMessage());
			}
			if ("".equals(readLine.trim()) || readLine.matches("^#.*$"))
				continue;
			String sWADL = readLine;
			if (!readLine.matches(".*\\.wadl$")) {
				sWADL += "/" + Property.get("OBSERVER_XML_FILENAME");
			}
			logger.debug("- Loading observer contract: " + sWADL);
			Observer obs = new Observer();
			WADLUnmarshaller unmarshaller;
			try {
				unmarshaller = new WADLUnmarshallerXPath();
				unmarshaller.addURL(new URL(sWADL));
				unmarshaller.unmarshal();
			} catch (MalformedURLException e) {
				logger.error("Invalid observer contract URL \"" + sWADL + "\". Check the observers list file.", e);
				logger.warn("> This observer will be skiped");
				continue;
			} catch (ParserConfigurationException e) {
				throw new InitializationFailedException("ParserConfigurationException: " + e.getMessage());
			} catch (IOException e) {
				logger.error("Unable to read observer contract: " + sWADL, e);
				logger.warn("> This observer will be skiped");
				continue;
			} catch (Exception e) {
				logger.error("Error unmarshalling contract: " + sWADL, e);
				logger.warn("> This observer will be skiped");
				continue;
			}
			try {
				obs.setResponseType(unmarshaller.getResponseType());
			} catch (UnknownParserException e) {
				logger.error("Unknown parser: " + unmarshaller.getResponseType() + ". Check observer contract or responseParsers.properties.", e);
				logger.warn("> This observer will be skiped");
				continue;
			}
			obs.setListOfCallMethod(unmarshaller.getListOfCallMethod());
			obs.setParamLangName(unmarshaller.getNameOfLangParameter());
			obs.setID(unmarshaller.getID());
			obs.setName(unmarshaller.getName());
			obs.setDescription(unmarshaller.getDescription());
			obs.setHelpLocation(unmarshaller.getHelpLocation());
			obs.setProvider(unmarshaller.getProvider());
			obs.setMapOfInputMethod(unmarshaller.getMapOfInputMethod());
			obs.setSupportedMimeTypes(unmarshaller.getSupportedMimeTypes());
			mapOfObserver.put(new String(obs.getID()), obs);
		} while (readLine != null);
		if (mapOfObserver.size() == 0) {
			throw new InitializationFailedException("There is no observer loaded. Check the observers list file.");
		} else {
			logger.info("OK - " + mapOfObserver.size() + " observer(s) successfully loaded.");
		}
	}
	public static void initTasklists() throws InitializationFailedException {	
		logger.debug("-------------------------------------------------------");
		logger.debug("Loading xml task files from tasklist directory: " + Property.get("PATH_TO_TASKLIST"));
		TasksListUnmarshaller aTaskListUnmarshaller = new TaskListUnmarshallerBeans(mapOfObserver);
		File[] tFileXML = ListFiles.listFiles(Property.get("PATH_TO_TASKLIST"), "\\.xml$");
		for (File aFile : tFileXML) {
			try {
				logger.debug("- Loading xml file: " + aFile.getName());
				aTaskListUnmarshaller.addURL(aFile.toURI().toURL());
				aTaskListUnmarshaller.unmarshal();
			} catch (MalformedURLException e) {
				logger.error(e.getMessage(), e);
			} catch (IOException e) {
				logger.error("Error reading file: " + aFile.getName(), e);
				logger.warn("> This task file will be skiped");
			} catch (Exception e) {
				logger.error("Error unmarshalling file: " + aFile.getName(), e);
				logger.warn("> This task file will be skiped");
			}
		}
		logger.debug("-------------------------------------------------------");
		logger.debug("Loading rdf task files from tasklist directory: " + Property.get("PATH_TO_TASKLIST"));
		File[] tFileRDF = ListFiles.listFiles(Property.get("PATH_TO_TASKLIST"), "\\.rdf$");
		RDFUnmarshaller aRDFUnmarshaller = new RDFUnmarshallerJena();
		aRDFUnmarshaller.setMapOfTask(aTaskListUnmarshaller.getMapOfTask());
		for (final File aFile : tFileRDF) {
			try {
				logger.debug("- Loading rdf file: " + aFile.getName());
				aRDFUnmarshaller.addURL(aFile.toURI().toURL());
				aRDFUnmarshaller.unmarshal();
			} catch (MalformedURLException e) {
				logger.error(e.getMessage(), e);
			} catch (IOException e) {
				logger.error("Error reading file: " + aFile.getName(), e);
				logger.warn("> This task file will be skiped");
			} catch (Exception e) {
				logger.error("Error unmarshalling file: " + aFile.getName(), e);
				logger.warn("> This task file will be skiped");
			}
		}
		mapOfTask = aTaskListUnmarshaller.getMapOfTask();
		for (Object key : mapOfTask.keySet()) {
			Task task = mapOfTask.get(key.toString());
			String defaultLang = Property.get("DEFAULT_LANGUAGE");
			if (task.getLongName().getLocalization(defaultLang) == null) {
				task.getLongName().addLocalization(defaultLang, key.toString());
				logger.warn("Missing default language long name for task: " + key + ". Long name will be the task id.");
			}
		}
		if (mapOfTask.size() == 0) {
			throw new InitializationFailedException("No task have been loaded. Check task files in: " + Property.get("PATH_TO_TASKLIST"));
		} else {
			String s = "Map of tasks:";
			logger.debug(s + mapOfTask);
			logger.info("OK - " + mapOfTask.size() + " task(s) successfully loaded.");
		}
	}
	public static void initLanguages() throws InitializationFailedException {	
		// Loading language files
		logger.debug("-------------------------------------------------------");
		logger.debug("Loading language files from language directory: " + Property.get("PATH_TO_LANGUAGE_FILES"));
		if (!Language.isISOLanguageCode(Property.get("DEFAULT_LANGUAGE"))) {
			throw new InitializationFailedException("Property DEFAULT_LANGUAGE is not a valid ISO639 code: " + Property.get("DEFAULT_LANGUAGE"));
		}
		
		File defaultLanguageFile = new File(Property.get("PATH_TO_LANGUAGE_FILES", "DEFAULT_LANGUAGE") + ".properties");
		Properties defaultProps = new Properties();
		
		try {
			defaultProps = Language.load(defaultLanguageFile);
			logger.debug("> Found language (default): " + defaultProps.getProperty("lang") + " - " + defaultProps.getProperty("language"));
			defaultProps.put("complete", "true");
			languageProperties.put(Property.get("DEFAULT_LANGUAGE"), defaultProps);
		} catch (IllegalArgumentException e) {
			logger.warn(e.getMessage());
		} catch (FileNotFoundException e) {
			throw new InitializationFailedException("Default language file does not exists: " + defaultLanguageFile.getPath());
		} catch (IOException e) {
			throw new InitializationFailedException("Unable to read default language file. " + defaultLanguageFile.getPath());
		}
		
		
		File[] languageFiles = ListFiles.listFiles(Property.get("PATH_TO_LANGUAGE_FILES"), "\\.properties$");
		
		for (File langFile : languageFiles) {
			if (langFile.equals(defaultLanguageFile))
				continue;
			try {
				Properties props = Language.load(langFile);
				logger.debug("> Found language: " + props.getProperty("lang") + " - " + props.getProperty("language"));
				Language.complete(props, defaultProps);
				languageProperties.put(props.getProperty("lang"), props);
			} catch (IllegalArgumentException e) {
				logger.warn(e.getMessage());
			} catch (FileNotFoundException e) {
				// Should not happen
				logger.error(e.getMessage(), e);
			} catch (IOException e) {
				logger.error("Unable to read language file. " + langFile + ". This file will be skiped.");
			}
		}
		
		if (languageProperties.size() == 0) {
			throw new InitializationFailedException("No language have been loaded. Check language files in: " + Property.get("PATH_TO_LANGUAGE_FILES"));
		} else {
			String s = "Language properties:";
			for (String key : languageProperties.keySet()) {
				s += "\n\n\t" + languageProperties.get(key).getProperty("language") + ":";
				for (Object langKey : languageProperties.get(key).keySet()) {
					s += "\n\t\t" + langKey + " => " + languageProperties.get(key).getProperty((String) langKey); 
				}
			}
			logger.debug(s);
			logger.info("OK - " + languageProperties.size() + " language(s) successfully loaded.");
		}
		for (String key : languageProperties.keySet()) {
			languages.put(key, languageProperties.get(key).getProperty("language"));
		}
	}	
	public static void initVelocity() throws InitializationFailedException {	
		// Creating velocity contexts
		logger.debug("-------------------------------------------------------");
		logger.debug("Initializing Velocity");
		for (String locale : languageProperties.keySet()) {
			VelocityContext context = new VelocityContext();
			Properties langProps = languageProperties.get(locale);
			for (Object key : langProps.keySet()) {
		    	context.put((String) key, langProps.get(key));
		    }
			context.put("tasklist", mapOfTask);
			context.put("param_prefix", Property.get("UNICORN_PARAMETER_PREFIX"));
			context.put("languages", languages);
			languageContexts.put(locale, context);
		}
		logger.debug("> "+languageContexts.size()+" velocity context(s) created");
		
		// Creating velocity engine
		velocityEngine = new VelocityEngine();
		Properties bProperties = Property.getProps("velocity.properties");
		bProperties.put(Velocity.FILE_RESOURCE_LOADER_PATH,
				Property.get("PATH_TO_TEMPLATES") + "," + 
				Property.get("PATH_TO_TEMPLATES")+"includes/");
		logger.debug("> Initializing velocity engine with FILE_RESOURCE_LOADER_PATH: " + Property.get("PATH_TO_TEMPLATES"));
		try {
			velocityEngine.init(bProperties);
			logger.debug("> Velocity engine successfully initialized");
		} catch (Exception e) {
			throw new InitializationFailedException("Error instanciating velocity engine: " + e.getMessage());
		}
		
		logger.info("OK - Velocity successfully initialized");
		
		Framework.logger.info("Unicorn initialized successfully.");
		isUcnInitialized = true;
	}
	
	private static void loadConfigFile(String path, boolean addUnicornHome) throws FileNotFoundException, IOException {		
		UCNProperties properties = new UCNProperties();
		File configFile = new File(path);
		String fileName = configFile.getName();
		if (fileName != null) {
			logger.debug("Loading config file: " + fileName);
			if (addUnicornHome)
				properties.put("UNICORN_HOME", unicornHome.getPath());
			properties.load(new FileInputStream(configFile));
			unicornPropertiesFiles.put(fileName, properties);
			logger.debug("> " + fileName + ":" + properties);
		}
	}
	
	public static Hashtable<String, Properties> getUnicornPropertiesFiles() {
		return unicornPropertiesFiles;
	}
	public static Hashtable<String, VelocityContext> getLanguageContexts() {
		return languageContexts;
	}
	public static VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}
	public static Hashtable<String, Properties> getLanguageProperties() {
		return languageProperties;
	}
}
