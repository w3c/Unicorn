// Author: Damien LEROY & Thomas GAMBET.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.EscapeTool;
import org.apache.velocity.tools.generic.MathTool;
import org.w3.unicorn.tasklist.TUi;
import org.w3c.unicorn.action.LanguageAction;
import org.w3c.unicorn.contract.Observer;
import org.w3c.unicorn.contract.WADLUnmarshaller;
import org.w3c.unicorn.contract.WADLUnmarshallerXPath;
import org.w3c.unicorn.exceptions.InitializationFailedException;
import org.w3c.unicorn.exceptions.UnknownParserException;
import org.w3c.unicorn.tasklist.Task;
import org.w3c.unicorn.tasklist.TaskListUnmarshallerBeans;
import org.w3c.unicorn.tasklist.Tasklist;
import org.w3c.unicorn.tasklist.TasksListUnmarshaller;
import org.w3c.unicorn.tasklist.parameters.Parameter;
import org.w3c.unicorn.tasklist.parameters.Value;
import org.w3c.unicorn.util.Language;
import org.w3c.unicorn.util.ListFiles;
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.util.UCNProperties;
import org.w3c.unicorn.response.Response;

import com.ibm.icu.util.ULocale;

/**
 * Main class of the central module of UniCORN.
 * 
 * @author Damien LEROY & Thomas GAMBET
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
	public static Map<String, Class<Response>> responseImpl;
	
	/**
	 * Logger
	 */
	public static Log logger = LogFactory.getLog(Framework.class);
	
	/**
	 * True if initialization did not throw any exception
	 */
	public static boolean isUcnInitialized;
	private static Hashtable<String, UCNProperties> unicornPropertiesFiles;
	private static Hashtable<ULocale, VelocityContext> languageContexts;
	private static Hashtable<ULocale, UCNProperties> languageProperties;
	private static Hashtable<ULocale, UCNProperties> metadataProperties;
	private static VelocityEngine velocityEngine;
	private static String[] configFiles = {
		"extensions.properties",
		"responseImpl.properties",
		"output.properties",
		"velocity.properties",
		"mail.properties",
		"observers.properties"};

	public static void reset() {
		unicornPropertiesFiles = new Hashtable<String, UCNProperties>();
		languageContexts = new Hashtable<ULocale, VelocityContext>();
		languageProperties = new Hashtable<ULocale, UCNProperties>();
		metadataProperties = new Hashtable<ULocale, UCNProperties>();
		mapOfObserver = new LinkedHashMap<String, Observer>();
		responseImpl = new LinkedHashMap<String, Class<Response>>();
		Language.reset();
	}
	
	/**
	 * Initialize Unicorn
	 */
	public static void initUnicorn() {
		isUcnInitialized = false;
		reset();
		try {
			initCore();
			initConfig();
			initUnmarshallers();
			initResponseImplementations();
			initObservers();
			initLanguages();
			initTasklists();
			initVelocity();
			isUcnInitialized = true;
			logger.info("Unicorn initialized successfully.");
		} catch (InitializationFailedException e) {
			logger.fatal(e.getMessage(), e);
			System.err.println(e.getMessage());
		}
	}
	
	public static void initCore() throws InitializationFailedException {
		try {
			URL classesDir = Framework.class.getResource("/");
			File classes = new File(classesDir.toURI());
			File webInf = new File(classes.getParent());
			System.setProperty("unicorn.home", webInf.getParent());
		} catch (URISyntaxException e) {
			throw new InitializationFailedException(e.getMessage(), e);
		}
		
		// Log4j initialization attempt
		URL log4jURL = Framework.class.getResource("/log4j.xml");
		if (log4jURL != null) {
			DOMConfigurator.configure(log4jURL);
			logger.info("OK - Log4j successfully initialized");
			logger.debug("> Used log4j.xml file: " + log4jURL.toString());
		} else {
			logger.warn("Log4j config file \"log4j.xml\" could not be found in classpath.");
			logger.warn("Log4j will not be initialized");
		}
	}
	
	public static void initConfig() throws InitializationFailedException {
		// Load unicorn.properties	
		logger.debug("-------------------------------------------------------");
		InputStream uniconfStream = Framework.class.getResourceAsStream("/unicorn.properties");
		if (uniconfStream == null)
			throw new InitializationFailedException("Unicorn config file \"unicorn.properties\" could not be found in classpath.");
		try {
			String[] uniHome = {"UNICORN_HOME", System.getProperty("unicorn.home")};
			loadConfigFile(uniconfStream, "unicorn.properties", uniHome);
			logger.info("OK - Config file unicorn.properties successfully loaded");
		} catch (IOException e) {
			throw new InitializationFailedException("Error reading \"unicorn.properties\": " + e.getMessage());
		} 

		// Loading other config files
		for (String fileName : configFiles) {
			InputStream confStream = Framework.class.getResourceAsStream("/" + fileName);
			logger.debug("-------------------------------------------------------");
			if (confStream == null)
				throw new InitializationFailedException("Mandatory config file \"" + fileName + "\" could not be found in classpath.");
			try {
				loadConfigFile(confStream, fileName);
				logger.info("OK - Config file " + fileName + " successfully loaded");
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
	}
	
	@SuppressWarnings("unchecked")
	public static void initResponseImplementations() throws InitializationFailedException {
	    // Load the map of Response implementations
		logger.debug("-------------------------------------------------------");
		logger.debug("Loading available response implementations form responseImpl.properties");
		Properties aProperties = Property.getProps("responseImpl.properties");
		for (Object key : aProperties.keySet()) {
			String className = aProperties.getProperty(key.toString());
			try {
				if (Response.class.isAssignableFrom(Class.forName(className))) {
					Class.forName(className).getConstructor(InputStream.class, String.class, String.class);
					responseImpl.put(key.toString(), (Class<Response>) Class.forName(className));
					logger.debug("> Implementation loaded: " + responseImpl.get(key).getClass().toString());
				} else {
					logger.error("> Class: " + className + " is not a Response implementation.");
				}
			} catch (ClassNotFoundException e) {
				logger.error("Class not found: " + className + ". Check responseImpl.properties.");
			} catch (NoSuchMethodException e) {
				logger.error("Response implementation: " + className + " does not have a constructor with signature (InputStream is, String charset, String observerId). Implementation skipped.");
			} catch (Exception e) {
				logger.error("Error trying to instanciate: " + className, e);
			}
		}
		if (responseImpl.size() == 0) {
			throw new InitializationFailedException("There is no response implementation loaded. Check responseImpl.properties.");
		} else {
			logger.info("OK - " + responseImpl.size() + " implementation(s) successfully loaded.");
		}
	}
	
	public static void initObservers() throws InitializationFailedException {
		// Loading observers
		logger.debug("-------------------------------------------------------");
		logger.debug("Loading available observers from the observers.properties");
		Properties observers = Property.getProps("observers.properties");
		for (Object key : observers.keySet()) {
			String observerId = key.toString();
			String observerContract = observers.getProperty(key.toString());
			if (!observerContract.matches(".*\\.wadl$"))
				observerContract += "/" + Property.get("OBSERVER_DEFAULT_FILENAME");
			logger.debug("- Loading observer contract: " + observerContract);
			Observer obs = new Observer();
			WADLUnmarshaller unmarshaller;
			try {
				unmarshaller = new WADLUnmarshallerXPath();
				unmarshaller.addURL(new URL(observerContract));
				unmarshaller.unmarshal();
			} catch (MalformedURLException e) {
				logger.error("Invalid observer contract URL \"" + observerContract + "\". Check the observers list file.", e);
				logger.warn("> This observer will be skiped");
				continue;
			} catch (ParserConfigurationException e) {
				throw new InitializationFailedException("ParserConfigurationException: " + e.getMessage());
			} catch (IOException e) {
				logger.error("Unable to read observer contract: " + observerContract, e);
				logger.warn("> This observer will be skiped");
				continue;
			} catch (Exception e) {
				logger.error("Error unmarshalling contract: " + observerContract, e);
				logger.warn("> This observer will be skiped");
				continue;
			}
			try {
				obs.setResponseType(unmarshaller.getResponseType());
			} catch (UnknownParserException e) {
				logger.error("Unknown implementation: " + unmarshaller.getResponseType() + ". Check observer contract or responseImpl.properties.", e);
				logger.warn("> This observer will be skiped");
				continue;
			}
			obs.setListOfCallMethod(unmarshaller.getListOfCallMethod());
			obs.setParamLangName(unmarshaller.getNameOfLangParameter());
			obs.setParamOutputName(unmarshaller.getNameOfOutputParameter());
			obs.setID(observerId);
			obs.setName(unmarshaller.getName());
			obs.setDescription(unmarshaller.getDescription());
			obs.setHelpLocation(unmarshaller.getHelpLocation());
			obs.setProvider(unmarshaller.getProvider());
			obs.setMapOfInputMethod(unmarshaller.getMapOfInputMethod());
			obs.setSupportedMimeTypes(unmarshaller.getSupportedMimeTypes());
			obs.setIndexURI(unmarshaller.getIndexUri());
			mapOfObserver.put(new String(obs.getID()), obs);
		}
		if (mapOfObserver.size() == 0) {
			throw new InitializationFailedException("There is no observer loaded. Check the observers list file.");
		} else {
			logger.info("OK - " + mapOfObserver.size() + " observer(s) successfully loaded.");
		}
	}
	
	public static void initLanguages() throws InitializationFailedException {	
		
		// Loading language files
		logger.debug("-------------------------------------------------------");
		logger.debug("Loading language files from language directory: " + Property.get("PATH_TO_LANGUAGE_FILES"));
		ULocale defaultLocale = null;
		for (ULocale locale : ULocale.getAvailableLocales()) {
			if (locale.getName().equals(Property.get("DEFAULT_LANGUAGE"))) {
				defaultLocale = locale;
				break;
			}
		}
		
		if (defaultLocale == null)
			throw new InitializationFailedException("Locale not found for default language in unicorn.properties: " + Property.get("DEFAULT_LANGUAGE"));
		
		Language.reset();
		Language.initLocaleMatcher(defaultLocale);	
		
		File defaultLanguageFile = new File(Property.get("PATH_TO_LANGUAGE_FILES", "DEFAULT_LANGUAGE") + ".properties");
		UCNProperties defaultProps = new UCNProperties();
		try {
			defaultProps = Language.load(defaultLanguageFile);
			logger.debug("> Found language (default): " + defaultLocale.getName() + " - " + defaultLocale.getDisplayName(defaultLocale));
			Language.addUiLocale(defaultLocale);
			LanguageAction.addLanguageProperties(defaultLocale, defaultProps);
			defaultProps.parse();
			defaultProps.put("complete", "true");
			languageProperties.put(defaultLocale, defaultProps);
			UCNProperties metaProps = new UCNProperties();
			LanguageAction.addMetadatasProperties(defaultLocale, metaProps);
			metadataProperties.put(defaultLocale, metaProps);
		} catch (IllegalArgumentException e) {
			logger.warn(e.getMessage());
		} catch (FileNotFoundException e) {
			throw new InitializationFailedException("Default language file does not exist: " + defaultLanguageFile.getPath());
		} catch (IOException e) {
			throw new InitializationFailedException("Unable to read default language file " + defaultLanguageFile.getPath());
		}
		
		File[] languageFiles = ListFiles.listFiles(Property.get("PATH_TO_LANGUAGE_FILES"), "\\.properties$");
		
		for (File langFile : languageFiles) {
			if (langFile.equals(defaultLanguageFile))
				continue;
			try {
				ULocale fileLocale = Language.getLocaleFromFileName(langFile.getName());
				UCNProperties props = Language.load(langFile);
				logger.debug("> Found language: " + fileLocale.getName() + " - " + fileLocale.getDisplayName(Language.getDefaultLocale()));
				Language.addUiLocale(fileLocale);
				Language.clean(props, defaultProps, langFile.getName());
				LanguageAction.addLanguageProperties(fileLocale, props);
				Language.complete(props, defaultProps, langFile.getName());
				props.parse();
				languageProperties.put(fileLocale, props);
				UCNProperties metaProps = new UCNProperties();
				LanguageAction.addMetadatasProperties(fileLocale, metaProps);
				metadataProperties.put(fileLocale, metaProps);
			} catch (IllegalArgumentException e) {
				logger.warn(e.getMessage());
			} catch (FileNotFoundException e) {
				// Should not happen
				logger.error(e.getMessage(), e);
			} catch (IOException e) {
				logger.error("Unable to read language file " + langFile + ". This file will be skiped.");
			}
		}
		
		Language.initUILocaleMatcher();	
		
		if (languageProperties.size() == 0) {
			throw new InitializationFailedException("No language have been loaded. Check language files in: " + Property.get("PATH_TO_LANGUAGE_FILES"));
		} else {
			String s = "Language properties:";
			for (ULocale localeKey : languageProperties.keySet()) {
				Properties props = languageProperties.get(localeKey);
				s += "\n\n\t" + localeKey.getDisplayName(Language.getDefaultLocale()) + ":";
				for (Object langKey : props.keySet()) {
					s += "\n\t\t" + langKey + " => " + props.getProperty((String) langKey); 
				}
			}
			logger.debug(s);
			logger.info("OK - " + languageProperties.size() + " language(s) successfully loaded.");
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
		mapOfTask = aTaskListUnmarshaller.getMapOfTask();
		if (mapOfTask.size() == 0) {
			throw new InitializationFailedException("No task have been loaded. Check task files in: " + Property.get("PATH_TO_TASKLIST"));
		} else {
			String s = "Map of tasks:";
			logger.debug(s + mapOfTask);
			logger.info("OK - " + mapOfTask.size() + " task(s) successfully loaded.");
		}
		
		logger.debug("-------------------------------------------------------");
		logger.debug("Loading tasks metadata files from tasks language directory: " + Property.get("PATH_TO_TASK_LANGUAGE_FILES"));
		
		File defaultTaskFile = new File(Property.get("PATH_TO_TASK_LANGUAGE_FILES", "DEFAULT_LANGUAGE") + ".tasklist.properties");
		String defaultLang = Language.getDefaultLocale().getName();
		
		UCNProperties defaultProps = null;
		try{
			defaultProps = Language.load(defaultTaskFile);
			defaultProps.parse();
			logger.debug("> Found default tasks metadata file: " + defaultTaskFile.getPath());
			LanguageAction.addMetadatasProperties(Language.getDefaultLocale(), defaultProps);
			metadataProperties.put(Language.getDefaultLocale(), defaultProps);
			for (String taskKey : mapOfTask.keySet()) {
				Task task = mapOfTask.get(taskKey);
				if (defaultProps.containsKey(taskKey)) 
					task.addLongName(defaultLang, defaultProps.getProperty(taskKey));
				else
					logger.warn(">> No default longname found for the task: " + taskKey + ". This task longname will be its key and it won't appear in the translation form.");
				if (defaultProps.containsKey(taskKey + ".description"))
					task.addDescription(defaultLang, defaultProps.getProperty(taskKey + ".description"));
				else
					logger.warn(">> No default description found for the task: " + taskKey + ". This task description will be empty and it won't appear in the translation form.");
				
				Map<String, Parameter> params = task.getMapOfParameter();
				for (Object paramKey : params.keySet()) {
					Parameter param = (Parameter) params.get(paramKey);
					if (defaultProps.containsKey(taskKey + ".param." + paramKey))
						param.addLongName(defaultLang, defaultProps.getProperty(taskKey + ".param." + paramKey));
					else if (defaultProps.containsKey("param." + paramKey))
						param.addLongName(defaultLang, defaultProps.getProperty("param." + paramKey));
					else
						logger.warn(">> No default parameter longname found for the parameter: " + paramKey + ". This parameter longname will be its key and it won't appear in the translation form.");
					
					Map<String, Value> values = param.getMapOfValue();
					for (Object valueKey : values.keySet()) {
						Value value = (Value) values.get(valueKey);
						if (defaultProps.containsKey(taskKey + ".param." + paramKey + "." + valueKey))
							value.addLongName(defaultLang, defaultProps.getProperty(taskKey + ".param." + paramKey + "." + valueKey));
						else if (defaultProps.containsKey("param." + paramKey + "." + valueKey))
							value.addLongName(defaultLang, defaultProps.getProperty("param." + paramKey + "." + valueKey));
						else if (param.getUiLevel() != TUi.NONE) // Warn only if the value is displayed
							logger.warn(">> No default value longname found for the value \"" + valueKey + "\" for the parameter \"" + paramKey + "\" from the task " + taskKey + ". This value longname will be its key and it won't appear in the translation form.");
					}
				}
			}
		} catch (IllegalArgumentException e) {
			logger.warn(e.getMessage());
		} catch (FileNotFoundException e) {
			throw new InitializationFailedException("Default tasks metadata file does not exist: " + defaultTaskFile.getPath());
		} catch (IOException e) {
			throw new InitializationFailedException("Unable to read default tasks metadata file " + defaultTaskFile);
		}
		
		File[] taskFiles = ListFiles.listFiles(Property.get("PATH_TO_TASK_LANGUAGE_FILES"), "\\.tasklist.properties$");
		
		for (File taskFile : taskFiles) {
			if (taskFile.equals(defaultTaskFile))
				continue;
			try {
				ULocale fileLocale = Language.getLocaleFromFileName(taskFile.getName());
				String lang = fileLocale.getName();
				UCNProperties props = Language.load(taskFile);
				props.parse();
				logger.debug("> Found tasks metadata file: " + taskFile.getPath());
				
				ArrayList<Object> keys = new ArrayList<Object>();
				for (Object key : props.keySet()) {
					if (defaultProps != null && !defaultProps.containsKey(key)) {
						keys.add(key);
						logger.warn(">> Unknown key \"" + key + "\" found in " + taskFile.getPath() + ". Please remove this key manually from the tasklist properties file.");
					}
				}
				for (Object key : keys)
					props.remove(key);
				
				LanguageAction.addMetadatasProperties(fileLocale, props);
				metadataProperties.put(fileLocale, props);
				
				for (String taskKey : mapOfTask.keySet()) {
					Task task = mapOfTask.get(taskKey);
					if (props.containsKey(taskKey)) 
						task.addLongName(lang, props.getProperty(taskKey));
					if (props.containsKey(taskKey + ".description"))
						task.addDescription(lang, props.getProperty(taskKey + ".description"));
					
					Map<String, Parameter> params = task.getMapOfParameter();
					for (Object paramKey : params.keySet()) {
						Parameter param = (Parameter) params.get(paramKey);
						if (props.containsKey(taskKey + ".param." + paramKey))
							param.addLongName(lang, props.getProperty(taskKey + ".param." + paramKey));
						else if (props.containsKey("param." + paramKey))
							param.addLongName(lang, props.getProperty("param." + paramKey));
						
						Map<String, Value> values = param.getMapOfValue();
						for (Object valueKey : values.keySet()) {
							Value value = (Value) values.get(valueKey);
							if (props.containsKey(taskKey + ".param." + paramKey + "." + valueKey))
								value.addLongName(lang, props.getProperty(taskKey + ".param." + paramKey + "." + valueKey));
							else if (props.containsKey("param." + paramKey + "." + valueKey))
								value.addLongName(lang, props.getProperty("param." + paramKey + "." + valueKey));
						}
					}
				}
			} catch (IllegalArgumentException e) {
				logger.warn(e.getMessage());
			} catch (FileNotFoundException e) {
				// Should not happen
				logger.error(e.getMessage(), e);
			} catch (IOException e) {
				logger.error("Unable to read task file " + taskFile + ". This file will be skiped.");
			}
		}
	}
	
	public static void initVelocity() throws InitializationFailedException {	

		logger.debug("-------------------------------------------------------");
		logger.debug("Initializing Velocity");
		
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
		
		// Creating velocity contexts
		for (ULocale locale : languageProperties.keySet()) {
			VelocityContext context = new VelocityContext();
			Properties langProps = languageProperties.get(locale);
			for (Object key : langProps.keySet()) {
		    	context.put((String) key, langProps.get(key));
		    }
			context.put("esc", new EscapeTool());
			context.put("math", new MathTool());
			context.put("ucn", new Language());
			context.put("strUtils", new StringUtils());
			context.put("tasklist", mapOfTask);
			context.put("param_prefix", Property.get("UNICORN_PARAMETER_PREFIX"));
			context.put("languages", Language.getUiLocales());
			context.put("lang", locale.getName());
			context.put("direction", Language.getLocaleDirection(locale));
			context.put("defaultLocale", Language.getDefaultLocale());
			languageContexts.put(locale, context);
		}
		logger.debug("> " + languageContexts.size() + " velocity context(s) created");
		
		logger.info("OK - Velocity successfully initialized");
	}
	
	private static void loadConfigFile(InputStream stream, String fileName, String[]... parameters) throws IOException {		
		UCNProperties properties = new UCNProperties();
		logger.debug("Loading config file: " + fileName);
		for (String[] param : parameters) {
			if (param.length == 2 && param[1] != null)
				properties.put(param[0], param[1]);
		}
		properties.load(stream);
		properties.parse();
		unicornPropertiesFiles.put(fileName, properties);
		logger.debug("> " + fileName + ":" + properties);
	}
	
	public static Hashtable<String, UCNProperties> getUnicornPropertiesFiles() {
		return unicornPropertiesFiles;
	}
	public static Hashtable<ULocale, VelocityContext> getLanguageContexts() {
		return languageContexts;
	}
	public static VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}
	public static Hashtable<ULocale, UCNProperties> getLanguageProperties() {
		return languageProperties;
	}
	public static Hashtable<ULocale, UCNProperties> getMetadataProperties() {
		return metadataProperties;
	}
	public static Task getDefaultTask() {
		return mapOfTask.getDefaultTask();
	}

}
