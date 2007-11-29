// $Id: Framework.java,v 1.3 2007-11-29 14:11:59 dtea Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.contract.Observer;
import org.w3c.unicorn.contract.RDFContractUnmarshaller;
import org.w3c.unicorn.contract.RDFContractUnmarshallerJena;
import org.w3c.unicorn.contract.WADLUnmarshaller;
import org.w3c.unicorn.contract.WADLUnmarshallerXPath;
import org.w3c.unicorn.tasklist.RDFUnmarshaller;
import org.w3c.unicorn.tasklist.RDFUnmarshallerJena;
import org.w3c.unicorn.tasklist.Task;
import org.w3c.unicorn.tasklist.TasksListUnmarshaller;
import org.w3c.unicorn.tasklist.TasksListUnmarshallerJAXB;
import org.w3c.unicorn.util.ListFiles;
import org.w3c.unicorn.util.LocalizedString;
import org.w3c.unicorn.util.Property;
import org.xml.sax.SAXException;

/**
 * Main class of the central module of UniCORN.
 * @author Damien LEROY
 */
public class Framework {

	/**
	 * Instance of log class to, as his name say, log any information during execution.
	 */
	public static final Log logger = LogFactory.getLog(Framework.class);

	public static Map<String, Observer> mapOfObserver = null;

	public static Map<String, Task> mapOfTask = null;

	public static Properties aPropertiesExtension;

	static {
		// Load the list of extensions
		try {
			final URL aURLPropFile = Framework.class.getResource("extensions.properties");			
			final Properties aProperties = new Properties();
			aProperties.load(aURLPropFile.openStream());
			Framework.aPropertiesExtension = aProperties;
		} catch (final IOException e) {
			Framework.logger.error("IOException : "+e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Statics operations to initialise framework the first time this class is used.
	 */
	static {
		if (Framework.logger.isDebugEnabled()) {
			Framework.logger.debug("Observer initialisation.");
		}
		//final String sLanguage = Property.get("DEFAULT_LANGUAGE");
		final String sLanguage = Property.get("DEFAULT_LANGUAGE");
		if (null != sLanguage) {
			LocalizedString.DEFAULT_LANGUAGE = sLanguage;
		}
		Framework.mapOfObserver = new LinkedHashMap<String, Observer>();
		try {			
			// Add all observer contract
			final BufferedReader aBufferedReader;
			aBufferedReader = new BufferedReader(new FileReader(Property.get("OBSERVER_LIST_FILE")));
			for (
					String sReadLine = aBufferedReader.readLine();
					null != sReadLine;
					sReadLine = aBufferedReader.readLine()) {
				if ("".equals(sReadLine.trim())) {
					continue;
				}
				final String sWADL;
				final String sRDF;
				String[] tString = sReadLine.split(" ");
				if (1 == tString.length) {
					sWADL = tString[0] + "/" + Property.get("OBSERVER_XML_FILENAME");
					sRDF = tString[0] + "/" + Property.get("OBSERVER_RDF_FILENAME");
				} else {
					sWADL = tString[0];
					sRDF = tString[1];
				}
				try {
					if (Framework.logger.isDebugEnabled()) {
						Framework.logger.debug("Observer WADL file : "+sWADL+".");
						Framework.logger.debug("Observer RDF file : "+sRDF+".");
					}

					try {
						final Observer aObserver = new Observer();
						final WADLUnmarshaller aWADLUnmarshaller = new WADLUnmarshallerXPath();
						aWADLUnmarshaller.addURL(new URL(sWADL));
						aWADLUnmarshaller.unmarshal();
						aObserver.setListOfCallMethod(aWADLUnmarshaller.getListOfCallMethod());
						final RDFContractUnmarshaller aRDFContractUnmarshaller;
						aRDFContractUnmarshaller = new RDFContractUnmarshallerJena(aObserver.getListOfCallMethod());
						aRDFContractUnmarshaller.addURL(new URL(sRDF));
						aRDFContractUnmarshaller.unmarshal();
						//this.aObserverDescription = aRDFUnmarshaller.getDescription();
						aObserver.setID(aRDFContractUnmarshaller.getID());
						aObserver.setName(aRDFContractUnmarshaller.getName());
						aObserver.setDescription(aRDFContractUnmarshaller.getDescription());
						aObserver.setHelpLocation(aRDFContractUnmarshaller.getHelpLocation());
						aObserver.setProvider(aRDFContractUnmarshaller.getProvider());
						aObserver.setMapOfInputMethod(aRDFContractUnmarshaller.getMapOfInputMethod());
						Framework.mapOfObserver.put(new String(aObserver.getID()), aObserver);
					}
					catch (final ParserConfigurationException e) {
						Framework.logger.error("ParserConfigurationException : "+e.getMessage(), e);
						e.printStackTrace();
					}
					catch (final XPathExpressionException e) {
						Framework.logger.error("XPathExpressionException : "+e.getMessage(), e);
						e.printStackTrace();
					}
					catch (final SAXException e) {
						Framework.logger.error("SAXException : "+e.getMessage(), e);
						e.printStackTrace();
					}
					catch (final IOException e) {
						Framework.logger.error("IOException : "+e.getMessage(), e);
						e.printStackTrace();
					}
					catch (final URISyntaxException e) {
						Framework.logger.error("URISyntaxException : "+e.getMessage(), e);
						e.printStackTrace();
					}
				} catch (final Exception aException) {
					aException.printStackTrace();
					Framework.logger.error("Exception : "+aException.getMessage(), aException);
				}
			}
		} catch (final FileNotFoundException aFileNotFoundException) {
			Framework.logger.error(
					"FileNotFoundException : "+aFileNotFoundException.getMessage(),
					aFileNotFoundException);
			aFileNotFoundException.printStackTrace();
		} catch (final IOException ioe) {
			Framework.logger.error("IOException : "+ioe.getMessage(), ioe);
			ioe.printStackTrace();
		}

		if (Framework.logger.isDebugEnabled()) {
			Framework.logger.debug("Task initialisation.");
		}
		//Framework.mapOfTask = new LinkedHashMap<String, Task>();
		try {
			// TODO parser tout les fichiers de taches

			final File[] tFileXML = ListFiles.listFiles(Property.get("PATH_TO_TASKLIST"), "\\.xml");
			final TasksListUnmarshaller aTaskListUnmarshaller =
				new TasksListUnmarshallerJAXB(Framework.mapOfObserver);
			for (final File aFile : tFileXML) {
				aTaskListUnmarshaller.addURL(aFile.toURL());
				aTaskListUnmarshaller.unmarshal();
			}

			final File[] tFileRDF = ListFiles.listFiles(Property.get("PATH_TO_TASKLIST"), "\\.rdf");
			final RDFUnmarshaller aRDFUnmarshaller = new RDFUnmarshallerJena();
			aRDFUnmarshaller.setMapOfTask(aTaskListUnmarshaller.getMapOfTask());
			aRDFUnmarshaller.setMapOfObserver(Framework.mapOfObserver);
			for (final File aFile : tFileRDF) {
				aRDFUnmarshaller.addURL(aFile.toURL());
			}
			aRDFUnmarshaller.unmarshal();

			Framework.mapOfTask = aTaskListUnmarshaller.getMapOfTask();
		} catch (final JAXBException e) {
			Framework.logger.error("JAXBException : "+e.getMessage(), e);
			e.printStackTrace();
		} catch (final MalformedURLException e) {
			Framework.logger.error("MalformedURLException : "+e.getMessage(), e);
			e.printStackTrace();
		} catch (final Exception e) {
			Framework.logger.error("Exception : "+e.getMessage(), e);
			e.printStackTrace();
		}
		Framework.logger.info("End of initialisation of UniCORN.");
	}

}
