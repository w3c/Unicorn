// $Id: RDFUnmarshallerJena.java,v 1.3 2008-08-26 15:26:37 fbatard Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.contract.Observer;
import org.w3c.unicorn.tasklist.parameters.Mapping;
import org.w3c.unicorn.tasklist.parameters.Parameter;
import org.w3c.unicorn.tasklist.parameters.Value;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

/**
 * @author Damien LEROY
 *
 */
public class RDFUnmarshallerJena implements RDFUnmarshaller {

	private static final Log logger = LogFactory.getLog("org.w3c.unicorn.tasklist");

	private static final Model MODEL = ModelFactory.createDefaultModel();

	private static final String UCN_NAMESPACE = "http://www.w3.org/unicorn#";

	private static Resource RESOURCE_TASK = null;

	private static Property PROPERTY_DESCRIPTION = null;
	private static Property PROPERTY_HASMAPPING = null;
	private static Property PROPERTY_HASPARAMETER = null;
	private static Property PROPERTY_HASVALUE = null;
	private static Property PROPERTY_LONGNAME = null;
	private static Property PROPERTY_OBSERVER = null;
	private static Property PROPERTY_PARAMETER = null;
	private static Property PROPERTY_REFERENCE = null;
	private static Property PROPERTY_TYPE = null;
	private static Property PROPERTY_VALUE = null;

	static {
		RDFUnmarshallerJena.MODEL.read(
				org.w3c.unicorn.util.Property.get("TASKLIST_RDF_MODEL"),
				null);

		// define resource use to find information into the RDF graph
		RDFUnmarshallerJena.RESOURCE_TASK = RDFUnmarshallerJena.MODEL.getProperty(RDFUnmarshallerJena.UCN_NAMESPACE+"Task");

		// define property use to find information into the RDF graph
		RDFUnmarshallerJena.PROPERTY_DESCRIPTION = RDFUnmarshallerJena.MODEL.getProperty(RDFUnmarshallerJena.UCN_NAMESPACE+"description");
		RDFUnmarshallerJena.PROPERTY_HASMAPPING = RDFUnmarshallerJena.MODEL.getProperty(RDFUnmarshallerJena.UCN_NAMESPACE+"hasMapping");
		RDFUnmarshallerJena.PROPERTY_HASPARAMETER = RDFUnmarshallerJena.MODEL.getProperty(RDFUnmarshallerJena.UCN_NAMESPACE+"hasParameter");
		RDFUnmarshallerJena.PROPERTY_HASVALUE = RDFUnmarshallerJena.MODEL.getProperty(RDFUnmarshallerJena.UCN_NAMESPACE+"hasValue");
		RDFUnmarshallerJena.PROPERTY_LONGNAME = RDFUnmarshallerJena.MODEL.getProperty(RDFUnmarshallerJena.UCN_NAMESPACE+"longName");
		RDFUnmarshallerJena.PROPERTY_OBSERVER = RDFUnmarshallerJena.MODEL.getProperty(RDFUnmarshallerJena.UCN_NAMESPACE+"observer");
		RDFUnmarshallerJena.PROPERTY_PARAMETER = RDFUnmarshallerJena.MODEL.getProperty(RDFUnmarshallerJena.UCN_NAMESPACE+"parameter");
		RDFUnmarshallerJena.PROPERTY_REFERENCE = RDFUnmarshallerJena.MODEL.getProperty(RDFUnmarshallerJena.UCN_NAMESPACE+"reference");
		RDFUnmarshallerJena.PROPERTY_TYPE = RDFUnmarshallerJena.MODEL.getProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
		RDFUnmarshallerJena.PROPERTY_VALUE = RDFUnmarshallerJena.MODEL.getProperty(RDFUnmarshallerJena.UCN_NAMESPACE+"value");

	}

	private Map<String, Observer> mapOfObserver = null;
	private Map<String, Task> mapOfTask = null;
	private Model aModel = null;

	/**
	 * Default constructor.
	 *
	 */
	public RDFUnmarshallerJena () {
		RDFUnmarshallerJena.logger.trace("Constructor");
		this.aModel = ModelFactory.createDefaultModel();
	}

	/**
	 * Adds a Model with the given URL to this aModel.
	 * @param aURL The URL to add.
	 */
	public void addURL (final URL aURL) throws IOException {
		RDFUnmarshallerJena.logger.trace("addURL");
		if (RDFUnmarshallerJena.logger.isDebugEnabled()) {
			RDFUnmarshallerJena.logger.debug("URL : "+aURL+".");
		}
		final Model aModel = ModelFactory.createDefaultModel();
		aModel.read(aURL.openStream(), null);
		this.aModel.add(aModel);
	}

	/**
	 * Adds a name (with its language) to the given task.
	 * @param aTask The task to name.
	 * @param aLiteral The name of the task.
	 */
	private void addLongName (final Task aTask, final Literal aLiteral) {
		RDFUnmarshallerJena.logger.debug(
				"LongName lang:" + aLiteral.getLanguage() +
				" value:" + aLiteral.getString() + ".");
		aTask.addLongName(aLiteral.getLanguage(), aLiteral.getString());
	}

	/**
	 * Adds a description (with its language) to the given task.
	 * @param aTask The task to describe.
	 * @param aLiteral The description of the task.
	 */
	private void addDescription (final Task aTask, final Literal aLiteral) {
		RDFUnmarshallerJena.logger.debug(
				"Description lang:" + aLiteral.getLanguage() +
				" value:" + aLiteral.getString() + ".");
		aTask.addDescription(aLiteral.getLanguage(), aLiteral.getString());
	}

	/**
	 * Adds a name (with its language) to the given parameter.
	 * @param aTask The parameter to name.
	 * @param aLiteral The name of the parameter.
	 */
	private void addLongName (final Parameter aParameter, final Literal aLiteral) {
		RDFUnmarshallerJena.logger.debug(
				"Parameter long name lang:" + aLiteral.getLanguage() +
				" value:" + aLiteral.getString() + ".");
		aParameter.addLongName(aLiteral.getLanguage(), aLiteral.getString());
	}

	/**
	 * Adds a name (with its language) to the given value.
	 * @param aTask The value to name.
	 * @param aLiteral The name of the value.
	 */
	private void addLongName (final Value aValue, final Literal aLiteral) {
		RDFUnmarshallerJena.logger.debug(
				"Value long name lang:" + aLiteral.getLanguage() +
				" value:" + aLiteral.getString() + ".");
		aValue.addLongName(aLiteral.getLanguage(), aLiteral.getString());
	}

	/**
	 * Adds a mapping to the given value.
	 * @param value The value to map.
	 * @param aMapping The mapping for the value.
	 */
	private void addMapping (final Value aValue, final Resource aMapping) {
		RDFUnmarshallerJena.logger.trace("addMapping");
		if (RDFUnmarshallerJena.logger.isDebugEnabled()) {
			RDFUnmarshallerJena.logger.debug("Value : "+aValue.getName()+".");
			RDFUnmarshallerJena.logger.debug("Mapping : "+aMapping.getLocalName()+".");
		}
		// TODO verifier ce brol
		final String sObserver = aMapping.getProperty(RDFUnmarshallerJena.PROPERTY_OBSERVER).getLiteral().getString();
		final String sParameter = aMapping.getProperty(RDFUnmarshallerJena.PROPERTY_PARAMETER).getLiteral().getString();
		final String sValue = aMapping.getProperty(RDFUnmarshallerJena.PROPERTY_VALUE).getLiteral().getString();
		final Mapping oMapping = new Mapping();
		oMapping.setObserver(this.mapOfObserver.get(sObserver));
		oMapping.setParam(sParameter);
		oMapping.setValue(sValue);
	}

	/**
	 * Adds a value to the given parameter.
	 * @param aParameter The parameter to consider.
	 * @param aValue The value to add.
	 */
	private void addValue (final Parameter aParameter, final Resource aValue) {
		final String sValue =
			aValue.getProperty(RDFUnmarshallerJena.PROPERTY_VALUE).getLiteral().getString();
		final Value oValue = aParameter.getValue(sValue);
		if (null == oValue) {
			RDFUnmarshallerJena.logger.warn(
					"Value " + sValue +
					" not found in parameter " + aParameter.getName() + ".");
			return;
		}
		RDFUnmarshallerJena.logger.debug("Parameter value  : " + sValue + ".");
		// find and add longName of the Value
		for (
				final StmtIterator siLongName = aValue.listProperties(
						RDFUnmarshallerJena.PROPERTY_LONGNAME);
				siLongName.hasNext();) {
			final Literal lLongName = siLongName.nextStatement().getLiteral();
			this.addLongName(oValue, lLongName);
		} // find and add longName of the Value
		// TODO completer l'ajout de Value
		// find and add mapping of the Value
		for (
				final StmtIterator siMapping = aValue.listProperties(
						RDFUnmarshallerJena.PROPERTY_HASMAPPING);
				siMapping.hasNext();) {
			final Resource aMapping = (Resource) siMapping.nextStatement().getObject();
			this.addMapping(oValue, aMapping);
		} // find and add mapping of the Value
	}

	/**
	 * Adds a parameter to the given task.
	 * @param aTask The task to consider.
	 * @param aParameter The parameter to add.
	 */
	private void addParameter (final Task aTask, final Resource aParameter) {
		final String sParameterReference =
			aParameter.getProperty(RDFUnmarshallerJena.PROPERTY_REFERENCE).getLiteral().getString();
		final Parameter oParameter = aTask.getMapOfParameter().get(sParameterReference);
		if (null == oParameter) {
			RDFUnmarshallerJena.logger.warn(
					"Parameter " + sParameterReference +
					" not found in task " + aTask.getID() + ".");
			return;
		}
		RDFUnmarshallerJena.logger.debug("Parameter : "+sParameterReference+".");
		// find and add longName of the Parameter
		for (
				StmtIterator siLongName = aParameter.listProperties(
						RDFUnmarshallerJena.PROPERTY_LONGNAME);
				siLongName.hasNext();) {
			final Literal lLongName = siLongName.nextStatement().getLiteral();
			this.addLongName(oParameter, lLongName);
		} // find and add longName of the Parameter
		// find and add value of the Parameter
		for (
				final StmtIterator siValue = this.aModel.listStatements(
						aParameter,
						RDFUnmarshallerJena.PROPERTY_HASVALUE,
						(RDFNode) null);
				siValue.hasNext();) {
			final Resource aValue = (Resource) siValue.nextStatement().getObject();
			if (null == aValue) {
				RDFUnmarshallerJena.logger.error("Resource value == null.");
				continue;
			}
			this.addValue(oParameter, aValue);
		} // find and add value of the Parameter
	}

	/**
	 * Adds a task to this object.
	 * @param aTask The task to add.
	 * @throws Exception
	 */
	private void addTask (final Resource aTask) throws Exception {
		final Statement aReference = aTask.getProperty(RDFUnmarshallerJena.PROPERTY_REFERENCE);
		final Task oTask = this.mapOfTask.get(aReference.getLiteral().getString());
		if (oTask == null) {
			// TODO creer et initialiser une Task si les informations peuvent
			// TODO etre mise uniquement dans le fichier rdf
			RDFUnmarshallerJena.logger.error(
					"No task with reference : " +
					aReference.getLiteral().getString() +
					".");
			throw new Exception("No task with reference : "+aReference.getLiteral().getString()+".");
		}
		RDFUnmarshallerJena.logger.debug("Reference : "+aReference.getObject().toString()+".");
		// find and add longName of the task
		for (
				final StmtIterator siLongName = aTask.listProperties(
						RDFUnmarshallerJena.PROPERTY_LONGNAME);
				siLongName.hasNext();) {
			final Literal lLongName = siLongName.nextStatement().getLiteral();
			this.addLongName(oTask, lLongName);
		} // find and add longName of the task
		// find and add description of the task
		for (
				final StmtIterator siDescription = aTask.listProperties(
						RDFUnmarshallerJena.PROPERTY_DESCRIPTION);
				siDescription.hasNext();) {
			final Literal lDescription = siDescription.nextStatement().getLiteral();
			this.addDescription(oTask, lDescription);
		} // find and add description of the task
		// find and add Parameter of the task
		for (
				final StmtIterator siParameter = this.aModel.listStatements(
						aTask,
						RDFUnmarshallerJena.PROPERTY_HASPARAMETER,
						(RDFNode) null);
				siParameter.hasNext();) {
			final Resource aParameter = (Resource) siParameter.nextStatement().getObject();
			if (null == aParameter) {
				RDFUnmarshallerJena.logger.error("Resource parameter == null.");
				continue;
			}
			this.addParameter(oTask, aParameter);
		} // find and add Parameter of the task
	}

	/* (non-Javadoc)
	 * @see org.w3c.unicorn.util.Unmarshaller#unmarshal(java.net.URL)
	 */
	public void unmarshal () throws Exception {
		RDFUnmarshallerJena.logger.trace("unmarshal");

		// find and add task
		for (
				final StmtIterator siTask = this.aModel.listStatements(
						null,
						RDFUnmarshallerJena.PROPERTY_TYPE,
						RDFUnmarshallerJena.RESOURCE_TASK);
				siTask.hasNext();) {
			final Resource aTask = siTask.nextStatement().getSubject();
			this.addTask(aTask);
		} // find and add task
		RDFUnmarshallerJena.logger.trace("End.");
	}

	/**
	 * @return Returns the observers.
	 *//*
	public Map<String, Observer> getMapOfObserver () {
		return this.mapOfObserver;
	}*/

	/**
	 * @param mapOfTask The observers to set.
	 */
	public void setMapOfObserver (final Map<String, Observer> mapOfObserver) {
		this.mapOfObserver = mapOfObserver;
	}

	/**
	 * @return Returns the tasks.
	 */
	public Map<String, Task> getMapOfTask () {
		return this.mapOfTask;
	}

	/**
	 * @param mapOfTask The tasks to set.
	 */
	public void setMapOfTask (final Map<String, Task> mapOfTask) {
		this.mapOfTask = mapOfTask;
	}

}
